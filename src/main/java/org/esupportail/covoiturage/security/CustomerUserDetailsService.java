package org.esupportail.covoiturage.security;

import java.util.List;

import javax.annotation.Resource;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;

import org.esupportail.covoiturage.domain.Customer;
import org.esupportail.covoiturage.repository.CustomerRepository;
import org.esupportail.covoiturage.repository.StatRepository;
import org.esupportail.covoiturage.repository.StatRepository.StatType;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * Ce service permet de récupérer les informations de l'utilisateur qui tente de
 * s'authentifier.
 *
 * @author Florent Cailhol (Anyware Services)
 */
@Component("userDetailsService")
public class CustomerUserDetailsService implements UserDetailsService {

    @Value("${ldap.userDnSubPath}")
    private String userDnSubPath;

    @Value("${ldap.userObjectClass}")
    private String userObjectClass;

    @Value("${ldap.attribute.uid}")
    private String uidAttribute;

    @Value("${ldap.attribute.mail}")
    private String mailAttribute;

    @Value("${ldap.attribute.firstname}")
    private String firstnameAttribute;

    @Value("${ldap.attribute.lastname}")
    private String lastnameAttribute;

    @Resource(name = "administratorLogins")
    private List<String> administratorLogins;

    @Resource
    private CustomerRepository customerRepository;

    @Resource
    private StatRepository statRepository;

    @Resource
    private LdapTemplate ldapTemplate;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = customerRepository.findOneByLogin(username);
        boolean freshlyCreated = false;

        if (customer == null) {
            customer = customerRepository.createCustomer(loadUserFromLdap(username));
            statRepository.incrementStatistic(StatType.REGISTRATIONS);
            freshlyCreated = true;
        } else {
            customerRepository.updateLastConnectionDate(customer);
        }

        statRepository.incrementStatistic(StatType.LOGINS);

        // Check if the current user has administrator rights
        boolean hasAdminRights = administratorLogins.contains(username);

        return new CustomerUserDetails(customer, hasAdminRights, freshlyCreated);
    }

    @SuppressWarnings("unchecked")
    private Customer loadUserFromLdap(String uid) throws UsernameNotFoundException {
        DistinguishedName dn = new DistinguishedName(userDnSubPath);

        AndFilter filter = new AndFilter();
        filter.and(new EqualsFilter("objectClass", userObjectClass));
        filter.and(new EqualsFilter(uidAttribute, uid));

        List<Customer> customers = ldapTemplate.search(dn, filter.encode(), SearchControls.SUBTREE_SCOPE,
                new AttributesMapperImpl());

        if (customers.isEmpty()) {
            throw new UsernameNotFoundException("LDAP user '" + uid + "' not found");
        } else if (customers.size() > 1) {
            throw new UsernameNotFoundException("Too many LDAP user '" + uid + "'");
        }

        return customers.get(0);
    }

    private class AttributesMapperImpl implements AttributesMapper {

        @Override
        public Object mapFromAttributes(Attributes attributes) throws NamingException {
            String uid = attributes.get(uidAttribute).get().toString();
            String mail = attributes.get(mailAttribute).get().toString();
            String firstname = attributes.get(firstnameAttribute).get().toString();
            String lastname = attributes.get(lastnameAttribute).get().toString();

            return new Customer(0, uid, mail, firstname, lastname, false, false, false);
        }

    }

}
