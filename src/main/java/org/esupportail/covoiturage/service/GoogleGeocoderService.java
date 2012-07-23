package org.esupportail.covoiturage.service;

import org.esupportail.covoiturage.domain.Location;
import org.esupportail.covoiturage.exception.LocationNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderAddressComponent;
import com.google.code.geocoder.model.GeocoderRequest;
import com.google.code.geocoder.model.GeocoderResult;
import com.google.code.geocoder.model.LatLng;

@Component
public class GoogleGeocoderService implements GeocoderService, InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(GoogleGeocoderService.class);

    @Value("${google.geocoding.clientId}")
    private String clientId;

    @Value("${google.geocoding.clientKey}")
    private String clientKey;

    private Geocoder geocoder;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (StringUtils.hasText(clientId) && StringUtils.hasText(clientKey)) {
            if (logger.isInfoEnabled()) {
                logger.info("Geocoder uses Premier account id '" + clientId + "'");
            }
            geocoder = new Geocoder(clientId, clientKey);
        } else {
            if (logger.isInfoEnabled()) {
                logger.info("Geocoder uses anonymous account");
            }
            geocoder = new Geocoder();
        }
    }

    @Override
    public Location geocode(String address) throws LocationNotFoundException {
        GeocoderRequest request = new GeocoderRequestBuilder().setAddress(address).getGeocoderRequest();
        GeocodeResponse response = geocoder.geocode(request);

        // Check if the geocoding request suceeded
        if (!response.getStatus().equals("OK") || !response.getResults().isEmpty()) {
            throw new LocationNotFoundException(address);
        }

        // Get the first result
        GeocoderResult result = response.getResults().get(0);
        LatLng location = result.getGeometry().getLocation();

        // Retrieve the city
        String city = "";
        for (GeocoderAddressComponent addressComponent : result.getAddressComponents()) {
            if (addressComponent.getTypes().contains("locality")) {
                city = addressComponent.getLongName();
                break;
            }
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Found a location with city '" + city + "'");
        }

        return new Location(location.getLat().doubleValue(), location.getLng().doubleValue(), city,
                result.getFormattedAddress());
    }

}
