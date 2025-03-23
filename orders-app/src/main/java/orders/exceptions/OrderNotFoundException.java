package orders.exceptions;

import commons.exceptions.TechnicalException;

public class OrderNotFoundException extends TechnicalException {

    public OrderNotFoundException(Integer id) {
        super(String.valueOf(id));
    }

}
