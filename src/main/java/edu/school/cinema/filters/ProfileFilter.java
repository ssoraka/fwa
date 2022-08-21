package edu.school.cinema.filters;

import edu.school.cinema.models.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = {"/profile", "/profile/\\d"})
public class ProfileFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest rq = (HttpServletRequest)request;
        HttpServletResponse rs = (HttpServletResponse)response;

        if (rq.getRequestURI().matches("/profile/\\d")) {
            chain.doFilter(request, response);
        } else if (rq.getSession().getAttribute("user") != null && rq.getSession().getAttribute("user") instanceof User) {
            chain.doFilter(request, response);
        } else {
            rs.sendError(HttpServletResponse.SC_FORBIDDEN);
        }
    }
}
