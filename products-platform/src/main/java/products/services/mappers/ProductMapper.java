package products.services.mappers;

import org.springframework.stereotype.Component;
import products.controller.model.ProductDto;
import products.repository.entity.Product;

@Component
public class ProductMapper {

    public ProductDto map(Product model) {
        return ProductDto.builder()
                .id(model.getId())
                .name(model.getName())
                .color(model.getColor())
                .price(model.getPrice())
                .quantity(model.getQuantity())
                .build();
    }

}
