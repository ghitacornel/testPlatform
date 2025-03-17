package products.exceptions;

import commons.exceptions.BusinessException;

public class CannotBuyMoreThanAvailableException extends BusinessException {

    public CannotBuyMoreThanAvailableException(int productId, int requested, int available) {
        super("Cannot buy more that available product id " + productId + ", requested " + requested + ", available " + available);
    }
}
