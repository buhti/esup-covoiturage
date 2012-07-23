package org.esupportail.covoiturage.exception;

@SuppressWarnings("serial")
public class LocationNotFoundException extends Exception {

    private final String address;

    public LocationNotFoundException(String address) {
        super("The following address was not found: " + address);
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

}
