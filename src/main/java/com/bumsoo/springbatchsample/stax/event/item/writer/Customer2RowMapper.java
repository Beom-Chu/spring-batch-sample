package com.bumsoo.springbatchsample.stax.event.item.writer;

import com.bumsoo.springbatchsample.dto.Customer2;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Customer2RowMapper implements RowMapper<Customer2> {
    @Override
    public Customer2 mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Customer2(rs.getLong("id")
        ,rs.getString("firstName")
        ,rs.getString("lastName")
        ,rs.getString("birthDate"));
    }
}
