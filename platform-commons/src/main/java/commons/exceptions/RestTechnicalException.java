package commons.exceptions;

public class RestTechnicalException extends TechnicalException {

    public RestTechnicalException(String url, String message) {
        super(message);
    }

}
