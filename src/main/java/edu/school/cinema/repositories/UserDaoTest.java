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
    public boolean createUser(User user) {
        users.add(user);
        return true;
    }
}
