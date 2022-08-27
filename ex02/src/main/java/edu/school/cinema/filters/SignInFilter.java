package edu.school.cinema.filters;

import edu.school.cinema.models.User;
import edu.school.cinema.repositories.UserDao;
import edu.school.cinema.services.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = {"/signIn"})
public class SignInFilter implements Filter {
    private UserService userService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);

        ServletContext servletContext = filterConfig.getServletContext();
        ApplicationContext springContext = (ApplicationContext) servletContext.getAttribute("springContext");
        userService = springContext.getBean(UserService.class);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest rq = (HttpServletRequest)request;
        HttpServletResponse rs = (HttpServletResponse)response;

        if (rq.getSession().getAttribute("user") != null) {
            rs.sendRedirect("/profile");
            return;
        }

        if (!rq.getMethod().equals("POST")){
            chain.doFilter(request, response);
            return;
        }

        if (request.getParameter("phoneNumber").isEmpty() || request.getParameter("password").isEmpty()) {
            rs.sendRedirect(rq.getRequestURI());
        } else {
            chain.doFilter(request, response);
        }

    }
}
