package org.esupportail.covoiturage.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.esupportail.covoiturage.security.CustomerUserDetails;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * Cet intercepteur permet de rediriger l'utilisateur vers la page "Mon compte"
 * suite à sa première connexion.
 *
 * @author Florent Cailhol (Anyware Services)
 */
public class FirstConnectionHandlerInterceptor extends HandlerInterceptorAdapter {

    private final String profileMapping;

    /**
     * Constructeur.
     *
     * @param profileMapping URL de la page "Mon compte"
     */
    public FirstConnectionHandlerInterceptor(String profileMapping) {
        this.profileMapping = profileMapping;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() != null) {
            CustomerUserDetails customer = (CustomerUserDetails) auth.getPrincipal();
            if (customer.isFreshlyCreated()) {
                customer.setFreshlyCreated(false);

                if (profileMapping.startsWith("/")) {
                    response.sendRedirect(request.getContextPath() + profileMapping);
                } else {
                    response.sendRedirect(profileMapping);
                }

                return false;
            }
        }

        return true;
    }

}
