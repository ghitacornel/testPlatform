package invoices.exceptions;

import commons.exceptions.ResourceNotFound;

public class InvoiceNotFoundException extends ResourceNotFound {

    public InvoiceNotFoundException(Integer id) {
        super("Invoice not found for id " + id);
    }

}
