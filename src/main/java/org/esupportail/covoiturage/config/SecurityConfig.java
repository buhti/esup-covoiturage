package org.esupportail.covoiturage.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ImportResource("/WEB-INF/context/security.xml")
@PropertySource(value = { "classpath:default.properties", "classpath:config.properties" })
public class SecurityConfig {

}
