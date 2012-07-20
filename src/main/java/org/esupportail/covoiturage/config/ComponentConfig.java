package org.esupportail.covoiturage.config;

import java.security.InvalidKeyException;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import com.google.code.geocoder.Geocoder;

@Configuration
@ComponentScan("org.esupportail.covoiturage")
@ImportResource("/WEB-INF/context/componentData.xml")
@PropertySource(value = { "classpath:default.properties", "classpath:config.properties" })
public class ComponentConfig {

    @Resource
    private Environment environment;

    @Bean
    public Geocoder geocoder() throws InvalidKeyException {
        final String clientId = environment.getProperty("google.geocoding.clientId");
        final String clientKey = environment.getProperty("google.geocoding.clientKey");

        if (StringUtils.hasText(clientId) && StringUtils.hasText(clientKey)) {
            return new Geocoder(clientId, clientKey);
        }

        return new Geocoder();
    }

}
