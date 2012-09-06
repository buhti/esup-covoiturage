package org.esupportail.covoiturage.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.esupportail.covoiturage.domain.Customer;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Ce modèle permet de manipuler l'utilisateur courant au sein des services de
 * sécurité de l'application.
 *
 * @author Florent Cailhol (Anyware Services)
 */
public class CustomerUserDetails extends Customer implements UserDetails {

    private static final long serialVersionUID = 2360022873140258870L;

    private final List<SimpleGrantedAuthority> authorities;
    private boolean freshlyCreated;

    /**
     * Constructeur.
     *
     * @param customer Information de l'utilisateur
     * @param admin <code>true</code> si administrateur
     * @param freshlyCreated <code>true</code> si nouvellement créé
     */
    public CustomerUserDetails(Customer customer, boolean admin, boolean freshlyCreated) {
        super(customer.getId(), customer.getLogin(), null, null, null, false, false, false);

        authorities = new ArrayList<SimpleGrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        if (admin) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }

        this.freshlyCreated = freshlyCreated;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return getLogin();
    }

    @Override
    public String getUsername() {
        return getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public boolean isFreshlyCreated() {
        return freshlyCreated;
    }

    public void setFreshlyCreated(boolean freshlyCreated) {
        this.freshlyCreated = freshlyCreated;
    }

}
