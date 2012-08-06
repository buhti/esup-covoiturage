package org.esupportail.covoiturage.repository;

import java.util.List;

import javax.annotation.Resource;

import org.esupportail.covoiturage.domain.Location;
import org.esupportail.covoiturage.domain.Route;
import org.esupportail.covoiturage.domain.RouteOccasional;
import org.esupportail.covoiturage.domain.RouteRecurrent;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class JdbcRouteRepository implements RouteRepository {

    private static final Integer EQUATOR_CIRCUMFERENCE = 6371000;
    private static final Integer POLAR_CIRCUMFERENCE = 6356800;

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private JdbcRouteMapper routeMapper;

    @Transactional
    @Override
    public long createRoute(Route route) {
        if (route.isRecurrent()) {
            RouteRecurrent r = (RouteRecurrent) route;
            jdbcTemplate.update(INSERT_ROUTE_RECURRENT, 
                    r.getOwner().getId(), r.isDriver(), r.getSeats(), r.getDistance(),
                    locationToSqlPoint(r.getFrom()), r.getFrom().getCity(), r.getFrom().getAddress(),
                    locationToSqlPoint(r.getTo()), r.getTo().getCity(), r.getTo().getAddress(),
                    r.getStartDate().toDate(), r.getEndDate().toDate(),
                    r.getWayOutTime(), r.getWayBackTime());
        } else {
            RouteOccasional r = (RouteOccasional) route;
            jdbcTemplate.update(INSERT_ROUTE_OCCASIONAL,
                    r.getOwner().getId(), r.isDriver(), r.getSeats(), r.getDistance(),
                    locationToSqlPoint(r.getFrom()), r.getFrom().getCity(), r.getFrom().getAddress(),
                    locationToSqlPoint(r.getTo()), r.getTo().getCity(), r.getTo().getAddress(),
                    r.getWayOutDate().toDate(), r.getWayBackDate().toDate());
        }

        return jdbcTemplate.queryForLong(SELECT_LAST_INSERT_ID, route.getOwner().getId());
    }

    @Override
    public Route findOneById(long id) {
        return jdbcTemplate.queryForObject(SELECT_ROUTE_BY_ID, routeMapper, id);
    }

    public List<Route> findByTolerance(double lat, double lng, int meters) {
        final double latRadian = lat * Math.PI / 360;

        final double latMetersPerDegree = meters * 360.0 / (Math.cos(latRadian) * EQUATOR_CIRCUMFERENCE);
        final double lngMetersPerDegree = meters * 360.0 / POLAR_CIRCUMFERENCE;

        final double minLat = lat - latMetersPerDegree; // South
        final double maxLat = lat + latMetersPerDegree; // North
        final double minLng = lng - lngMetersPerDegree; // West
        final double maxLng = lng + lngMetersPerDegree; // East

        return null;
    }

    private String locationToSqlPoint(Location location) {
        return "POINT(" + location.getLat() + " " + location.getLng() + ")";
    }

    private static final String INSERT_ROUTE_RECURRENT = 
            "INSERT INTO Route (owner_id, driver, seats, distance, from_point, from_city, from_address, to_point, to_city, to_address, recurrent, start_date, end_date, wayout_time, wayback_time) " +
            "VALUES (?, ?, ?, ?, GeomFromText(?), ?, ?, GeomFromText(?), ?, ?, 1, ?, ?, ?, ?)";
    
    private static final String INSERT_ROUTE_OCCASIONAL = 
            "INSERT INTO Route (owner_id, driver, seats, distance, from_point, from_city, from_address, to_point, to_city, to_address, recurrent, wayout_date, wayback_date) " +
            "VALUES (?, ?, ?, ?, GeomFromText(?), ?, ?, GeomFromText(?), ?, ?, 0, ?, ?)";

    private static final String SELECT_LAST_INSERT_ID = 
            "SELECT r.route_id FROM Route r " +
            "WHERE r.owner_id = ? " +
            "ORDER BY r.route_id DESC " +
            "LIMIT 1";

    private static final String SELECT_ROUTE = 
            "SELECT r.route_id, r.driver, r.seats, r.distance, r.from_city, r.from_address, r.to_city, r.to_address, r.recurrent, " +
            "AsText(r.from_point) AS from_point_text, AsText(r.to_point) AS to_point_text, " +
            "r.start_date, r.end_date, r.wayout_time, r.wayback_time, r.wayout_date, r.wayback_date, " +
            "c.customer_id, c.login, c.email, c.firstname, c.lastname " +
            "FROM Route r " +
            "INNER JOIN Customer c ON r.owner_id = c.customer_id ";

    private static final String SELECT_ROUTE_BY_ID = SELECT_ROUTE +
            "WHERE r.route_id = ?";

}
