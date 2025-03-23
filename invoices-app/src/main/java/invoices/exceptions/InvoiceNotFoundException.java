package invoices.exceptions;

import commons.exceptions.TechnicalException;

public class InvoiceNotFoundException extends TechnicalException {

    public InvoiceNotFoundException(Integer id) {
        super("Invoice not found for id " + id);
    }

}
