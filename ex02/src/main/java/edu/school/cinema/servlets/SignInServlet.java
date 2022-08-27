package edu.school.cinema.servlets;

import edu.school.cinema.models.User;
import edu.school.cinema.services.UserService;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@WebServlet(value = "/signIn", name = "SignIn", description = "Sing In")
public class SignInServlet extends HttpServlet {

    private UserService userService;
    @Override
    public void init() throws ServletException {
        super.init();

        ServletContext servletContext = getServletContext();
        ApplicationContext springContext = (ApplicationContext) servletContext.getAttribute("springContext");
        userService = springContext.getBean(UserService.class);

        System.out.println("init");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        request.getRequestDispatcher("/WEB-INF/html/signIn.html").forward(request,response);
        System.out.println("get");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String phoneNumber = request.getParameter("phoneNumber");
        String password = request.getParameter("password");

        try {
            User user = userService.getUserByPhoneNumber(phoneNumber);
            if (userService.isValidPassword(user, password)) {
                userService.authenticateUser(user, request.getRemoteAddr(), request.getSession().getId());
                request.getSession().setAttribute("user", user);
                request.getSession().setAttribute("userService", userService);
                response.sendRedirect("/profile");
            } else {
                response.sendRedirect("/");
            }
        } catch (Exception e) {
            response.sendRedirect("/");
        }
    }
}