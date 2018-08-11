package com.mukul.mvc.repository;

import com.mukul.mvc.domain.User;
import com.mukul.mvc.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.defaultString;

public class UserRepository {

    private static UserRepository userRepository;
    @Autowired
    protected static NamedParameterJdbcTemplate jdbcTemplate;

    public synchronized static UserRepository getInstance() {
        if (userRepository == null) {
            userRepository = new UserRepository();
            userRepository.setJdbcTemplate(jdbcTemplate);
        }
        return userRepository;
    }

    public User findbyUserId(int userId) {
        String sql = "SELECT * " +
                " FROM \"USER_DETAILS\" " +
                " WHERE USER_ID = :userId " +
                "  AND O.ACTIVE_IND = 1 FOR READ ONLY WITH UR";

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("userId", userId);
        return jdbcTemplate.queryForObject(sql, parameters, User.class);
    }

    public int addUser(User user) {
        String customerSql = "INSERT INTO USER " +
                "(SEQ_NUM, USER_ID, FRST_NM, LST_NM, ADDR1_TXT, ADDR2_TXT, CITY_NM, STATE_NM, POSTAL_CD, COUNTRY_CD, EMAIL_ADDR) " +
                "VALUES " +
                "((SELECT MAX(PEND_CUST_SEQ_NUM) + 1 FROM PENDING_CUSTOMER), :userId, :firstName, :lastName, :streetAddressLine1, :streetAddressLine2, :city, :state, :zipCode, :country, :email)";
        HashMap<String, Object> sqlParameters = populateParametersFrom(user);
        sqlParameters.put("userId", user.getCustomerId());
        return jdbcTemplate.update(customerSql, sqlParameters);
    }

    public User findByUsername(String username) throws Exception {
        String customerSql = "SELECT PEND_CUST_USER_ID,PEND_CUST_FRST_NM, PEND_CUST_LST_NM, CUST_ST_ADDR1_TXT, CUST_ST_ADDR2_TXT, CITY_NM, STATE_NM, POSTAL_CD, COUNTRY_CD, PENDCUST_EMAIL_TXT,VAT_CD," +
                "CORPORATION_1_NM FROM PENDING_CUSTOMER WHERE PEND_CUST_USER_ID = :userId FETCH FIRST ROW ONLY WITH UR ";
        HashMap<String, Object> sqlParameters = new HashMap<String, Object>();
        sqlParameters.put("userId", username);
        try {
            return (User) jdbcTemplate.queryForObject(customerSql, sqlParameters, new UserMapper());
        } catch (DataAccessException e) {
            throw new Exception();
        }
    }

    private HashMap<String, Object> populateParametersFrom(User user) {
        HashMap<String, Object> sqlParameters = new HashMap<String, Object>();
        sqlParameters.put("firstName", defaultString(user.getFirstName()));
        sqlParameters.put("lastName", defaultString(user.getLastName()));
        sqlParameters.put("streetAddressLine1", defaultString(user.getAddressLine1()));
        sqlParameters.put("streetAddressLine2", defaultString(user.getAddressLine2()));
        sqlParameters.put("city", defaultString(user.getCity()));
        sqlParameters.put("state", defaultString(user.getState()));
        sqlParameters.put("zipCode", defaultString(user.getPostalCode()));
        sqlParameters.put("country", defaultString(user.getCountry()));
        sqlParameters.put("email", defaultString(user.getEmailAddress()));
        return sqlParameters;
    }

    public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}

