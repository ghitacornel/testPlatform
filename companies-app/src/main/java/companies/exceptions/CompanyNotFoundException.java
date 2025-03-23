package companies.exceptions;

import commons.exceptions.ResourceNotFound;

public class CompanyNotFoundException extends ResourceNotFound {

    public CompanyNotFoundException(Integer id) {
        super("Company not found for id " + id);
    }

}
