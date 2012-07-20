package org.esupportail.covoiturage.web.form;

import java.util.Map;

import javax.validation.constraints.NotNull;

import org.esupportail.covoiturage.domain.Route;

public class RouteForm {

	private final Map<String, String> predefinedLocations;
	private final Map<String, String> possibleStatuses;
	private final Map<String, String> availableSeats;

	@NotNull
	private String fromCity;

	@NotNull
	private String fromAddress;

	@NotNull
	private String toCity;

	@NotNull
	private String toAddress;

	@NotNull
	private int status;

	@NotNull
	private int seats;

	public RouteForm(Map<String, String> predefinedLocations, Map<String, String> possibleStatuses,
			Map<String, String> availableSeats) {
		this.predefinedLocations = predefinedLocations;
		this.possibleStatuses = possibleStatuses;
		this.availableSeats = availableSeats;
	}

	public Map<String, String> getPredefinedLocations() {
		return predefinedLocations;
	}

	public Map<String, String> getPossibleStatuses() {
		return possibleStatuses;
	}

	public Map<String, String> getAvailableSeats() {
		return availableSeats;
	}

	public String getFromCity() {
		return fromCity;
	}

	public void setFromCity(String fromCity) {
		this.fromCity = fromCity;
	}

	public String getFromAddress() {
		return fromAddress;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	public String getToCity() {
		return toCity;
	}

	public void setToCity(String toCity) {
		this.toCity = toCity;
	}

	public String getToAddress() {
		return toAddress;
	}

	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getSeats() {
		return seats;
	}

	public void setSeats(int seats) {
		this.seats = seats;
	}

	public Route createRoute() {
		return null;
	}

}
