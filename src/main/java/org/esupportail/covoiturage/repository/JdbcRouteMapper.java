package org.esupportail.covoiturage.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Resource;

import org.esupportail.covoiturage.domain.Customer;
import org.esupportail.covoiturage.domain.Location;
import org.esupportail.covoiturage.domain.Route;
import org.esupportail.covoiturage.domain.RouteOccasional;
import org.esupportail.covoiturage.domain.RouteRecurrent;

import org.joda.time.DateTime;
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
        int status = rs.getInt("status");
        int seats = rs.getInt("seats");

        if (rs.getBoolean("recurrent")) {
            return new RouteRecurrent(id, owner, status, seats, from, to,
                    new DateTime(rs.getDate("start_date").getTime()),
                    new DateTime(rs.getDate("end_date").getTime()),
                    rs.getString("wayout_time"), rs.getString("wayout_time"));
        }

        return new RouteOccasional(id, owner, status, seats, from, to, 
                new DateTime(rs.getDate("wayout_date").getTime()),
                new DateTime(rs.getDate("wayback_date").getTime()));
    }

    private Location mapLocation(ResultSet rs, String columnLabel) throws SQLException {
        double lat = 0D;
        double lng = 0D;
        return new Location(lat, lng, rs.getString(columnLabel + "_city"), rs.getString(columnLabel + "_address"));
    }

}
