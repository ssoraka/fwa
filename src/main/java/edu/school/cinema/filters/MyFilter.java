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

@WebFilter(urlPatterns = {"/signIn", "/signUp"})
public class MyFilter implements Filter {
    PasswordEncoder encoder;
    UserDao dao;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);

        ServletContext servletContext = filterConfig.getServletContext();
        ApplicationContext springContext = (ApplicationContext) servletContext.getAttribute("springContext");
        encoder = springContext.getBean(PasswordEncoder.class);
        dao = springContext.getBean(UserDao.class);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("doFilter");
        HttpServletRequest rq = (HttpServletRequest)request;
        HttpServletResponse rs = (HttpServletResponse)response;

        if (!rq.getMethod().equals("POST")){
            chain.doFilter(request, response);
            return;
        }

        User user = new User();
        user.setFirstName(request.getParameter("firstName"));
        user.setLastName(request.getParameter("lastName"));
        user.setPhoneNumber(request.getParameter("phoneNumber"));
        user.setPassword(encoder.encode(request.getParameter("password")));

        if (request.getParameter("firstName").isEmpty()
                || request.getParameter("lastName").isEmpty()
                || request.getParameter("password").isEmpty()) {
            rs.sendRedirect(rq.getRequestURI());
        } else {
            chain.doFilter(request, response);
        }

    }
}
