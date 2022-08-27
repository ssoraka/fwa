package edu.school.cinema.repositories;

import edu.school.cinema.models.Authentication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

public class AuthenticationDaoImpl implements AuthenticationDao {

    private final String SQL_FIND_AUTH_BY_USER = "select * from authentications where user_id = ?";
    private final String SQL_INSERT_AUTH = "insert into authentications(ip, date, session, user_id) values(?,?,?,?) returning id";
    private final String SQL_DELETE_AUTH = "delete from authentications where session = ?";

    private JdbcTemplate jdbcTemplate;
    private RowMapper<Authentication> mapper = new AuthenticationMapper();


    public AuthenticationDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Authentication> getAuthenticationsByUserId(Long userId) {
        return jdbcTemplate.query(SQL_FIND_AUTH_BY_USER, ps -> ps.setLong(1, userId), mapper);
    }

    @Override
    public Authentication addAuthentication(Authentication auth) {
        Long id = jdbcTemplate.queryForObject(SQL_INSERT_AUTH, Long.class, auth.getIp(), auth.getDate(), auth.getSession(), auth.getUser().getId());
        auth.setId(id);
        return auth;
    }

    @Override
    public boolean deleteAuthenticationBySession(String session) {
        return jdbcTemplate.update(SQL_DELETE_AUTH, session) > 0;
    }
}
