package org.esupportail.covoiturage.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.esupportail.covoiturage.domain.Location;
import org.esupportail.covoiturage.domain.Route;
import org.esupportail.covoiturage.domain.RouteOccasional;
import org.esupportail.covoiturage.domain.RouteRecurrent;
import org.esupportail.covoiturage.exception.RouteNotFoundException;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Ce service permet de manipuler les informations relatives aux trajets.
 *
 * @author Florent Cailhol (Anyware Services)
 */
@Repository
public class JdbcRouteRepository implements RouteRepository {

    private static final Integer EQUATOR_CIRCUMFERENCE = 6371000;
    private static final Integer POLAR_CIRCUMFERENCE = 6356800;

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private JdbcRouteMapper routeMapper;

    @Value("${app.search.maximumResults}")
    private int maximumResults;

    @Transactional
    @Override
    public long createRoute(Route route) {
        if (route.isRecurrent()) {
            RouteRecurrent r = (RouteRecurrent) route;
            jdbcTemplate.update(INSERT_ROUTE_RECURRENT,
                    r.getOwner().getId(), r.isDriver(), r.isLadiesOnly(), r.getSeats(), r.getDistance(),
                    locationToSqlPoint(r.getFrom()), r.getFrom().getCity(), r.getFrom().getAddress(),
                    locationToSqlPoint(r.getTo()), r.getTo().getCity(), r.getTo().getAddress(),
                    r.getStartDate().toDate(), r.getEndDate().toDate(), r.isRoundTrip(),
                    r.getWayOutTime().toString("HH:mm"), r.isRoundTrip() ? r.getWayBackTime().toString("HH:mm") : null,
                    weekDaysToString(r.getWeekDays()));
        } else {
            RouteOccasional r = (RouteOccasional) route;
            jdbcTemplate.update(INSERT_ROUTE_OCCASIONAL,
                    r.getOwner().getId(), r.isDriver(), r.isLadiesOnly(), r.getSeats(), r.getDistance(),
                    locationToSqlPoint(r.getFrom()), r.getFrom().getCity(), r.getFrom().getAddress(),
                    locationToSqlPoint(r.getTo()), r.getTo().getCity(), r.getTo().getAddress(),
                    r.isRoundTrip(), r.getWayOutDate().toDate(),
                    r.isRoundTrip() ? r.getWayBackDate().toDate() : null);
        }

        return jdbcTemplate.queryForLong(SELECT_LAST_INSERT_ID, route.getOwner().getId());
    }

    @Override
    public void updateRoute(long ownerId, long routeId, Route route) {
        if (route.isRecurrent()) {
            RouteRecurrent r = (RouteRecurrent) route;
            String wayBackTime = r.isRoundTrip() ? r.getWayBackTime().toString("HH:mm") : null;
            jdbcTemplate.update(UPDATE_ROUTE,
                    r.isDriver(), r.isLadiesOnly(), r.getSeats(), r.getDistance(),
                    locationToSqlPoint(r.getFrom()), r.getFrom().getCity(), r.getFrom().getAddress(),
                    locationToSqlPoint(r.getTo()), r.getTo().getCity(), r.getTo().getAddress(),
                    1, r.isRoundTrip(), r.getStartDate().toDate(), r.getEndDate().toDate(),
                    r.getWayOutTime().toString("HH:mm"), wayBackTime,
                    weekDaysToString(r.getWeekDays()), null, null, routeId, ownerId);
        } else {
            RouteOccasional r = (RouteOccasional) route;
            Date wayBackDate = r.isRoundTrip() ? r.getWayBackDate().toDate() : null;
            jdbcTemplate.update(UPDATE_ROUTE,
                    r.isDriver(), r.isLadiesOnly(), r.getSeats(), r.getDistance(),
                    locationToSqlPoint(r.getFrom()), r.getFrom().getCity(), r.getFrom().getAddress(),
                    locationToSqlPoint(r.getTo()), r.getTo().getCity(), r.getTo().getAddress(),
                    0, r.isRoundTrip(), null, null, null, null, null,
                    r.getWayOutDate().toDate(), wayBackDate, routeId, ownerId);
        }
    }

