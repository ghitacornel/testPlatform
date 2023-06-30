package product.model;

import lombok.Data;

@Data
public class Product  {

    private Integer id;
    private String name;
    private String color;
    private double price;
    private int quantity;

}
