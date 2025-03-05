package invoices.mapper;

import contracts.invoices.*;
import invoices.repository.entity.Invoice;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
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
