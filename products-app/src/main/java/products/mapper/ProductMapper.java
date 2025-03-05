package products.mapper;

import contracts.products.ProductDetailsResponse;
import contracts.products.ProductSellRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import products.repository.entity.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDetailsResponse map(Product model);

    @Mapping(target = "status", ignore = true)
    Product map(ProductSellRequest model);

}