    @Override
    public void deleteRoute(long ownerId, long routeId) {
        jdbcTemplate.update(DELETE_ROUTE, routeId, ownerId);
    }

    @Override
    public Route findOneById(long routeId) throws RouteNotFoundException {
        try {
            return jdbcTemplate.queryForObject(SELECT_ROUTE_BY_ID, routeMapper, routeId);
        } catch (EmptyResultDataAccessException e) {
            throw new RouteNotFoundException(routeId);
        }
    }

    @Override
    public List<Route> findRoutesByTolerance(Location from, int fromTolerence, Location to, int toTolerence, DateTime date, int dateTolerence) {
        Date intervalLow = date.minuteOfHour().addToCopy(-dateTolerence).toDate();
        Date intervalHigh = date.minuteOfHour().addToCopy(dateTolerence).toDate();

        String fromPolygon = locationToSqlPolygon(from, fromTolerence);
        String toPolygon = locationToSqlPolygon(to, toTolerence);

        List<Route> occasionalRoutes = jdbcTemplate.query(SELECT_FIND_OCCASIONAL_ROUTES, routeMapper,
                intervalLow, intervalHigh, fromPolygon, toPolygon,
                maximumResults);

        List<Route> recurrentRoutes = jdbcTemplate.query(SELECT_FIND_RECURRENT_ROUTES, routeMapper,
                date.toDate(), date.toDate(), fromPolygon, toPolygon,
                maximumResults - occasionalRoutes.size());

        List<Route> routes = new ArrayList<Route>();
        routes.addAll(occasionalRoutes);
        routes.addAll(recurrentRoutes);
        return routes;
    }

    @Override
    public List<Route> findRoutesByOwner(long ownerId) {
        return jdbcTemplate.query(SELECT_ROUTE_BY_OWNER, routeMapper, ownerId);
    }

    @Override
    public void deleteExpiredRecurrentRoutes(int days) {
        jdbcTemplate.update(DELETE_EXPIRED_RECURRENT_ROUTES, DateTime.now().minusDays(days).toDate());
    }

    @Override
    public void deleteExpiredOccasionalRoutes(int days) {
        jdbcTemplate.update(DELETE_EXPIRED_OCCASIONAL_ROUTES, DateTime.now().minusDays(days).toDate());
    }

    @Override
    public List<Route> findNearlyExpiredRecurrentRoutes(int days) {
        return jdbcTemplate.query(SELECT_NEARLY_EXPIRED_RECURRENT_ROUTES, routeMapper, DateTime.now().minusDays(days).toDate());
    }

    @Override
    public void deleteRoutesByOwner(long ownerId) {
        jdbcTemplate.update(DELETE_ROUTES_BY_OWNER, ownerId);
    }

    private String locationToSqlPoint(Location location) {
        return "Point(" + location.getLat() + " " + location.getLng() + ")";
    }

    private String locationToSqlPolygon(Location location, int meters) {
        double lat = location.getLat();
        double lng = location.getLng();

        final double latRadian = lat * Math.PI / 360;

        final double latMetersPerDegree = meters * 360.0 / (Math.cos(latRadian) * EQUATOR_CIRCUMFERENCE);
        final double lngMetersPerDegree = meters * 360.0 / POLAR_CIRCUMFERENCE;

        final double minLat = lat - latMetersPerDegree; // South
        final double maxLat = lat + latMetersPerDegree; // North
        final double minLng = lng - lngMetersPerDegree; // West
        final double maxLng = lng + lngMetersPerDegree; // East

        return "Polygon((" + maxLat + " " + minLng + ", " + maxLat + " " + maxLng + ", " + minLat + " " + maxLng + ", " + minLat + " " + minLng + ", " + maxLat + " " + minLng + "))";
    }

    private String weekDaysToString(int[] weekDays) {
        int i = 0;
        int n = weekDays.length;

        StringBuilder sb = new StringBuilder(n);

        for (; i < n; i++) {
            sb.append(weekDays[i]);
        }

        return sb.toString();
    }

