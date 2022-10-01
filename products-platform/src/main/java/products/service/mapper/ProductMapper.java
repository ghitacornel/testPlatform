package products.service.mapper;

import org.mapstruct.Mapper;
import products.controller.model.ProductDto;
import products.repository.entity.Product;

@Mapper
public interface ProductMapper {

    ProductDto map(Product model);

}
