package edu.school.cinema.repositories;

import edu.school.cinema.models.Authentication;
import edu.school.cinema.models.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

class AuthenticationMapper implements RowMapper<Authentication> {

    @Override
    public Authentication mapRow(ResultSet resultSet, int i) throws SQLException {
        Authentication user = new Authentication();
        user.setId(resultSet.getLong("id"));
        user.setIp(resultSet.getString("ip"));
        user.setDate(resultSet.getDate("date"));
        user.setSession(resultSet.getString("session"));
        return user;
    }
}
