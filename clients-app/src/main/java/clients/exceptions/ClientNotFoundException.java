package clients.exceptions;

import commons.exceptions.TechnicalException;

public class ClientNotFoundException extends TechnicalException {

    public ClientNotFoundException(Integer id) {
        super("Client not found for id " + id);
    }

}
