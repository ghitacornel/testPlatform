package orders.repository.entity;

import contracts.orders.OrderStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class OrderArchive {

    @Id
    private Integer id;

    @NotNull
    @Column(nullable = false)
    private Integer clientId;

    @NotNull
    @Column(nullable = false)
    private Integer productId;

    @NotNull
    @Positive
    @Column(nullable = false)
    private Integer quantity;

    @NotNull
    @Enumerated
    private OrderStatus status;

}
