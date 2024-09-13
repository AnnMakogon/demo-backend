package dev.check.controller.errorHandler;

import dev.check.FindError500;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Slf4j
        //todo проверь, как отрабатывает 500 (не NoSuch
@org.springframework.web.bind.annotation.ControllerAdvice(annotations = FindError500.class)
public class ControllerAdvice {

    // измени название, засунь в другое место, не в контроллеры
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleException(NoSuchElementException e) {
        String message = String.format("%s %s", LocalDateTime.now(), e.getMessage());
        log.info("перехват исключения на NOT_FOUND");
        return new ResponseEntity<String>(message, HttpStatus.NOT_FOUND);
    }
}
