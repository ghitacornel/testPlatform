package orders.repository.entity;

import commons.model.Identifiable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@Table(name = "T_ORDER")
public class Order extends Identifiable {

    @NotNull
    @Column(nullable = false)
    private Integer clientId;

    @NotBlank
    @Column(nullable = false)
    private Integer productId;

    @NotNull
    @Positive
    @Column(nullable = false)
    private Integer quantity;

    @NotNull
    @Enumerated
    private OrderStatus status = OrderStatus.NEW;

}
