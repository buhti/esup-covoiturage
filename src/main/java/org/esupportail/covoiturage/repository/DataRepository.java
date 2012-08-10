package org.esupportail.covoiturage.repository;

import java.util.Collection;
import java.util.Map;

public interface DataRepository {

    Map<Integer, String> getAvailableSeats();
    Map<Integer, String> getDateTolerances();
    Map<Integer, String> getDistanceTolerances();
    Map<String, String> getPredefinedLocations();
    String getPredefinedLocationsJSON();

    Collection<Integer> getDays();
    Map<Integer, String> getMonths();
    Collection<Integer> getYears();
    Collection<String> getHoursAndMinutes();
    Map<Integer, String> getWeekDays();

}
