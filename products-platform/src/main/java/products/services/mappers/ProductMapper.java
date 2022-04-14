package products.services.mappers;

import org.springframework.stereotype.Component;
import products.controllers.models.ProductDto;
import products.repositories.entities.Product;

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
