package edu.school.cinema.servlets;

import edu.school.cinema.models.User;
import edu.school.cinema.repositories.UserDao;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/signIn", name = "SignIn", description = "Sing In")
public class SignInServlet extends HttpServlet {

    UserDao dao;
    PasswordEncoder encoder;

    @Override
    public void init() throws ServletException {
        super.init();

        ServletContext servletContext = getServletContext();
        ApplicationContext springContext = (ApplicationContext) servletContext.getAttribute("springContext");
        dao = springContext.getBean(UserDao.class);
        encoder = springContext.getBean(PasswordEncoder.class);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        request.getRequestDispatcher("/WEB-INF/html/signIn.html").forward(request,response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String phoneNumber = request.getParameter("phoneNumber");
        String password = request.getParameter("password");

        try {
            User user = dao.getUserByPhoneNumber(phoneNumber);
            if (encoder.matches(password, user.getPassword())) {
                request.getSession().setAttribute("user", user);
                response.sendRedirect("/profile");
            } else {
                response.sendRedirect("/");
            }
        } catch (Exception e) {
            response.sendRedirect("/");
        }
    }
}