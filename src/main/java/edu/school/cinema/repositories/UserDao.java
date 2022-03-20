package edu.school.cinema.repositories;

import edu.school.cinema.models.User;

import java.util.List;

public interface UserDao {
    User getUserById(Long id);

    List<User> getAllUsers();

    boolean deleteUser(User user);

    boolean updateUser(User user);

    boolean createUser(User user);
}
