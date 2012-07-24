package org.esupportail.covoiturage.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.env.Environment;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class ConfigurationExposingHandlerInterceptor implements HandlerInterceptor {

    private final Environment environment;

    public ConfigurationExposingHandlerInterceptor(Environment environment) {
        this.environment = environment;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        if (modelAndView != null) {
            modelAndView.addObject("config", new ConfigurationHolder());
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    }

    public class ConfigurationHolder {

        public String getGoogleApiKey() {
            return environment.getProperty("google.services.apiKey");
        }

    }
}
