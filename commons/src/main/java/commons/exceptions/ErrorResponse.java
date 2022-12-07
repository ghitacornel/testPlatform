package commons.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(String message, List<FieldError> fieldErrors) {

    public ErrorResponse(String message) {
        this(message, null);
    }
}
