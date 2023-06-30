package product.model;

import lombok.Data;

@Data
public class ProductBuy {

    private Integer clientId;
    private Integer productId;
    private int quantity;

}
