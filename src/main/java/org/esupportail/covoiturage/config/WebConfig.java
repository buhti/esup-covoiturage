package org.esupportail.covoiturage.config;

import java.util.List;

import javax.annotation.Resource;

import org.esupportail.covoiturage.web.interceptor.AccountExposingHandlerInterceptor;
import org.esupportail.covoiturage.web.interceptor.ConfigurationExposingHandlerInterceptor;
import org.esupportail.covoiturage.web.interceptor.FirstConnectionHandlerInterceptor;
import org.esupportail.covoiturage.web.resolver.AccountHandlerMethodArgumentResolver;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.tiles2.TilesConfigurer;
import org.springframework.web.servlet.view.tiles2.TilesView;

/**
 * Cette classe permet de définir les composants utilisés par la webapp. Elle
 * permet aussi de configuration la webapp afin d'exposer plus d'informations
 * aux vues ou de faciliter l'injecter des dépendences au sein des contrôleurs.
 *
 * @author Florent Cailhol (Anyware Services)
 */
@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {

    @Resource
    private Environment environment;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new AccountHandlerMethodArgumentResolver());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AccountExposingHandlerInterceptor());
        registry.addInterceptor(new ConfigurationExposingHandlerInterceptor(environment));
        registry.addInterceptor(new FirstConnectionHandlerInterceptor("/mon-compte"));
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("/WEB-INF/messages/messages");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setUseCodeAsDefaultMessage(true);
        if (environment.getProperty("app.skin.debug", Boolean.class)) {
            messageSource.setCacheSeconds(0);
        }
        return messageSource;
    }

    @Bean
    public ViewResolver viewResolver() {
        UrlBasedViewResolver viewResolver = new UrlBasedViewResolver();
        viewResolver.setViewClass(TilesView.class);
        return viewResolver;
    }

    @Bean
    public TilesConfigurer tilesConfigurer() {
        TilesConfigurer configurer = new TilesConfigurer();
        configurer.setDefinitions(new String[] { "/WEB-INF/layouts/tiles.xml", "/WEB-INF/views/**/tiles.xml" });
        configurer.setCheckRefresh(true);
        return configurer;
    }

}
