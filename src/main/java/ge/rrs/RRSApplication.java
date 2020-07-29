package ge.rrs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;

import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionListener;
import java.util.Collections;

@SpringBootApplication
public class RRSApplication {

    /**
     * Registers new ServletContextListener.
     *
     * @return registration bean
     */
    @Bean
    public ServletListenerRegistrationBean<ServletContextListener> contextListenerRegistrationBean() {
        ServletListenerRegistrationBean<ServletContextListener> bean = new ServletListenerRegistrationBean<>();
        bean.setListener(new RRSContextListener());
        return bean;
    }

    /**
     * Registers new HttpSessionListener.
     *
     * @return registration bean
     */
    @Bean
    public ServletListenerRegistrationBean<HttpSessionListener> sessionListenerRegistrationBean() {
        ServletListenerRegistrationBean<HttpSessionListener> bean = new ServletListenerRegistrationBean<>();
        bean.setListener(new RRSSessionListener());
        return bean;
    }

    /**
     * Starts Spring Boot application on port 8080.
     *
     * @param args args
     */
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(RRSApplication.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", "8080"));
        app.run(args);
    }
}
