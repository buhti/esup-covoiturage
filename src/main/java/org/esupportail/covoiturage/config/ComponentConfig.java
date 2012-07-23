package org.esupportail.covoiturage.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("org.esupportail.covoiturage")
@ImportResource("/WEB-INF/context/componentData.xml")
@PropertySource(value = { "classpath:default.properties", "classpath:config.properties" })
public class ComponentConfig {

}
