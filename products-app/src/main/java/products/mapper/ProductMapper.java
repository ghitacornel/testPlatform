package products.mapper;

import contracts.products.ProductDetailsResponse;
import contracts.products.ProductSellRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import products.repository.entity.Product;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ProductMapper {

    ProductDetailsResponse map(Product model);

    @Mapping(target = "status", ignore = true)
    @Mapping(target = "version", ignore = true)
    Product map(ProductSellRequest model);

}
