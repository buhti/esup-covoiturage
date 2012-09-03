package org.esupportail.covoiturage.exception;

/**
 * Cette exception est levée quand un lieu ne peut être trouvé.
 *
 * @author Florent Cailhol (Anyware Services)
 */
@SuppressWarnings("serial")
public class LocationNotFoundException extends Exception {

    private final String address;

    /**
     * Constructeur.
     *
     * @param address Adresse du lieu
     */
    public LocationNotFoundException(String address) {
        super("The following address was not found: " + address);
        this.address = address;
    }

    /**
     * Retourne l'adresse du lieu.
     *
     * @return adresse
     */
    public String getAddress() {
        return address;
    }

}
