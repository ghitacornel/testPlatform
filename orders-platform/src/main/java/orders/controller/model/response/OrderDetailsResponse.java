package orders.controller.model.response;

import lombok.Data;

@Data
public class OrderDetailsResponse {

    private Integer id;
    private Integer userId;
    private String userName;
    private String userCreditCardType;
    private String productId;
    private String productName;
    private String productColor;
    private Double price;
    private Integer quantity;
    private Integer companyId;
    private String companyName;
    private String status;

}
