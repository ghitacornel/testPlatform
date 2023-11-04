package companies.controller.handler;

import commons.exceptions.CommonControllerExceptionHandler;
import commons.exceptions.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
@Slf4j
public class ControllerExceptionHandler extends CommonControllerExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        log.error("data integrity error " + e.getMessage());
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), BAD_REQUEST);
    }

}