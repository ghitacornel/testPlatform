package products.exceptions;

import commons.exceptions.BusinessException;

public class CannotBuyMoreThanAvailableException extends BusinessException {

    public CannotBuyMoreThanAvailableException(int requested, int available) {
        super("Cannot buy more that it exists, requested " + requested + " available " + available);
    }
}
