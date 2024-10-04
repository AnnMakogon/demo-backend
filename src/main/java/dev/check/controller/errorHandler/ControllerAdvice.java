package dev.check.controller.errorHandler;

import dev.check.FindError500;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Slf4j
@org.springframework.web.bind.annotation.ControllerAdvice(annotations = FindError500.class)
public class ControllerAdvice {

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleException(NoSuchElementException e) {
        String message = String.format("%s %s", LocalDateTime.now(), e.getMessage());
        log.error("перехват исключения на NOT_FOUND");
        return new ResponseEntity<String>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception e) {
        String message = String.format("%s %s", LocalDateTime.now(), e.getMessage());
        log.error("Внутренняя ошибка сервера: ", e);
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e){
        String message = String.format("%s Error of the Validation %s", LocalDateTime.now(), e.getMessage());
        log.error("Error of thw Validation: ", e);
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
}
