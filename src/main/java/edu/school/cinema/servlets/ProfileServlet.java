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
import java.io.PrintWriter;

@WebServlet("/profile/*")
public class ProfileServlet extends HttpServlet {

    UserDao dao;

    @Override
    public void init() throws ServletException {
        super.init();

        ServletContext servletContext = getServletContext();
        ApplicationContext springContext = (ApplicationContext) servletContext.getAttribute("springContext");
        dao = springContext.getBean(UserDao.class);

        System.out.println("init");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        Long id = Long.parseLong(request.getRequestURI().substring("/profile/".length()));
        User user = dao.getUserById(id);

        try (PrintWriter writer = response.getWriter()) {
            writer.println("<!DOCTYPE html><html>");
            writer.println("<head>");
            writer.println("<meta charset=\"UTF-8\" />");
            writer.println("<title>Profile</title>");
            writer.println("</head>");
            writer.println("<body>");

            writer.println("<h1>firstname " + user.getFirstName() + ".</h1>");
            writer.println("<h1>lastname " + user.getLastName()  + ".</h1>");
            if (user.getPhoneNumber() != null) {
                writer.println("<h1>phone number " + user.getPhoneNumber() + ".</h1>");
            }

            writer.println("</body>");
            writer.println("</html>");
        }

        System.out.println("get");
    }
}
