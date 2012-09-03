package org.esupportail.covoiturage.domain;

/**
 * Ce modèle représente un lieu grâce à ces coordonnées GPS ainsi qu'une brève
 * description.
 *
 * @author Florent Cailhol (Anyware Services)
 */
public class Location {

    private final double lat;
    private final double lng;
    private final String city;
    private final String address;

    /**
     * Constructeur.
     *
     * @param lat
     * @param lng
     * @param city
     * @param address
     */
    public Location(double lat, double lng, String city, String address) {
        this.lat = lat;
        this.lng = lng;
        this.city = city;
        this.address = address;
    }

    /**
     * Retourne la lattitude GPS du lieu.
     *
     * @return lattitude
     */
    public double getLat() {
        return lat;
    }

    /**
     * Retourne la longitude GPS du lieu.
     *
     * @return longitude
     */
    public double getLng() {
        return lng;
    }

    /**
     * Retourne le nom de la ville où se situe le lieu.
     *
     * @return ville
     */
    public String getCity() {
        return city;
    }

    /**
     * Retourne l'adresse complète du lieu.
     *
     * @return adresse
     */
    public String getAddress() {
        return address;
    }

}
