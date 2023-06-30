package product.model;

import lombok.Data;

@Data
public class Product  {

    private Integer id;
    private String name;
    private String color;
    private Double price;
    private Integer quantity;

}
