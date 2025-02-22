package products.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import products.controller.model.request.ProductSellRequest;
import products.controller.model.response.ProductDetailsResponse;
import products.repository.entity.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDetailsResponse map(Product model);

    @Mapping(target = "status", ignore = true)
    Product map(ProductSellRequest model);

}
