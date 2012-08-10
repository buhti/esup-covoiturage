package org.esupportail.covoiturage.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

@Repository
public class StaticDataRepository implements DataRepository {

    @Resource(name = "availableSeats")
    private Map<Integer, String> availableSeats;

    @Resource(name = "dateTolerances")
    private Map<Integer, String> dateTolerances;

    @Resource(name = "distanceTolerances")
    private Map<Integer, String> distanceTolerances;

    @Resource(name = "predefinedLocations")
    private Map<String, String> predefinedLocations;

    private final Collection<Integer> days;
    private final Map<Integer, String> months;
    private final Collection<Integer> years;
    private final Collection<String> hoursAndMinutes;
    private final Map<Integer, String> weekDays;

    public StaticDataRepository() {
        DateTime today = DateTime.now();

        days = range(1, 31);
        months = new LinkedHashMap<Integer, String>();
        years = range(today.getYear(), today.getYear() + 3);
        hoursAndMinutes = new ArrayList<String>();
        weekDays = new LinkedHashMap<Integer, String>();

        for (int i = 1; i <= 12; i++) {
            months.put(i, today.withMonthOfYear(i).monthOfYear().getAsText(new Locale("fr")));
        }

        for (int i = 0; i <= 23; i++) {
            String hour = i < 10 ? "0" + i : Integer.toString(i);
            hoursAndMinutes.add(hour + ":00");
            hoursAndMinutes.add(hour + ":30");
        }

        for (int i = 1; i <= 7; i++) {
            weekDays.put(i, today.withDayOfWeek(i).dayOfWeek().getAsShortText(new Locale("fr")));
        }
    }

    @Override
    public Map<Integer, String> getAvailableSeats() {
        return availableSeats;
    }

    @Override
    public Map<Integer, String> getDateTolerances() {
        return dateTolerances;
    }

    @Override
    public Map<Integer, String> getDistanceTolerances() {
        return distanceTolerances;
    }

    @Override
    public Map<String, String> getPredefinedLocations() {
        return predefinedLocations;
    }

    @Override
    public Collection<Integer> getDays() {
        return days;
    }

    @Override
    public Map<Integer, String> getMonths() {
        return months;
    }

    @Override
    public Collection<Integer> getYears() {
        return years;
    }

    @Override
    public Collection<String> getHoursAndMinutes() {
        return hoursAndMinutes;
    }

    @Override
    public Map<Integer, String> getWeekDays() {
        return weekDays;
    }

    private static List<Integer> range(int start, int end) {
        List<Integer> range = new ArrayList<Integer>(end - start + 1);
        for (int i = start; i <= end; i++) {
            range.add(i);
        }
        return range;
    }

}
