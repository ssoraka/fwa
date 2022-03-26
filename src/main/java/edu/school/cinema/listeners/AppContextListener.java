package edu.school.cinema.listeners;

import edu.school.cinema.config.ApplicationContextConfig;
import edu.school.cinema.repositories.UserDaoTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        final ServletContext servletContext = sce.getServletContext();

        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(ApplicationContextConfig.class);
        ctx.refresh();
        servletContext.setAttribute("springContext", ctx);

        System.out.println("contextInitialized");




//        ServletRegistration.Dynamic appServlet =
//                servletContext.addServlet("mvc", new DispatcherServlet(new GenericWebApplicationContext()));
//        appServlet.setLoadOnStartup(1);
//        appServlet.addMapping("/");

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("contextDestroyed");
    }
}
