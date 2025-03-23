package invoices.mapper;

import contracts.invoices.*;
import invoices.repository.entity.Invoice;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface InvoiceMapper {

    InvoiceDetails map(Invoice data);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "clientName", ignore = true)
    @Mapping(target = "clientCardType", ignore = true)
    @Mapping(target = "clientCountry", ignore = true)
    @Mapping(target = "productName", ignore = true)
    @Mapping(target = "productColor", ignore = true)
    @Mapping(target = "productPrice", ignore = true)
    @Mapping(target = "companyId", ignore = true)
    @Mapping(target = "companyName", ignore = true)
    @Mapping(target = "companyUrl", ignore = true)
    @Mapping(target = "companyIndustry", ignore = true)
    @Mapping(target = "companyCountry", ignore = true)
    @Mapping(target = "status", ignore = true)
    void update(@MappingTarget Invoice invoice, UpdateOrderRequest data);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orderQuantity", ignore = true)
    @Mapping(target = "productId", ignore = true)
    @Mapping(target = "productName", ignore = true)
    @Mapping(target = "productColor", ignore = true)
    @Mapping(target = "productPrice", ignore = true)
    @Mapping(target = "companyId", ignore = true)
    @Mapping(target = "companyName", ignore = true)
    @Mapping(target = "companyUrl", ignore = true)
    @Mapping(target = "companyIndustry", ignore = true)
    @Mapping(target = "companyCountry", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "creationDateTime", ignore = true)
    void update(@MappingTarget Invoice invoice, UpdateClientRequest data);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orderQuantity", ignore = true)
    @Mapping(target = "clientId", ignore = true)
    @Mapping(target = "clientName", ignore = true)
    @Mapping(target = "clientCardType", ignore = true)
    @Mapping(target = "clientCountry", ignore = true)
    @Mapping(target = "productId", ignore = true)
    @Mapping(target = "productName", ignore = true)
    @Mapping(target = "productColor", ignore = true)
    @Mapping(target = "productPrice", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "creationDateTime", ignore = true)
    void update(@MappingTarget Invoice invoice, UpdateCompanyRequest data);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orderQuantity", ignore = true)
    @Mapping(target = "clientId", ignore = true)
    @Mapping(target = "clientName", ignore = true)
    @Mapping(target = "clientCardType", ignore = true)
    @Mapping(target = "clientCountry", ignore = true)
    @Mapping(target = "companyName", ignore = true)
    @Mapping(target = "companyUrl", ignore = true)
    @Mapping(target = "companyIndustry", ignore = true)
    @Mapping(target = "companyCountry", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "creationDateTime", ignore = true)
    void update(@MappingTarget Invoice invoice, UpdateProductRequest data);

}
