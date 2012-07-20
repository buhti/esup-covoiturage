package org.esupportail.covoiturage.domain;

public class Location {

    private final double lat;
    private final double lng;
    private final String city;
    private final String address;

    public Location(double lat, double lng, String city, String address) {
        this.lat = lat;
        this.lng = lng;
        this.city = city;
        this.address = address;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public String getCity() {
        return city;
    }

    public String getAddress() {
        return address;
    }

}
