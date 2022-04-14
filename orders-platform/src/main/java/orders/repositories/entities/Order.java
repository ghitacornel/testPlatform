package orders.repositories.entities;

import commons.model.Identifiable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity
@Table(name = "T_ORDER")
@Getter
@Setter
@ToString(callSuper = true)
public class Order extends Identifiable {

    @NotNull
    @Column(nullable = false)
    private Integer userId;

    @NotBlank
    @Column(nullable = false)
    private String userName;

    @NotBlank
    @Column(nullable = false)
    private String userCreditCardType;

    @NotBlank
    @Column(nullable = false)
    private String productName;

    @NotBlank
    @Column(nullable = false)
    private String productColor;

    @NotNull
    @Positive
    @Column(nullable = false)
    private Double productPrice;

    @NotNull
    @Positive
    @Column(nullable = false)
    private Integer productQuantity;

    @NotNull
    @Column(nullable = false)
    private Integer companyId;

    @NotBlank
    @Column(nullable = false)
    private String companyName;

}
