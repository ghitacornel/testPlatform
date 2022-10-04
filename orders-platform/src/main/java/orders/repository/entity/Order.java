package orders.repository.entity;

import commons.model.Identifiable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@Table(name = "T_ORDER")
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

    @NotNull
    @Column(nullable = false)
    private Integer productId;

    @NotBlank
    @Column(nullable = false)
    private String productName;

    @NotBlank
    @Column(nullable = false)
    private String productColor;

    @NotNull
    @Positive
    @Column(nullable = false)
    private Double price;

    @NotNull
    @Positive
    @Column(nullable = false)
    private Integer quantity;

    @NotNull
    @Column(nullable = false)
    private Integer companyId;

    @NotBlank
    @Column(nullable = false)
    private String companyName;

    @NotNull
    @Enumerated
    private OrderStatus status = OrderStatus.NEW;

}
