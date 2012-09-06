package org.esupportail.covoiturage.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.env.Environment;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * Cet intercepteur permet de passer des paramètres de configuration aux vues.
 *
 * @author Florent Cailhol (Anyware Services)
 */
public class ConfigurationExposingHandlerInterceptor extends HandlerInterceptorAdapter {

    private final Environment environment;

    public ConfigurationExposingHandlerInterceptor(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        if (modelAndView != null) {
            modelAndView.addObject("config", new ConfigurationHolder());
        }
    }

    public class ConfigurationHolder {

        public boolean isDebugSkin() {
            return environment.getProperty("app.skin.debug", Boolean.class);
        }

        public String getGoogleApiKey() {
            return environment.getProperty("google.services.apiKey");
        }

        public Co2Holder getCo2() {
            return new Co2Holder();
        }

    }

    public class Co2Holder {

        public String getA() {
            return environment.getProperty("app.co2.a");
        }

        public String getB() {
            return environment.getProperty("app.co2.b");
        }

    }

}
