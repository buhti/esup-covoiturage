package org.esupportail.covoiturage.domain;

/**
 * Ce modèle représente un utilisateur de l'application.
 *
 * @author Florent Cailhol (Anyware Services)
 */
public class Customer {

    private final long id;
    private final String login;
    private final String email;
    private final String firstname;
    private final String lastname;
    private final boolean chatting;
    private final boolean smoking;
    private final boolean listeningMusic;

    /**
     * Constructeur.
     *
     * @param id ID de l'utilisateur
     */
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

    /**
     * Constructeur.
     *
     * @param id
     * @param login
     * @param email
     * @param firstname
     * @param lastname
     * @param chatting
     * @param smoking
     * @param listeningMusic
     */
    public Customer(long id, String login, String email, String firstname, String lastname, boolean chatting,
            boolean smoking, boolean listeningMusic) {
        this.id = id;
        this.login = login;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.chatting = chatting;
        this.smoking = smoking;
        this.listeningMusic = listeningMusic;
    }

    /**
     * Retourne l'identifiant de l'utilisateur.
     *
     * @return ID
     */
    public long getId() {
        return id;
    }

    /**
     * Retourne le login de l'utilisateur.
     *
     * @return login
     */
    public String getLogin() {
        return login;
    }

    /**
     * Retourne l'adresse email de l'utilisateur.
     *
     * @return adresse email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Retourne le prénom de l'utilisateur.
     *
     * @return prénom
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * Retourne le nom de l'utilisateur.
     *
     * @return nom
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * Retourne <code>true</code> si l'utilisateur aime discuter.
     *
     * @return
     */
    public boolean isChatting() {
        return chatting;
    }

    /**
     * Retourne <code>true</code> si l'utilisateur est fumeur.
     *
     * @return
     */
    public boolean isSmoking() {
        return smoking;
    }

    /**
     * Retourne <code>true</code> si l'utilisateur aime écouter la musique.
     *
     * @return
     */
    public boolean isListeningMusic() {
        return listeningMusic;
    }

    @Override
    public String toString() {
        return firstname + " " + lastname.charAt(0) + ".";
    }

}
