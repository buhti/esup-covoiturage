package org.esupportail.covoiturage.domain;

public class Customer {

    private final long id;
    private final String login;
    private final String email;
    private final String firstname;
    private final String lastname;

    public Customer(long id) {
        this.id = id;
        this.login = null;
        this.email = null;
        this.firstname = null;
        this.lastname = null;
    }

    public Customer(long id, String login, String email, String firstname, String lastname) {
        this.id = id;
        this.login = login;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    @Override
    public String toString() {
        return firstname + " " + lastname.charAt(0) + ".";
    }

}
