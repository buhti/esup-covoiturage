package org.esupportail.covoiturage.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Resource;

import org.esupportail.covoiturage.domain.Customer;
import org.esupportail.covoiturage.domain.Location;
import org.esupportail.covoiturage.domain.Route;

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
        return new Route(rs.getLong("route_id"), owner, rs.getInt("status"), rs.getInt("seats"), from, to);
    }

    private Location mapLocation(ResultSet rs, String columnLabel) throws SQLException {
        double lat = 0D;
        double lng = 0D;
        return new Location(lat, lng, rs.getString(columnLabel + "_city"), rs.getString(columnLabel + "_address"));
    }

}
