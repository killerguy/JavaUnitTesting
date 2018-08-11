package com.mukul.mvc.mapper;

import com.mukul.mvc.domain.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper {

    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        User user = new User();
        user.setFirstName(resultSet.getString("FRST_NM"));
        user.setLastName(resultSet.getString("LST_NM"));
        user.setAddressLine1(resultSet.getString("ADDR1_TXT"));
        user.setAddressLine2(resultSet.getString("CADDR2_TXT"));
        user.setCity(resultSet.getString("CITY_NM"));
        user.setState(resultSet.getString("STATE_NM"));
        user.setPostalCode(resultSet.getString("POSTAL_CD"));
        user.setCountry(resultSet.getString("COUNTRY_CD"));
        user.setEmailAddress(resultSet.getString("EMAIL_ADDR"));
        return user;
    }
}
