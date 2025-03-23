package products.controller.handler;

import commons.exceptions.CommonControllerExceptionHandler;
import commons.exceptions.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import products.exceptions.CannotBuyMoreThanAvailableException;
import products.exceptions.ProductNotFoundException;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler extends CommonControllerExceptionHandler {

    @ExceptionHandler(CannotBuyMoreThanAvailableException.class)
    public ResponseEntity<Object> handleCannotBuyMoreThanAvailableException(CannotBuyMoreThanAvailableException e) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), BAD_REQUEST);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProductNotFoundException(ProductNotFoundException e) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), NOT_FOUND);
    }

    @ExceptionHandler(org.hibernate.StaleObjectStateException.class)
    public ResponseEntity<Object> handleStaleObjectStateException(org.hibernate.StaleObjectStateException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), INTERNAL_SERVER_ERROR);
    }

}