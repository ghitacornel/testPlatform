package platform.order.model;

import lombok.Data;

@Data
public class Order {

    private Integer id;
    private Integer userId;
    private String userName;
    private String userCreditCardType;
    private String productName;
    private String productColor;
    private Double price;
    private Integer quantity;
    private Integer companyId;
    private String companyName;

}
