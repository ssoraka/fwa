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

@WebServlet("/signIn")
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

        System.out.println("init");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        request.getRequestDispatcher("/WEB-INF/html/signIn.html").forward(request,response);
        System.out.println("get");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String password = request.getParameter("password");

        try {
            User user = dao.getUserByFirstNameLastNamePassword(firstName, lastName);
            if (encoder.matches(password, user.getPassword())) {
                response.sendRedirect("/profile/" + user.getId());
            } else {
                response.sendError(403);
            }
        } catch (Exception e) {
            response.sendError(403);
        }
    }
}
//$2a$10$gmocb65ocMlENubErnP1V.Sy3VJLT7ZPnBXvJspfmqRoWIcMobN4u
//$2a$10$3w5MBE0hAt5iDVDBY6cfnuu4Ii9yuIRmF5j/MDqp/WZvUKN5AL3ja