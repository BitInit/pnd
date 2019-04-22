package site.bitinit.pnd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author john
 */
@SpringBootApplication
public class PndApplication {

    public static void main(String[] args) {
        SpringApplication.run(PndApplication.class, args);
    }

    @Profile("dev")
    @Configuration
    static class DefaultWebMvcConfigurer implements WebMvcConfigurer{

        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**").allowedMethods("*");
        }
    }

}
