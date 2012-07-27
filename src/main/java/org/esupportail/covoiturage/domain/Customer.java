package org.esupportail.covoiturage.domain;

public class Customer {

    private final long id;
    private final String login;
    private final String email;
    private final String name;

    public Customer(long id) {
        this.id = id;
        this.login = null;
        this.email = null;
        this.name = null;
    }

    public Customer(long id, String login, String email, String name) {
        this.id = id;
        this.login = login;
        this.email = email;
        this.name = name;
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

    public String getName() {
        return name;
    }

}
