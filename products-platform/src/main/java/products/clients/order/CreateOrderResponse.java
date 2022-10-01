package products.clients.order;

import lombok.Data;

@Data
public class CreateOrderResponse {

    private Integer id;
    private Integer userId;
    private String userName;
    private String userCreditCardType;
    private String productName;
    private String productColor;
    private Double productPrice;
    private Integer productQuantity;
    private Integer companyId;
    private String companyName;

}
