package org.esupportail.covoiturage.web.resolver;

import org.esupportail.covoiturage.domain.Customer;

import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * Cette classe permet d'injecter une instance représentant l'utilisateur
 * courant au sein des contrôleurs.
 *
 * @author Florent Cailhol (Anyware Services)
 */
public class AccountHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return Customer.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Authentication auth = (Authentication) webRequest.getUserPrincipal();
        return auth != null && auth.getPrincipal() instanceof Customer ? auth.getPrincipal() : null;
    }

}
