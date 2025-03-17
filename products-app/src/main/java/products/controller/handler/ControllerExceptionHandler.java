package products.controller.handler;

import commons.exceptions.CommonControllerExceptionHandler;
import commons.exceptions.ErrorResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import products.exceptions.CannotBuyMoreThanAvailableException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler extends CommonControllerExceptionHandler {

    @ExceptionHandler(CannotBuyMoreThanAvailableException.class)
    public ResponseEntity<Object> handleCannotBuyMoreThanAvailableException(CannotBuyMoreThanAvailableException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), BAD_REQUEST);
    }

    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException e) {
        log.error("Entity not found, {}", e.getMessage());
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), NOT_FOUND);
    }

    @ExceptionHandler(org.hibernate.StaleObjectStateException.class)
    public ResponseEntity<Object> handleCannotBuyMoreThanAvailableException(org.hibernate.StaleObjectStateException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), BAD_REQUEST);
    }

}