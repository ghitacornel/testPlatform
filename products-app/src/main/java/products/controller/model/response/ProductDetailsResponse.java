package products.controller.model.response;

import lombok.Data;
import products.repository.entity.ProductStatus;

@Data
public class ProductDetailsResponse {

    private Integer id;
    private String name;
    private String color;
    private Double price;
    private Integer quantity;
    private Integer companyId;
    private ProductStatus status;

}
