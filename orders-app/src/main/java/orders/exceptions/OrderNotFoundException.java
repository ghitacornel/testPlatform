package orders.exceptions;

import commons.exceptions.ResourceNotFound;

public class OrderNotFoundException extends ResourceNotFound {

    public OrderNotFoundException(Integer id) {
        super("Order not found for id " + id);
    }

}
