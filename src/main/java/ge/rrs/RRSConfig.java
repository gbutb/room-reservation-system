package ge.rrs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * Configuration class for Spring framework.
 * Takes the responsibility of rrs-servlet.xml configuration file.
 */
@Configuration
@EnableWebMvc
@ComponentScan({"ge.rrs"})
public class RRSConfig implements WebMvcConfigurer {

    /**
     * Adds webapp resources folder to Spring MVC so it can be accessed from .jsp/.html files.
     *
     * @param registry registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("resources/**").addResourceLocations("/resources/");
    }

    /**
     * Filenames returned by Spring controllers will be suffixed with '.jsp'.
     *
     * @return InternalResourceViewResolver object
     */
    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver vr = new InternalResourceViewResolver();
        vr.setSuffix(".jsp");

        return vr;
    }
}
