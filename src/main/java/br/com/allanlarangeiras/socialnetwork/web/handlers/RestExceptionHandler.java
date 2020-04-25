package br.com.allanlarangeiras.socialnetwork.web.handlers;

import br.com.allanlarangeiras.socialnetwork.exceptions.InnactiveException;
import br.com.allanlarangeiras.socialnetwork.exceptions.NotAuthorizedException;
import br.com.allanlarangeiras.socialnetwork.exceptions.NotFoundException;
import br.com.allanlarangeiras.socialnetwork.web.responses.ApiErrorResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity handleNotFound(NotFoundException notFoundException) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiErrorResponse.build("Resource not found"));
    }

    @ExceptionHandler(NotAuthorizedException.class)
    public ResponseEntity handleNotAuthorized(NotAuthorizedException notAuthorizedException) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ApiErrorResponse.build("Not Authorized"));
    }

    @ExceptionHandler(InnactiveException.class)
    public ResponseEntity handleInnactivity(InnactiveException innactiveException) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ApiErrorResponse.build("The resource is not active"));
    }



}
