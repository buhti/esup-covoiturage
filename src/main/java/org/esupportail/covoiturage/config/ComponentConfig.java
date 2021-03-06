package org.esupportail.covoiturage.config;

import java.util.Locale;

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
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * Cette classe permet de définit les composants Spring utilisés par les
 * services de l'application.
 *
 * @author Florent Cailhol (Anyware Services)
 */
@Configuration
@ComponentScan("org.esupportail.covoiturage")
@ImportResource("/WEB-INF/context/componentData.xml")
@PropertySource(value = { "classpath:default.properties", "classpath:config.properties" })
public class ComponentConfig {

    @Resource
    private Environment environment;

    @Bean
    public ContextSource ldapContextSource() {
        LdapContextSource contextSource = new LdapContextSource();
        contextSource.setUrl(environment.getProperty("ldap.url"));
        contextSource.setBase(environment.getProperty("ldap.base"));
        contextSource.setUserDn(environment.getProperty("ldap.userDn"));
        contextSource.setPassword(environment.getProperty("ldap.password"));
        return contextSource;
    }

    @Bean
    public LdapTemplate ldapTemplate() {
        return new LdapTemplate(ldapContextSource());
    }

    @Bean
    public Validator smartValidator() {
        return new LocalValidatorFactoryBean();
    }

    @Bean
    public MailSender mailSender() {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost(environment.getProperty("smtp.host"));
        sender.setPort(environment.getProperty("smtp.port", Integer.class));
        sender.setUsername(environment.getProperty("smtp.username"));
        sender.setPassword(environment.getProperty("smtp.password"));
        return sender;
    }

    @Bean
    public Locale defautLocale() {
        return Locale.FRENCH;
    }

}
