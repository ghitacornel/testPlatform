package orders.controllers.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

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
