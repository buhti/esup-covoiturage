package org.esupportail.covoiturage.web.form;

import javax.validation.Valid;

import org.esupportail.covoiturage.domain.Criterias;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Ce formulaire permet la recherche de trajets.
 *
 * @author Florent Cailhol (Anyware Services)
 */
public class SearchForm {

    @NotEmpty
    private String from;

    @NotEmpty
    private String to;

    @Valid
    private DateTimeField date;

    private int fromTolerance;
    private int toTolerance;
    private int dateTolerance;

    /**
     * Constructeur.
     */
    public SearchForm() {
        // Create fields
        date = new DateTimeField();
    }

    /**
     * Remplit les champs du formulaire en fonction des critères passés en
     * paramètre.
     *
     * @param route Trajet
     */
    public void populate(Criterias criterias) {
        date = new DateTimeField(criterias.getDate());

        from = criterias.getFrom();
        to = criterias.getTo();
        fromTolerance = criterias.getFromTolerance();
        toTolerance = criterias.getToTolerance();
        dateTolerance = criterias.getDateTolerance();
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public int getFromTolerance() {
        return fromTolerance;
    }

    public void setFromTolerance(int fromTolerance) {
        this.fromTolerance = fromTolerance;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public int getToTolerance() {
        return toTolerance;
    }

    public void setToTolerance(int toTolerance) {
        this.toTolerance = toTolerance;
    }

    public DateTimeField getDate() {
        return date;
    }

    public void setDateTime(DateTimeField date) {
        this.date = date;
    }

    public int getDateTolerance() {
        return dateTolerance;
    }

    public void setDateTolerance(int dateTolerance) {
        this.dateTolerance = dateTolerance;
    }

    /**
     * Retourne les critères de recherche.
     *
     * @return critères
     */
    public Criterias toCriterias() {
        return new Criterias(from, to, date.toDateTime(), fromTolerance, toTolerance, dateTolerance);
    }

}
