package com.example.ms_backend.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage handleAllException(Exception ex, WebRequest request) {
        ex.printStackTrace();
        return new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
    }

    @ExceptionHandler({NotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage handleNotFoundException(Exception ex, WebRequest request) {
        return new ErrorMessage(HttpStatus.NOT_FOUND, ex.getLocalizedMessage());
    }

    @ExceptionHandler({BadRequestException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage handleNotEnoughException(Exception ex, WebRequest request) {
        return new ErrorMessage(HttpStatus.NOT_FOUND, ex.getLocalizedMessage());
    }

    @ExceptionHandler({ForbiddenException.class})
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public ErrorMessage handleForbiddenException(Exception ex, WebRequest request) {
        return new ErrorMessage(HttpStatus.FORBIDDEN, ex.getLocalizedMessage());
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<ErrorMessage> handleAccessDeniedException(
            Exception ex, WebRequest request) {
        ErrorMessage re = new ErrorMessage(HttpStatus.FORBIDDEN,
                ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(re);
    }

}

record ErrorMessage(HttpStatus status, String message) {
}
