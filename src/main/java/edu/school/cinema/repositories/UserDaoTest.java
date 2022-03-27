package edu.school.cinema.repositories;

import edu.school.cinema.models.User;

import java.util.ArrayList;
import java.util.List;

public class UserDaoTest implements UserDao {

    List<User> users = new ArrayList<>();

    @Override
    public User getUserById(Long id) {
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        return users;
    }

    @Override
    public boolean deleteUser(User user) {
        if (!users.contains(user)) {
            return false;
        }
        users.remove(user);
        return true;
    }

    @Override
    public boolean updateUser(User user) {
        return false;
    }

    @Override
    public User createUser(User user) {
        users.add(user);
        user.setId((long)users.size());
        return user;
    }

    @Override
    public User getUserByFirstNameLastNamePassword(String firstName, String lastName, String password) {
        return users.stream()
                .filter(u -> u.getFirstName().equals(firstName) && u.getLastName().equals(lastName) && u.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }
}
