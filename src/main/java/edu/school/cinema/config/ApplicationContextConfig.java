package edu.school.cinema.config;

import edu.school.cinema.repositories.UserDao;
import edu.school.cinema.repositories.UserDaoImpl;
import edu.school.cinema.repositories.UserDaoTest;
import org.hibernate.SessionFactory;
import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

//@EnableWebMvc //для запуска addViewControllers
@Configuration
@ComponentScan(basePackages = { "edu.school.cinema" })
//@EnableTransactionManagement
public class ApplicationContextConfig implements WebMvcConfigurer {

//    private static Logger logger = LoggerFactory.logger(ApplicationContextConfig.class);

//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/").setViewName("index");
//    }

    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver bean = new InternalResourceViewResolver();

//        bean.setApplicationContext(applicationContext);
        //эта херота нужна для построения урла из названия файла
        bean.setViewClass(JstlView.class);
        bean.setPrefix("/WEB-INF/jsp/");
        bean.setSuffix(".jsp");

        System.out.println("sdasdsadas");

        return bean;
    }


    @Bean
    public UserDao userDao() {
        try {
            return new UserDaoImpl(dataSource());
        } catch (Exception e) {
            System.out.println("!!!!что-то пошло не так с бд!!!!");
            return new UserDaoTest();
        }
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


//    @Bean
    public DataSource dataSource() {
//        try {
//            return DataSourceBuilder.create()
//                    .url("jdbc:postgresql://localhost:5432/postgres")
//                    .driverClassName("org.postgresql.Driver")
//                    .username("postgres")
//                    .password("")
//                    .build();

            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setUrl("jdbc:postgresql://localhost:5432/postgres");
            dataSource.setUsername("postgres");
            dataSource.setPassword("");
            dataSource.setDriverClassName("org.postgresql.Driver");
            return dataSource;


//            return new EmbeddedDatabaseBuilder()
//                    .setType(EmbeddedDatabaseType.HSQL)
////                    .addScripts("classpath:sql/schema.sql", "classpath:sql/test-data.sql")
//                    .build();
//        } catch (Exception e) {
////            logger.error("Embedded DataSource bean cannot be created!", e);
//            return null;
//        }
    }

    private Properties hibernateProperties() {
        Properties hibernateProp = new Properties();
        hibernateProp.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQL10Dialect");
        hibernateProp.put("hibernate.format_sql", true);
        hibernateProp.put("hibernate.use_sql_comments", true);
        hibernateProp.put("hibernate.show_sql", true);
        hibernateProp.put("hibernate.max_fetch_depth", 3);
        hibernateProp.put("hibernate.jdbc.batch_size", 10);
        hibernateProp.put("hibernate.jdbc.fetch_size", 50);

        hibernateProp.put("hibernate.connection.CharSet", "utf8");
        hibernateProp.put("hibernate.connection.characterEncoding", "utf8");
        hibernateProp.put("hibernate.connection.useUnicode", true);


        hibernateProp.put("hibernate.hbm2ddl.auto", "create-drop");
        return hibernateProp;
    }

//    @Bean
    public SessionFactory sessionFactory() throws IOException {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource());
        sessionFactoryBean.setPackagesToScan("edu.school.cinema.models");
        sessionFactoryBean.setHibernateProperties(hibernateProperties());
        sessionFactoryBean.afterPropertiesSet();
        return sessionFactoryBean.getObject();
    }

    @Bean
    public PlatformTransactionManager transactionManager() throws IOException {
        return new HibernateTransactionManager(sessionFactory());
    }

}
