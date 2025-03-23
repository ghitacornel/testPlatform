package companies.exceptions;

import commons.exceptions.TechnicalException;

public class CompanyNotFoundException extends TechnicalException {

    public CompanyNotFoundException(Integer id) {
        super("Company not found for id " + id);
    }

}
