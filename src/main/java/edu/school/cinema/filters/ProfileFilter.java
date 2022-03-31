package edu.school.cinema.filters;

import edu.school.cinema.models.User;
import edu.school.cinema.repositories.UserDao;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebFilter(urlPatterns = {"/profile"})
public class ProfileFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("doFilter");
        HttpServletRequest rq = (HttpServletRequest)request;
        HttpServletResponse rs = (HttpServletResponse)response;

        if (request.getAttribute("id") == null || !(request.getAttribute("id") instanceof Long)) {
            rs.sendError(403);
        } else {
            chain.doFilter(request, response);
        }
    }
}
