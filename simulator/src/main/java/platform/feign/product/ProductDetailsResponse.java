package platform.feign.product;

import lombok.Data;

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
