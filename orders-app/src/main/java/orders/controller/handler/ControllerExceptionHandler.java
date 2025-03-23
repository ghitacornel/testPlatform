package orders.controller.handler;

import commons.exceptions.CommonControllerExceptionHandler;
import commons.exceptions.ErrorResponse;
import orders.exceptions.OrderNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class ControllerExceptionHandler extends CommonControllerExceptionHandler {

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleOrderNotFoundException(OrderNotFoundException e) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), NOT_FOUND);
    }

}