package companies.exception;

import commons.exceptions.BusinessException;

public class CompanyNotFoundException extends BusinessException {
    public CompanyNotFoundException(Integer id) {
        super("Company with id " + id + " not found");
    }
}
