package ge.rrs;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

/**
 * Configuration class for Spring framework.
 * Takes the responsibility of rrs-servlet.xml configuration file.
 */
@Configuration
public class RRSConfig implements WebMvcConfigurer {
	public void addViewControllers(ViewControllerRegistry registry) {
        // Index page
		registry.addViewController("/home").setViewName("index");
        registry.addViewController("/").setViewName("index");

        // Authentication
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/register").setViewName("registration");
	}

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
