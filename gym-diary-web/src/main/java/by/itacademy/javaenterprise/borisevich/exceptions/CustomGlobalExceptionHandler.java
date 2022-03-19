package by.itacademy.javaenterprise.borisevich.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.io.IOException;

@RestControllerAdvice
public class CustomGlobalExceptionHandler  {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> springHandleNotFound(RuntimeException ex, WebRequest request) throws IOException {
        String bodyOfResponse = "Entity with this id doesn't exist";
        return new ResponseEntity<>(bodyOfResponse, HttpStatus.NOT_FOUND);
    }

}
