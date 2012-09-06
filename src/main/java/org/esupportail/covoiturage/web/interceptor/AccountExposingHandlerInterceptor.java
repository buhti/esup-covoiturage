package org.esupportail.covoiturage.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.esupportail.covoiturage.security.CustomerUserDetails;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * Cet intercepteur permet de passer l'utilisateur courant aux vues afin
 * d'afficher ses informations.
 *
 * @author Florent Cailhol (Anyware Services)
 */
public class AccountExposingHandlerInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() != null) {
            request.setAttribute("account", auth.getPrincipal());
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        CustomerUserDetails principal = (CustomerUserDetails) request.getAttribute("account");
        if (modelAndView != null && principal != null) {
            modelAndView.addObject("account", principal);
        }
    }

}
