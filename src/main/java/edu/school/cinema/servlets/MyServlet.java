package edu.school.cinema.servlets;

import edu.school.cinema.repositories.UserDao;
import edu.school.cinema.repositories.UserDaoTest;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/users")
public class MyServlet extends HttpServlet {

    int counter = -1;

    UserDao dao;
    @Autowired
    UserDao dao1;


    @Override
    public void init() throws ServletException {
        super.init();
        counter = 0;
        dao = new UserDaoTest();
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

            writer.println("<h1>This is a simple java servlet get " + dao1 + ".</h1>");

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
