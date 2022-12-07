package companies.controller.handler;

import commons.exceptions.CommonControllerExceptionHandler;
import companies.exception.CompanyNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler extends CommonControllerExceptionHandler {

    @ExceptionHandler(CompanyNotFoundException.class)
    public ResponseEntity<Object> handle(CompanyNotFoundException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(e.getMessage(), NOT_FOUND);
    }
}