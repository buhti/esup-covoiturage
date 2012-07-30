package org.esupportail.covoiturage.config;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

@Configuration
@ComponentScan("org.esupportail.covoiturage")
@ImportResource("/WEB-INF/context/componentData.xml")
@PropertySource(value = { "classpath:default.properties", "classpath:config.properties" })
public class ComponentConfig {

    @Resource
    private Environment environment;

    @Bean
    public ContextSource contextSource() {
        LdapContextSource contextSource = new LdapContextSource();
        contextSource.setUrl(environment.getProperty("ldap.url"));
        contextSource.setBase(environment.getProperty("ldap.base"));
        contextSource.setUserDn(environment.getProperty("ldap.userDn"));
        contextSource.setPassword(environment.getProperty("ldap.password"));
        return contextSource;
    }

    @Bean
    public LdapTemplate ldapTemplate() {
        return new LdapTemplate(contextSource());
    }

}
