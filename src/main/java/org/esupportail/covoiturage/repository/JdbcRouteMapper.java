package org.esupportail.covoiturage.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.esupportail.covoiturage.domain.Customer;
import org.esupportail.covoiturage.domain.Location;
import org.esupportail.covoiturage.domain.Route;
import org.esupportail.covoiturage.domain.RouteOccasional;
import org.esupportail.covoiturage.domain.RouteRecurrent;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class JdbcRouteMapper implements RowMapper<Route> {

    @Resource
    private JdbcCustomerMapper customerMapper;

    @Override
    public Route mapRow(ResultSet rs, int rowNum) throws SQLException {
        Customer owner = customerMapper.mapRow(rs, rowNum);
        Location from = mapLocation(rs, "from");
        Location to = mapLocation(rs, "to");
        long id = rs.getLong("route_id");
        boolean driver = rs.getBoolean("driver");
        int seats = rs.getInt("seats");
        int distance = rs.getInt("distance");

        Route route;
        if (rs.getBoolean("recurrent")) {
            route = new RouteRecurrent(id, owner, driver, seats, from, to, distance,
                    new DateTime(rs.getDate("start_date").getTime()),
                    new DateTime(rs.getDate("end_date").getTime()),
                    mapTime(rs.getString("wayout_time")), mapTime(rs.getString("wayback_time")),
                    mapDays(rs.getString("week_days")));
        } else {
            route = new RouteOccasional(id, owner, driver, seats, from, to, distance,
                    new DateTime(rs.getTimestamp("wayout_date").getTime()),
                    new DateTime(rs.getTimestamp("wayback_date").getTime()));
        }

        return route;
    }

    private Location mapLocation(ResultSet rs, String columnLabel) throws SQLException {
        double[] latlng = mapPoint(rs.getString(columnLabel + "_point_text"));
        return new Location(latlng[0], latlng[1], rs.getString(columnLabel + "_city"), rs.getString(columnLabel + "_address"));
    }

    private double[] mapPoint(String point) {
        Pattern p = Pattern.compile("(-?[0-9.]+) (-?[0-9.]+)");
        Matcher m = p.matcher(point);

        if (!m.find()) {
            throw new RuntimeException("Invalid point");
        }

        return new double[] { Double.parseDouble(m.group(1)), Double.parseDouble(m.group(2)) };
    }

    private LocalTime mapTime(String time) {
        int hours = Integer.parseInt(time.split(":", 2)[0]);
        int minutes = Integer.parseInt(time.split(":", 2)[1]);
        return new LocalTime(hours, minutes, 0);
    }

    private int[] mapDays(String str) {
        int i = 0;
        int n = str.length();
        int[] weekDays = new int[n];

        for (; i < n; i++) {
            weekDays[i] = Integer.parseInt(Character.toString(str.charAt(i)));
        }

        return weekDays;
    }

}
