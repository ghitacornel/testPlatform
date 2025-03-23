package companies.controller.handler;

import commons.exceptions.CommonControllerExceptionHandler;
import commons.exceptions.ErrorResponse;
import companies.exceptions.CompanyNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class ControllerExceptionHandler extends CommonControllerExceptionHandler {

    @ExceptionHandler(CompanyNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleClientNotFoundException(CompanyNotFoundException e) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), NOT_FOUND);
    }

}