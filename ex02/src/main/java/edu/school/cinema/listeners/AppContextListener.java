package edu.school.cinema.listeners;

import edu.school.cinema.config.ApplicationContextConfig;
import edu.school.cinema.repositories.AuthenticationDao;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();

        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(ApplicationContextConfig.class);
        ctx.refresh();
        servletContext.setAttribute("springContext", ctx);

        AuthenticationDao dao = ctx.getBean(AuthenticationDao.class);
        sce.getServletContext().addListener(new MySessionListener(dao));

        System.out.println("contextInitialized");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("contextDestroyed");
    }
}
