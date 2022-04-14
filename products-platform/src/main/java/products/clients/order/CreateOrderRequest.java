package products.clients.order;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CreateOrderRequest {

    @NotBlank
    @Column(nullable = false)
    private String userName;

    @NotBlank
    @Column(nullable = false)
    private Integer productId;

    @NotNull
    @Column(nullable = false)
    private Double productPrice;

    @NotNull
    @Column(nullable = false)
    private Integer productQuantity;

    @NotNull
    @Column(nullable = false)
    private String companyName;

}
