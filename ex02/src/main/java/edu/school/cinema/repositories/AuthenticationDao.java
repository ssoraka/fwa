package edu.school.cinema.repositories;

import edu.school.cinema.models.Authentication;

import java.util.List;

public interface AuthenticationDao {
    List<Authentication> getAuthenticationsByUserId(Long userId);
    Authentication addAuthentication(Authentication auth);
    boolean deleteAuthenticationBySession(String session);
}
