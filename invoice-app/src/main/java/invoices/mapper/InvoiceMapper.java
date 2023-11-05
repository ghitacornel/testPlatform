package invoices.mapper;

import invoices.controller.model.response.InvoiceDetails;
import invoices.repository.entity.Invoice;
import org.mapstruct.Mapper;

@Mapper
public interface InvoiceMapper {

    InvoiceDetails map(Invoice data);

}
