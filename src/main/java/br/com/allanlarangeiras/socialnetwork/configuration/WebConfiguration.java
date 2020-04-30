package br.com.allanlarangeiras.socialnetwork.configuration;

import br.com.allanlarangeiras.socialnetwork.web.handlers.SecurityHttpHandleInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Autowired
    private SecurityHttpHandleInterceptor securityHttpHandleInterceptor;

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(securityHttpHandleInterceptor);
    }
}
