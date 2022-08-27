package edu.school.cinema.config;

import edu.school.cinema.repositories.AuthenticationDao;
import edu.school.cinema.repositories.AuthenticationDaoImpl;
import edu.school.cinema.repositories.UserDao;
import edu.school.cinema.repositories.UserDaoImpl;
import edu.school.cinema.services.UserService;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = { "edu.school.cinema" })
@PropertySource("../application.properties")
public class ApplicationContextConfig {

    @Value("${datasource.driver}")
    private String driverClassName;
    @Value("${datasource.url}")
    private String url;
    @Value("${datasource.username}")
    private String userName;
    @Value("${datasource.password}")
    private String password;
    @Value("${storage.path}")
    private String pathToPic;

    @Bean
    public UserDao userDao() {
        return new UserDaoImpl(jdbcTemplate());
    }

    @Bean
    public AuthenticationDao authenticationDao() {
        return new AuthenticationDaoImpl(jdbcTemplate());
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    @Bean
    public UserService userService() {
        return new UserService(userDao(), authenticationDao(), passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(userName);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driverClassName);
        return dataSource;
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

    @Bean
    public File pathToPic() {
        File rootDir = new File(pathToPic);

        if (!rootDir.exists()) {
            if (!rootDir.mkdir()) throw new RuntimeException("Не получилось создать папку " + pathToPic);
        }
        if (!rootDir.isDirectory()) {
            throw new RuntimeException("Не получилось создать папку " + pathToPic);
        }
        if (!rootDir.canWrite() || !rootDir.canRead()) {
            throw new RuntimeException("Нет доступа к папке " + pathToPic);
        }
        return rootDir;
    }
}
