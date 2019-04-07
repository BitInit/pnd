package site.bitinit.pnd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class PndApplication {

    public static void main(String[] args) {
        SpringApplication.run(PndApplication.class, args);
    }

    @Configuration
    static class DefaultWebMvcConfigurer implements WebMvcConfigurer{

        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**").allowedMethods("*");
        }
    }

    @Controller
    static class PndController{

        @GetMapping({"/", "/index"})
        public String index(){
            return "redirect:/index.html";
        }
    }
}
