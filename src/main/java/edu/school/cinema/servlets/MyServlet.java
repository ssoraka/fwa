package edu.school.cinema.servlets;

import edu.school.cinema.repositories.UserDao;
import edu.school.cinema.repositories.UserDaoTest;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/users")
public class MyServlet extends HttpServlet {

    int counter;

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


        try (PrintWriter writer = response.getWriter()) {
            writer.println("<!DOCTYPE html><html>");
            writer.println("<head>");
            writer.println("<meta charset=\"UTF-8\" />");
            writer.println("<title>edu.school.cinema.servlets.MyServlet.java:doGet(): Servlet code!</title>");
            writer.println("</head>");
            writer.println("<body>");

            writer.println("<h1>This is a simple java servlet get " + dao.getAllUsers().size() + ".</h1>");

            writer.println("</body>");
            writer.println("</html>");
        }
        counter++;
        System.out.println("get");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");



        try (PrintWriter writer = response.getWriter()) {
            writer.println("<!DOCTYPE html><html>");
            writer.println("<head>");
            writer.println("<meta charset=\"UTF-8\" />");
            writer.println("<title>edu.school.cinema.servlets.MyServlet.java:doPost(): Servlet code!</title>");
            writer.println("</head>");
            writer.println("<body>");

            writer.println("<h1>This is a simple java servlet post " + counter + ".</h1>");

            writer.println("</body>");
            writer.println("</html>");
        }
        counter++;
    }

}
