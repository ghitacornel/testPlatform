package product.model;

import lombok.Data;

@Data
public class ProductSale {

    private String name;
    private String color;
    private Double price;
    private Integer quantity;
    private Integer companyId;

}
