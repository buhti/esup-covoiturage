package org.esupportail.covoiturage.repository;

import java.util.List;

import javax.annotation.Resource;

import org.esupportail.covoiturage.domain.Location;
import org.esupportail.covoiturage.domain.Route;

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
        jdbcTemplate.update(INSERT_ROUTE, route.getOwner().getId(), route.isDriver(), route.getSeats(),
                locationToSqlPoint(route.getFrom()), route.getFrom().getCity(), route.getFrom().getAddress(),
                locationToSqlPoint(route.getTo()), route.getTo().getCity(), route.getTo().getAddress(),
                route.isRecurrent());

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

    private static final String INSERT_ROUTE = "" +
            "INSERT INTO Route (owner_id, driver, seats, from_point, from_city, from_address, to_point, to_city, to_address, recurrent) " +
            "VALUES (?, ?, ?, GeomFromText(?), ?, ?, GeomFromText(?), ?, ?, ?)";

    private static final String SELECT_LAST_INSERT_ID = 
            "SELECT r.route_id FROM Route r " +
            "WHERE r.owner_id = ? " +
            "ORDER BY r.route_id DESC " +
            "LIMIT 1";

    private static final String SELECT_ROUTE = 
            "SELECT r.route_id, r.driver, r.seats, r.from_city, r.from_address, r.to_city, r.to_address, " +
            "c.customer_id, c.login, c.email, c.name " +
            "FROM Route r " +
            "INNER JOIN Customer c ON r.owner_id = c.customer_id ";

    private static final String SELECT_ROUTE_BY_ID = SELECT_ROUTE +
            "WHERE r.route_id = ?";

}
