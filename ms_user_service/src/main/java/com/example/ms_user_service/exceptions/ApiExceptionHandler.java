package com.example.ms_user_service.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler({Exception.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorMessage> handleAllException(Exception ex, WebRequest request) {
        ex.printStackTrace();
        ErrorMessage re = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR,
                "Internal Server Error");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(re);
    }

    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<ErrorMessage> handleNotFoundException(Exception ex, WebRequest request) {
        ErrorMessage re = new ErrorMessage(HttpStatus.NOT_FOUND,
                ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(re);
    }

    @ExceptionHandler({UserExistException.class})
    public ResponseEntity<ErrorMessage> handleConflictException(Exception ex, WebRequest request) {
        ErrorMessage re = new ErrorMessage(HttpStatus.CONFLICT,
                ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(re);
    }

    @ExceptionHandler({AuthenticationException.class,IncorrectPasswordException.class})
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleUnAuthenticationException(Exception ex) {

        ErrorMessage re = new ErrorMessage(HttpStatus.UNAUTHORIZED,
                ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(re);
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<ErrorMessage> handleForbiddenException(
            Exception ex, WebRequest request) {
        ErrorMessage re = new ErrorMessage(HttpStatus.FORBIDDEN,
                ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(re);
    }
}

record ErrorMessage(HttpStatus status, String message) {
}
