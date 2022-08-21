package edu.school.cinema.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = {"/profile/*"})
public class ProfileFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("doFilter");
        HttpServletRequest rq = (HttpServletRequest)request;
        HttpServletResponse rs = (HttpServletResponse)response;

        if (!rq.getRequestURI().matches("/profile/\\d")) {
            rs.sendError(403);
        } else {
            chain.doFilter(request, response);
        }
    }
}
