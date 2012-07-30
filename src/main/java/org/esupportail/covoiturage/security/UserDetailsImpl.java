package org.esupportail.covoiturage.security;

import java.util.ArrayList;
import java.util.Collection;

import org.esupportail.covoiturage.domain.Customer;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsImpl implements UserDetails {

    private static final long serialVersionUID = 2360022873140258870L;

    private final Customer customer;

    public UserDetailsImpl(Customer customer) {
        this.customer = customer;
    }

    @SuppressWarnings("serial")
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<GrantedAuthority>() {
            {
                add(new SimpleGrantedAuthority("ROLE_USER"));
            }
        };
    }

    @Override
    public String getPassword() {
        return customer.getLogin();
    }

    @Override
    public String getUsername() {
        return customer.getLogin();
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

}
