package edu.school.cinema.servlets;

import edu.school.cinema.models.User;
import org.springframework.context.ApplicationContext;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

@WebServlet(value = {"/images/*", "/images"}, name = "Image")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 100
)
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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse rs) throws ServletException, IOException {
        rs.setContentType("text/jsp");

        File pathToPic =  (File)req.getSession().getAttribute("pathImages");
        String filePath = pathToPic.toPath().resolve(req.getPart("file").getSubmittedFileName()).toFile().getAbsolutePath();

        try{
            for (Part part : req.getParts()) {
                part.write(filePath);
            }
        } catch (Exception ignored){}
        rs.sendRedirect("/profile");
    }
}
