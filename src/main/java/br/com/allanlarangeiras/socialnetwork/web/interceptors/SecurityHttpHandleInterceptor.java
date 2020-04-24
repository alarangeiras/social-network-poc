package br.com.allanlarangeiras.socialnetwork.web.interceptors;

import br.com.allanlarangeiras.socialnetwork.annotations.Secure;
import br.com.allanlarangeiras.socialnetwork.services.AuthenticationService;
import br.com.allanlarangeiras.socialnetwork.types.AppHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Optional;

@Component
public class SecurityHttpHandleInterceptor implements HandlerInterceptor {

    @Autowired
    private AuthenticationService authenticationService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        for (Annotation annotation : Arrays.asList(handler.getClass().getDeclaredAnnotations())){
            if (annotation instanceof Secure) {
                String token = request.getHeader(AppHeaders.TOKEN.toString());
                authenticationService.authorize(Optional.ofNullable(token));
                return true;
            }
        }
        return true;
    }
}