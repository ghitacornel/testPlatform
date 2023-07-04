package products.mapper;

import org.mapstruct.Mapper;
import products.controller.model.request.ProductSellRequest;
import products.controller.model.response.ProductDetailsResponse;
import products.repository.entity.Product;

@Mapper
public interface ProductMapper {

    ProductDetailsResponse map(Product model);
    Product map(ProductSellRequest model);

}
