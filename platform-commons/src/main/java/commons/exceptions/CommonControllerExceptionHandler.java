package commons.exceptions;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Comparator;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
public class CommonControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<ErrorResponse> handleEmptyResultDataAccessException(EmptyResultDataAccessException e) {
        log.error("empty data", e);
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), NOT_FOUND);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException e) {
        log.error("Entity not found, {}", e.getMessage());
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), NOT_FOUND);
    }

    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFound e) {
        // no logs for these exceptions
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), NOT_FOUND);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleBusinessException(BusinessException e) {
        log.error("business exception", e);
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), BAD_REQUEST);
    }

    /**
     * override how validation constraints in REST layer are handled
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error("invalid argument", e);
        return new ResponseEntity<>(new ErrorResponse(e.getMessage(), e.getBindingResult().getAllErrors().stream()
                .map(error -> {
                    String fieldName = ((FieldError) error).getField();
                    String fieldValue = String.valueOf(((FieldError) error).getRejectedValue());
                    String errorMessage = error.getDefaultMessage();
                    return new commons.exceptions.FieldError(fieldName, fieldValue, errorMessage);
                })
                .sorted(Comparator.comparing(commons.exceptions.FieldError::fieldName))
                .collect(Collectors.toList())), BAD_REQUEST);
    }

    /**
     * override how validation constraints in SERVICE layer are handled
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException e) {
        log.error("constraint violated", e);
        return new ResponseEntity<>(new ErrorResponse(e.getMessage(), e.getConstraintViolations().stream()
                .map(error -> {
                    String fieldName = error.getPropertyPath().toString();
                    String fieldValue = String.valueOf(error.getInvalidValue());
                    String errorMessage = error.getMessage();
                    return new commons.exceptions.FieldError(fieldName, fieldValue, errorMessage);
                })
                .sorted(Comparator.comparing(commons.exceptions.FieldError::fieldName))
                .collect(Collectors.toList())), BAD_REQUEST);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(ValidationException e) {
        log.error("validation error", e);
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        log.error("data integrity error", e);
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), BAD_REQUEST);
    }

}