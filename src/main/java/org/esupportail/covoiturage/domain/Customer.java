package org.esupportail.covoiturage.domain;


public class Customer {

    private final long id;
    private final String login;
    private final String email;
    private final String firstname;
    private final String lastname;
    private final boolean chatting;
    private final boolean smoking;
    private final boolean listeningMusic;

    public Customer(long id) {
        this.id = id;
        this.login = null;
        this.email = null;
        this.firstname = null;
        this.lastname = null;
        this.chatting = false;
        this.smoking = false;
        this.listeningMusic = false;
    }

    public Customer(long id, String login, String email, String firstname, String lastname,
            boolean chatting, boolean smoking, boolean listeningMusic) {
        this.id = id;
        this.login = login;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.chatting = chatting;
        this.smoking = smoking;
        this.listeningMusic = listeningMusic;
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

    public boolean isChatting() {
        return chatting;
    }

    public boolean isSmoking() {
        return smoking;
    }

    public boolean isListeningMusic() {
        return listeningMusic;
    }

    @Override
    public String toString() {
        return firstname + " " + lastname.charAt(0) + ".";
    }

}
