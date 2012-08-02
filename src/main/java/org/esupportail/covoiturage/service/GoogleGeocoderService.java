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
import org.esupportail.covoiturage.exception.LocationNotFoundException;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

@Component
public class GoogleGeocoderService implements GeocoderService, InitializingBean {

    private static final String GEOCODE_REQUEST_SERVER_HTTP = "http://maps.googleapis.com";
    private static final String GEOCODE_REQUEST_QUERY_BASIC = "/maps/api/geocode/xml?sensor=false";

    private static final String GEOCODE_XPATH_STATUS = "/GeocodeResponse/status";
    private static final String GEOCODE_XPATH_ADDRESS = "/GeocodeResponse/result/formatted_address";
    private static final String GEOCODE_XPATH_CITY = "/GeocodeResponse/result/address_component[type = 'locality']/long_name";
    private static final String GEOCODE_XPATH_LAT = "/GeocodeResponse/result/geometry/location/lat";
    private static final String GEOCODE_XPATH_LNG = "/GeocodeResponse/result/geometry/location/lng";

    private static DocumentBuilder documentBuilder;

    @Override
    public void afterPropertiesSet() throws Exception {
        documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    }

    @Override
    public Location geocode(String location) throws LocationNotFoundException {
        Document response;
        try {
            response = request(getURL(location));
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
            city = xpath.evaluate(GEOCODE_XPATH_CITY, response);
            address = xpath.evaluate(GEOCODE_XPATH_ADDRESS, response);
        } catch (XPathExpressionException e) {
            throw new RuntimeException("Unable to read Google Geocoding response", e);
        }

        return new Location(lat, lng, city, address);
    }

    private String getURL(String address) throws UnsupportedEncodingException {
        StringBuilder url = new StringBuilder(GEOCODE_REQUEST_SERVER_HTTP);

        url.append(GEOCODE_REQUEST_QUERY_BASIC);
        url.append("&address=");
        url.append(URLEncoder.encode(address, "UTF-8"));

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
