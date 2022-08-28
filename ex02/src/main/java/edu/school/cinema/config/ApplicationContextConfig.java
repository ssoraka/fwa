package edu.school.cinema.config;

import edu.school.cinema.repositories.AuthenticationDao;
import edu.school.cinema.repositories.AuthenticationDaoImpl;
import edu.school.cinema.repositories.UserDao;
import edu.school.cinema.repositories.UserDaoImpl;
import edu.school.cinema.services.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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
    public UserService userService() {
        return new UserService(userDao(), authenticationDao(), passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        try {
            Resource resource = new ClassPathResource("sql/schema.sql");
            ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);
            databasePopulator.execute(dataSource());

            resource = new ClassPathResource("sql/data.sql");
            databasePopulator = new ResourceDatabasePopulator(resource);
            databasePopulator.execute(dataSource());
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource());
        return jdbcTemplate;
    }

    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(userName);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driverClassName);
        return dataSource;
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
