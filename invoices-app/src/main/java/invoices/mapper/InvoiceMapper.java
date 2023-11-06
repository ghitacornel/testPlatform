package invoices.mapper;

import invoices.controller.model.request.UpdateClientRequest;
import invoices.controller.model.request.UpdateCompanyRequest;
import invoices.controller.model.request.UpdateOrderRequest;
import invoices.controller.model.request.UpdateProductRequest;
import invoices.controller.model.response.InvoiceDetails;
import invoices.repository.entity.Invoice;
import org.mapstruct.*;

@Mapper
public interface InvoiceMapper {

    InvoiceDetails map(Invoice data);

    @Mapping(target = "id", ignore = true)
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    void update(@MappingTarget Invoice invoice, UpdateOrderRequest data);

    @Mapping(target = "id", ignore = true)
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    void update(@MappingTarget Invoice invoice, UpdateClientRequest data);

    @Mapping(target = "id", ignore = true)
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    void update(@MappingTarget Invoice invoice, UpdateCompanyRequest data);

    @Mapping(target = "id", ignore = true)
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    void update(@MappingTarget Invoice invoice, UpdateProductRequest data);

}
