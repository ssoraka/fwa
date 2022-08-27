package edu.school.cinema.servlets;

import edu.school.cinema.models.User;
import edu.school.cinema.repositories.UserDao;
import edu.school.cinema.services.UserService;
import org.springframework.context.ApplicationContext;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/profile")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB
        maxRequestSize = 1024 * 1024 * 100   // 100 MB
)
public class ProfileServlet extends HttpServlet {

    private UserService userService;
    private File rootDir;

    @Override
    public void init() throws ServletException {
        super.init();

        ServletContext servletContext = getServletContext();
        ApplicationContext springContext = (ApplicationContext) servletContext.getAttribute("springContext");
        userService = springContext.getBean(UserService.class);
        String pathToPic = springContext.getBean(String.class);

        rootDir = new File(pathToPic);
        if (!rootDir.exists()) {
            if (!rootDir.mkdir()) throw new RuntimeException("Не получилось создать папку " + pathToPic);
        }
        if (!rootDir.isDirectory()) {
            throw new RuntimeException("Не получилось создать папку " + pathToPic);
        }
        if (!rootDir.canWrite() || !rootDir.canRead()) {
            throw new RuntimeException("Нет доступа к папке " + pathToPic);
        }

        System.out.println("init");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        File dir = rootDir.toPath().resolve(user.getPhoneNumber()).toFile();
        dir.mkdir();
        request.getSession().setAttribute("pathImages", dir);
        request.getRequestDispatcher("/WEB-INF/jsp/profile.jsp").forward(request, response);

        System.out.println("get");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/jsp");

        File pathToPic =  (File)req.getSession().getAttribute("pathImages");
        String filePath = pathToPic.toPath().resolve(req.getPart("file").getSubmittedFileName()).toFile().getAbsolutePath();

        try{
            for (Part part : req.getParts()) {
                part.write(filePath);
            }
        } catch (Exception ignored){}
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/profile.jsp");
        dispatcher.forward(req, resp);
    }
}
