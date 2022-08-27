package edu.school.cinema.servlets;

import edu.school.cinema.models.User;
import org.springframework.context.ApplicationContext;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

@WebServlet(value = {"/images/*"}, name = "Image")
public class ImageServlet extends HttpServlet {
    public ImageServlet(){}

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext context = config.getServletContext();
        ApplicationContext springContext = (ApplicationContext) context.getAttribute("springContext");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/jsp");
        File file = (File)req.getSession().getAttribute("pathImages");

        file = new File(file.getAbsolutePath() + req.getPathInfo());
        byte[] fileContent = Files.readAllBytes( file.toPath() );
        String encodedString = Base64.getEncoder().encodeToString(fileContent);
        req.getSession().setAttribute("image", encodedString);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/image.jsp");
        dispatcher.forward(req, resp);
    }
}
