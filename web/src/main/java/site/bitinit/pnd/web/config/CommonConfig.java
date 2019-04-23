package site.bitinit.pnd.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;

/**
 * @author: john
 * @date: 2019/4/23
 */
@Configuration
public class CommonConfig {
    @Autowired
    private Properties properties;

    @Bean
    public MultipartConfigElement multipartConfigElement(){
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.parse(properties.getMaxFileUploadSize()));
        factory.setMaxRequestSize(DataSize.parse(properties.getMaxRequestSize()));
        return factory.createMultipartConfig();
    }
}
