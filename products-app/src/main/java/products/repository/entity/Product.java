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
    @Enumerated(EnumType.ORDINAL)
    private ProductStatus status = ProductStatus.ACTIVE;

    @PrePersist
    private void checkStrictPositiveQuantityOnCreation() {
        if (quantity == 0) {
            throw new IllegalStateException("Product created with initial quantity ZERO");
        }
    }

    @PreUpdate
    private void adjustStatusBasedOnQuantity() {
        if (quantity == 0) {
            status = ProductStatus.CONSUMED;
        }
    }

}
