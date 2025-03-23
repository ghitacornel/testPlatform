package clients.exceptions;

import commons.exceptions.ResourceNotFound;

public class ClientNotFoundException extends ResourceNotFound {

    public ClientNotFoundException(Integer id) {
        super("Client not found for id " + id);
    }

}
