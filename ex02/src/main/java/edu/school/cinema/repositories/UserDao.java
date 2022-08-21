package edu.school.cinema.repositories;

import edu.school.cinema.models.User;

import java.util.List;

public interface UserDao {
    User getUserById(Long id);
    User getUserByPhoneNumber(String phoneNumber);

    List<User> getAllUsers();

    boolean deleteUser(User user);

    boolean updateUser(User user);

    User createUser(User user);
}
