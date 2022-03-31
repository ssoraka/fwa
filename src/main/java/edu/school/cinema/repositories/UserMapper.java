package edu.school.cinema.repositories;

import edu.school.cinema.models.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

class UserMapper implements RowMapper<User> {

    public User mapRow(ResultSet resultSet, int i) throws SQLException {

        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setFirstName(resultSet.getString("first_name"));
        user.setLastName(resultSet.getString("last_name"));
        user.setPhoneNumber(resultSet.getString("phone_number"));
        user.setPassword(resultSet.getString("password"));
        return user;
    }
}
