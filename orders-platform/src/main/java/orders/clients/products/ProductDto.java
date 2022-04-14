package orders.clients.products;

import lombok.Data;

@Data
public class ProductDto {

    private Integer id;
    private String name;
    private String color;
    private Double price;
    private Integer quantity;

}
