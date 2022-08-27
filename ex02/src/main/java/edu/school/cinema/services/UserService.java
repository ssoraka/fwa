package edu.school.cinema.services;

import edu.school.cinema.models.Authentication;
import edu.school.cinema.models.User;
import edu.school.cinema.repositories.AuthenticationDao;
import edu.school.cinema.repositories.UserDao;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

public class UserService {
    UserDao userDao;
    AuthenticationDao authDao;
    PasswordEncoder encoder;

    public UserService(UserDao userDao, AuthenticationDao authDao, PasswordEncoder encoder) {
        this.userDao = userDao;
        this.authDao = authDao;
        this.encoder = encoder;
    }

    public void createUser(String firstName, String lastName, String phoneNumber, String password) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPhoneNumber(phoneNumber);
        user.setPassword(encoder.encode(password));
        userDao.createUser(user);
    }

    public User getUserByPhoneNumber(String phoneNumber) {
        User user = userDao.getUserByPhoneNumber(phoneNumber);
        user.setAuthentications(authDao.getAuthenticationsByUserId(user.getId()));
        return user;
    }
    public boolean isValidPassword(User user, String password) {
        return encoder.matches(password, user.getPassword());
    }

    public void authenticateUser(User user, String ip, String sessionId) {
        if (ip.equals("0:0:0:0:0:0:0:1")) {
            ip = "localhost";
        }
        Authentication auth = new Authentication();
        auth.setDate(new Date());
        auth.setUser(user);
        auth.setIp(ip);
        auth.setSession(sessionId);
        auth = authDao.addAuthentication(auth);
        user.getAuthentications().add(auth);
    }
}
