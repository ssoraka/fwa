package edu.school.cinema.servlets;

import edu.school.cinema.models.User;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

@WebServlet("/profile")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 100
)
public class ProfileServlet extends HttpServlet {

    private File rootDir;

    @Override
    public void init() throws ServletException {
        super.init();

        ServletContext servletContext = getServletContext();
        ApplicationContext springContext = (ApplicationContext) servletContext.getAttribute("springContext");
        rootDir = springContext.getBean(File.class);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        File dir = rootDir.toPath().resolve(user.getPhoneNumber()).toFile();
        dir.mkdir();
        request.getSession().setAttribute("pathImages", dir);

        if (!dir.toPath().resolve("avatar.png").toFile().exists()) {
            Files.copy(ProfileServlet.class.getResourceAsStream("/avatar.png"), dir.toPath().resolve("avatar.png"));
        }
        File avatar = dir.toPath().resolve("avatar.png").toFile();
        byte[] fileContent = Files.readAllBytes( avatar.toPath() );
        String encodedString = Base64.getEncoder().encodeToString(fileContent);
        request.getSession().setAttribute("avatar", encodedString);

        request.getRequestDispatcher("/WEB-INF/jsp/profile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse rs) throws ServletException, IOException {
        File pathToPic =  (File) req.getSession().getAttribute("pathImages");
        if (!req.getPart("file").getSubmittedFileName().endsWith(".png")) {
            rs.sendRedirect("/profile");
            return;
        }

        File file = pathToPic.toPath().resolve("avatar.png").toFile();
        String filePath = file.getAbsolutePath();
        file.delete();

        try{
            for (Part part : req.getParts()) {
                part.write(filePath);
            }
        } catch (Exception ignored){}

        rs.sendRedirect("/profile");
    }
}
