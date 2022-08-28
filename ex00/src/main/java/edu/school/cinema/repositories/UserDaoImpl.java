package edu.school.cinema.repositories;

import edu.school.cinema.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.util.List;

public class UserDaoImpl implements UserDao {
    private final String SQL_FIND_PERSON = "select * from users where id = ?";
    private final String SQL_DELETE_PERSON = "delete from users where id = ?";
    private final String SQL_UPDATE_PERSON = "update users set first_name = ?, last_name = ?, phone_number = ? where id = ?";
    private final String SQL_GET_ALL = "select * from users";

    private final String SQL_INSERT_PERSON = "insert into users(first_name, last_name, phone_number, password) values(?,?,?,?) returning id";
    private final String SQL_FIND_BY_PHONENUMBER = "select * from users where phone_number = ? ";

    private JdbcTemplate jdbcTemplate;
    private RowMapper<User> mapper = new UserMapper();

    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public User getUserById(Long id) {
        return jdbcTemplate.queryForObject(SQL_FIND_PERSON, new Object[] { id }, new UserMapper());
    }

    public List<User> getAllUsers() {
        return jdbcTemplate.query(SQL_GET_ALL, new UserMapper());
    }

    public boolean deleteUser(User person) {
        return jdbcTemplate.update(SQL_DELETE_PERSON, person.getId()) > 0;
    }

    public boolean updateUser(User person) {
        return jdbcTemplate.update(SQL_UPDATE_PERSON, person.getFirstName(), person.getLastName(), person.getPhoneNumber(),
                person.getId()) > 0;
    }

    public User createUser(User person) {
        Long id = jdbcTemplate.queryForObject(SQL_INSERT_PERSON, Long.class, person.getFirstName(), person.getLastName(), person.getPhoneNumber(), person.getPassword());
        person.setId(id);
        return person;
    }

    @Override
    public User getUserByPhoneNumber(String phoneNumber) {
        Object[] args = new Object[]{phoneNumber};
        return jdbcTemplate.query(SQL_FIND_BY_PHONENUMBER, args, mapper).get(0);
    }
}