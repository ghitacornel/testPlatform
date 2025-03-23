package products.repository.entity;

import commons.model.Identifiable;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
public class Product extends Identifiable {

    @NotBlank
    @Column(nullable = false)
    private String name;

    @NotBlank
    @Column(nullable = false)
    private String color;

    @Min(1)
    @Column(nullable = false)
    private double price;

    @Min(0)
    @Column(nullable = false)
    private int quantity;

    @NotNull
    @Column(nullable = false)
    private Integer companyId;

    @NotNull
    @Enumerated
    private Status status = Status.ACTIVE;

    @Version
    private Integer version;

    @PrePersist
    private void checkStrictPositiveQuantityOnCreation() {
        if (quantity == 0) {
            throw new IllegalStateException("Product created with initial quantity ZERO");
        }
    }

    @PreUpdate
    private void adjustStatusBasedOnQuantity() {
        if (quantity == 0) {
            status = Status.CONSUMED;
        }
    }

    public void cancel() {
        status = Status.CANCELLED;
    }

    public void refill(Integer refill) {
        quantity += refill;
    }

}
