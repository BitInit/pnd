package site.bitinit.pnd.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author john
 * @date 2020-01-05
 */
@SpringBootApplication
public class PndWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(PndWebApplication.class, args);
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
