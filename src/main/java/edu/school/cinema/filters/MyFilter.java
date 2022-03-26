package edu.school.cinema.filters;

import edu.school.cinema.models.User;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = {"/signUp"})
public class MyFilter implements Filter {
    PasswordEncoder encoder;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);

        ServletContext servletContext = filterConfig.getServletContext();
        ApplicationContext springContext = (ApplicationContext) servletContext.getAttribute("springContext");
        encoder = springContext.getBean(PasswordEncoder.class);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest rq = (HttpServletRequest)request;

        if (rq.getMethod().equals("POST") && (
                request.getParameter("firstName").isEmpty() || request.getParameter("lastName").isEmpty() ||
                request.getParameter("phoneNumber").isEmpty() || request.getParameter("password").isEmpty())) {
            ((HttpServletResponse) response).sendRedirect("/webProject_war/signUp");
        } else {
            chain.doFilter(request, response);
        }
        System.out.println("doFilter");
    }
}
