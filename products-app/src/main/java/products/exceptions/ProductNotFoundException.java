package products.exceptions;

import commons.exceptions.ResourceNotFound;

public class ProductNotFoundException extends ResourceNotFound {

    public ProductNotFoundException(int id) {
        super("No product found for id " + id);
    }

}
