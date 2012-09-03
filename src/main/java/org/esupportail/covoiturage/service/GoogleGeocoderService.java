package org.esupportail.covoiturage.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.esupportail.covoiturage.domain.Location;
import org.esupportail.covoiturage.exception.DistanceNotFoundException;
import org.esupportail.covoiturage.exception.LocationNotFoundException;
import org.esupportail.covoiturage.util.XMLUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Ce service permet d'effectuer des opérations de géocodage grâce aux API de
 * Google Maps.
 *
 * @author Florent Cailhol (Anyware Services)
 */
@Component
public class GoogleGeocoderService implements GeocoderService, InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(GoogleGeocoderService.class);

    private static final String GOOGLE_MAPS_SERVER_HTTP = "http://maps.googleapis.com";

    private static final String GEOCODE_REQUEST_QUERY_BASIC = "/maps/api/geocode/xml?sensor=false";
    private static final String GEOCODE_XPATH_STATUS = "/GeocodeResponse/status";
    private static final String GEOCODE_XPATH_ADDRESS = "/GeocodeResponse/result/formatted_address";
    private static final String GEOCODE_XPATH_CITY = "/GeocodeResponse/result/address_component[type = 'locality']/long_name";
    private static final String GEOCODE_XPATH_LAT = "/GeocodeResponse/result/geometry/location/lat";
    private static final String GEOCODE_XPATH_LNG = "/GeocodeResponse/result/geometry/location/lng";

    private static final String DISTANCE_REQUEST_QUERY_BASIC = "/maps/api/distancematrix/xml?sensor=false";
    private static final String DISTANCE_XPATH_STATUS1 = "/DistanceMatrixResponse/status";
    private static final String DISTANCE_XPATH_STATUS2 = "/DistanceMatrixResponse/row/element/status";
    private static final String DISTANCE_XPATH_DISTANCE = "/DistanceMatrixResponse/row/element/distance/value";

    private static DocumentBuilder documentBuilder;

    @Override
    public void afterPropertiesSet() throws Exception {
        documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    }

    @Override
    public Location geocode(String location) throws LocationNotFoundException {
        Document response;
        try {
            String url = getGeocodeURL(location);

            if (logger.isTraceEnabled()) {
                logger.trace("Calling geocode [{}]", url);
            }

            response = request(url);

            if (logger.isTraceEnabled()) {
                logger.trace("Got response:\n{}", XMLUtil.format(response));
            }
        } catch (IOException e) {
            throw new RuntimeException("Unable to request Google Geocoding API", e);
        }

        double lat = 0;
        double lng = 0;
        String city;
        String address;

        try {
            XPathFactory factory = XPathFactory.newInstance();
            XPath xpath = factory.newXPath();

            // Check if the geocoding request suceeded
            if (!"OK".equals(xpath.evaluate(GEOCODE_XPATH_STATUS, response))) {
                throw new LocationNotFoundException(location);
            }

            // Read the geocoding response
            lat = Double.parseDouble(xpath.evaluate(GEOCODE_XPATH_LAT, response));
            lng = Double.parseDouble(xpath.evaluate(GEOCODE_XPATH_LNG, response));
            address = xpath.evaluate(GEOCODE_XPATH_ADDRESS, response);
            city = xpath.evaluate(GEOCODE_XPATH_CITY, response);

            // Check if the city can be read
            if (city == null || city.length() == 0) {
                throw new LocationNotFoundException(location);
            }
        } catch (XPathExpressionException e) {
            throw new RuntimeException("Unable to read Google Geocoding response", e);
        }

        return new Location(lat, lng, city, address);
    }

    @Override
    public int distance(Location origin, Location destination) throws DistanceNotFoundException {
        Document response;
        try {
            String url = getDistanceURL(origin.getAddress(), destination.getAddress());

            if (logger.isTraceEnabled()) {
                logger.trace("Calling distance [{}]", url);
            }

            response = request(url);

            if (logger.isTraceEnabled()) {
                logger.trace("Got response:\n{}", XMLUtil.format(response));
            }
        } catch (IOException e) {
            throw new RuntimeException("Unable to request Google Distance Matrix API", e);
        }

        int distance;

        try {
            XPathFactory factory = XPathFactory.newInstance();
            XPath xpath = factory.newXPath();

            // Check if the distance request suceeded
            if (!"OK".equals(xpath.evaluate(DISTANCE_XPATH_STATUS1, response))) {
                throw new DistanceNotFoundException(origin, destination);
            } else if (!"OK".equals(xpath.evaluate(DISTANCE_XPATH_STATUS2, response))) {
                throw new DistanceNotFoundException(origin, destination);
            }

            // Read the distance and convert meters to kilometers
            distance = 1 + Integer.parseInt(xpath.evaluate(DISTANCE_XPATH_DISTANCE, response)) / 1000;

        } catch (XPathExpressionException e) {
            throw new RuntimeException("Unable to read Google Distance Matrix response", e);
        }

        return distance;
    }

    private String getGeocodeURL(String address) throws UnsupportedEncodingException {
        StringBuilder url = new StringBuilder(GOOGLE_MAPS_SERVER_HTTP);

        url.append(GEOCODE_REQUEST_QUERY_BASIC);
        url.append("&address=");
        url.append(URLEncoder.encode(address, "UTF-8"));

        return url.toString();
    }

    private String getDistanceURL(String origin, String destination) throws UnsupportedEncodingException {
        StringBuilder url = new StringBuilder(GOOGLE_MAPS_SERVER_HTTP);

        url.append(DISTANCE_REQUEST_QUERY_BASIC);
        url.append("&mode=driving");
        url.append("&units=metric");
        url.append("&origins=");
        url.append(URLEncoder.encode(origin, "UTF-8"));
        url.append("&destinations=");
        url.append(URLEncoder.encode(destination, "UTF-8"));

        return url.toString();
    }

    private Document request(String url) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        Document geocoderResultDocument = null;

        try {
            conn.connect();
            geocoderResultDocument = documentBuilder.parse(new InputSource(conn.getInputStream()));
        } catch (SAXException e) {
            throw new RuntimeException("Failed to parse Google Geocoding response", e);
        } finally {
            conn.disconnect();
        }

        return geocoderResultDocument;
    }

}
