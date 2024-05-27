package dev.check;

import dev.check.entity.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
@Slf4j
@ControllerAdvice(annotations = CustomExceptionHandler1.class)
public class AdviceProject {

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleException(NoSuchElementException e) {
        String message = String.format("%s %s", LocalDateTime.now(), e.getMessage());
        log.info("перехват исключения на NOT_FOUND");
        return new ResponseEntity<String>(message, HttpStatus.NOT_FOUND);
    }
}
