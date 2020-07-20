package ge.rrs;

import org.springframework.context.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

// MVC
import org.springframework.web.servlet.config.annotation.*;

/**
 * Configuration class for Spring framework.
 * Takes the responsibility of rrs-servlet.xml configuration file.
 */
@Configuration
@EnableWebMvc
@ComponentScan({"ge.rrs"})
public class RRSConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");

        // Auth views
		registry.addViewController("/login").setViewName("login");
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
