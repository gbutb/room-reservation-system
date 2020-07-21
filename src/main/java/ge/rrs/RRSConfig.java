package ge.rrs;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class for Spring framework.
 * Takes the responsibility of rrs-servlet.xml configuration file.
 */
@Configuration
public class RRSConfig implements WebMvcConfigurer {
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/home").setViewName("index");
		registry.addViewController("/").setViewName("index");
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
