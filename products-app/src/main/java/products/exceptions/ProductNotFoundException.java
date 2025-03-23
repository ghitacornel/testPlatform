package products.exceptions;

import commons.exceptions.BusinessException;

public class ProductNotFoundException extends BusinessException {

    public ProductNotFoundException(int id) {
        super("No product found for id " + id);
    }

}
