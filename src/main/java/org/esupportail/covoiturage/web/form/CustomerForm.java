package org.esupportail.covoiturage.web.form;

import javax.validation.constraints.NotNull;

import org.esupportail.covoiturage.domain.Customer;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Ce formulaire permet la modification d'un utilisateur.
 *
 * @author Florent Cailhol (Anyware Services)
 */
public class CustomerForm {

    @NotEmpty
    private String firstname;

    @NotEmpty
    private String lastname;

    @Email
    private String email;

    @NotNull
    private boolean chatting;

    @NotNull
    private boolean smoking;

    @NotNull
    private boolean listeningMusic;

    /**
     * Constructeur.
     */
    public CustomerForm() {
        chatting = false;
        smoking = false;
        listeningMusic = false;
    }

    /**
     * Constructeur.
     *
     * @param customer Valeur par défaut.
     */
    public CustomerForm(Customer customer) {
        firstname = customer.getFirstname();
        lastname = customer.getLastname();
        email = customer.getEmail();
        chatting = customer.isChatting();
        smoking = customer.isSmoking();
        listeningMusic = customer.isListeningMusic();
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

    public boolean isChatting() {
        return chatting;
    }

    public void setChatting(boolean chatting) {
        this.chatting = chatting;
    }

    public boolean isSmoking() {
        return smoking;
    }

    public void setSmoking(boolean smoking) {
        this.smoking = smoking;
    }

    public boolean isListeningMusic() {
        return listeningMusic;
    }

    public void setListeningMusic(boolean listeningMusic) {
        this.listeningMusic = listeningMusic;
    }

    /**
     * Retourne l'utilisateur modifié.
     *
     * @param id ID de l'utilisateur
     * @param login Login
     * @return
     */
    public Customer toCustomer(long id, String login) {
        return new Customer(id, login, email, firstname, lastname, chatting, smoking, listeningMusic);
    }

}
