package clients.controller.handler;

import clients.exceptions.ClientNotFoundException;
import commons.exceptions.CommonControllerExceptionHandler;
import commons.exceptions.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class ControllerExceptionHandler extends CommonControllerExceptionHandler {

    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleClientNotFoundException(ClientNotFoundException e) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), NOT_FOUND);
    }

}