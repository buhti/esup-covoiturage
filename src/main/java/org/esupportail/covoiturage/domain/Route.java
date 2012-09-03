package org.esupportail.covoiturage.domain;

/**
 * Ce modèle représente un trajet.
 *
 * @author Florent Cailhol (Anyware Services)
 */
public abstract class Route {

    public static final int STATUS_UNKNOWN = 0;
    public static final int STATUS_DRIVER = 1;
    public static final int STATUS_PASSENGER = 2;

    private final long id;
    private final Customer owner;
    private final boolean driver;
    private final boolean ladiesOnly;
    private final int seats;
    private final Location from;
    private final Location to;
    private final int distance;
    private final boolean roundTrip;

    /**
     * Constructeur.
     *
     * @param id
     * @param owner
     * @param driver
     * @param ladiesOnly
     * @param seats
     * @param from
     * @param to
     * @param distance
     * @param roundTrip
     */
    protected Route(long id, Customer owner, boolean driver, boolean ladiesOnly, int seats, Location from, Location to,
            int distance, boolean roundTrip) {
        this.id = id;
        this.owner = owner;
        this.driver = driver;
        this.ladiesOnly = ladiesOnly;
        this.seats = seats;
        this.from = from;
        this.to = to;
        this.distance = distance;
        this.roundTrip = roundTrip;
    }

    /**
     * Retourne l'identifiant du trajet.
     *
     * @return ID
     */
    public long getId() {
        return id;
    }

    /**
     * Retourne le propriétaire du trajet.
     *
     * @return propriétaire
     */
    public Customer getOwner() {
        return owner;
    }

    /**
     * Retourne <code>true</code> si le propriétaire du trajet est le
     * conducteur.
     *
     * @return
     */
    public boolean isDriver() {
        return driver;
    }

    /**
     * Retourne <code>true</code> si ce trajet est réservé aux femmes.
     *
     * @return
     */
    public boolean isLadiesOnly() {
        return ladiesOnly;
    }

    /**
     * Retourne le nombre de places disponibles pour ce trajet.
     *
     * @return nombre de places
     */
    public int getSeats() {
        return seats;
    }

    /**
     * Retourne l'adresse de départ du trajet.
     *
     * @return adresse
     */
    public Location getFrom() {
        return from;
    }

    /**
     * Retourne l'adresse de retour du trajet.
     *
     * @return adresse
     */
    public Location getTo() {
        return to;
    }

    /**
     * Retourne la distance estimée du trajet.
     *
     * @return distance
     */
    public int getDistance() {
        return distance;
    }

    /**
     * Retourne <code>true</code> s'il s'agit d'un trajet Aller-Retour.
     *
     * @return
     */
    public boolean isRoundTrip() {
        return roundTrip;
    }

    /**
     * Retourne <code>true</code> s'il s'agit d'un trajet fréquent.
     *
     * @return
     */
    public abstract boolean isRecurrent();

}
