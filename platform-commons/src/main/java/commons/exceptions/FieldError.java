package commons.exceptions;


public record FieldError(String fieldName, String fieldValue, String message) {
}
