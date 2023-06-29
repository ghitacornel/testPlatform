package commons.exceptions;

import java.util.List;

public record ErrorResponse(String message, List<FieldError> fieldErrors) {

    public ErrorResponse(String message) {
        this(message, null);
    }
}
