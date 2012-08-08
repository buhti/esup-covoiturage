package org.esupportail.covoiturage.web.form;

import org.esupportail.covoiturage.domain.Customer;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class CustomerForm {

    @NotEmpty
    private String firstname;

    @NotEmpty
    private String lastname;

    @Email
    private String email;
    
    public CustomerForm() {
        // Default Spring constructor
    }

    public CustomerForm(Customer customer) {
        firstname = customer.getFirstname();
        lastname = customer.getLastname();
        email = customer.getEmail();
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