    private static final String INSERT_ROUTE_RECURRENT = "INSERT INTO Route (owner_id, driver, ladies_only, seats, distance, from_point, from_city, from_address, to_point, to_city, to_address, recurrent, start_date, end_date, round_trip, wayout_time, wayback_time, week_days) "
            + "VALUES (?, ?, ?, ?, ?, GeomFromText(?), ?, ?, GeomFromText(?), ?, ?, 1, ?, ?, ?, ?, ?, ?)";

    private static final String INSERT_ROUTE_OCCASIONAL = "INSERT INTO Route (owner_id, driver, ladies_only, seats, distance, from_point, from_city, from_address, to_point, to_city, to_address, recurrent, round_trip, wayout_date, wayback_date) "
            + "VALUES (?, ?, ?, ?, ?, GeomFromText(?), ?, ?, GeomFromText(?), ?, ?, 0, ?, ?, ?)";

    private static final String UPDATE_ROUTE = "UPDATE Route SET driver = ?, ladies_only = ?, seats = ?, distance = ?, from_point = GeomFromText(?), from_city = ?, from_address = ?, to_point = GeomFromText(?), to_city = ?, to_address = ?, recurrent = ?, round_trip = ?, "
            + "start_date = ?, end_date = ?, wayout_time = ?, wayback_time = ?, week_days = ?, "
            + "wayout_date = ?, wayback_date = ? "
            + "WHERE route_id = ? AND owner_id = ?";

    private static final String DELETE_ROUTE = "DELETE FROM Route WHERE route_id = ? AND owner_id = ?";

    private static final String SELECT_LAST_INSERT_ID = "SELECT r.route_id FROM Route r "
            + "WHERE r.owner_id = ? "
            + "ORDER BY r.route_id DESC LIMIT 1";

    private static final String SELECT_ROUTE = "SELECT r.*, c.*, "
            + "AsText(r.from_point) AS from_point_text, AsText(r.to_point) AS to_point_text "
            + "FROM Route r "
            + "INNER JOIN Customer c ON r.owner_id = c.customer_id ";

    private static final String SELECT_ROUTE_BY_ID = SELECT_ROUTE
            + "WHERE r.route_id = ?";

    private static final String SELECT_ROUTE_BY_OWNER = SELECT_ROUTE
            + "WHERE c.customer_id = ? "
            + "ORDER BY r.route_id DESC";

    private static final String SELECT_FIND_OCCASIONAL_ROUTES = SELECT_ROUTE
            + "WHERE r.recurrent = 0 "
            + "AND r.wayout_date >= ? AND r.wayout_date <= ? "
            + "AND MBRContains(GeomFromText(?), from_point) = 1 "
            + "AND MBRContains(GeomFromText(?), to_point) = 1 "
            + "ORDER BY r.wayout_date ASC "
            + "LIMIT ?";

    private static final String SELECT_FIND_RECURRENT_ROUTES = SELECT_ROUTE
            + "WHERE r.recurrent = 1 "
            + "AND r.start_date <= ? AND r.end_date >= ? "
            + "AND MBRContains(GeomFromText(?), from_point) = 1 "
            + "AND MBRContains(GeomFromText(?), to_point) = 1 "
            + "LIMIT ?";

    private static final String SELECT_NEARLY_EXPIRED_RECURRENT_ROUTES = SELECT_ROUTE
            + "WHERE r.recurrent = 1 AND r.end_date = ?";

    private static final String DELETE_EXPIRED_RECURRENT_ROUTES = "DELETE FROM Route "
            + "WHERE recurrent = 1 AND end_date <= ?";

    private static final String DELETE_EXPIRED_OCCASIONAL_ROUTES = "DELETE FROM Route "
            + "WHERE recurrent = 0 AND wayout_date <= ?";

    private static final String DELETE_ROUTES_BY_OWNER = "DELETE FROM Route WHERE owner_id = ?";

}
