package products.repository.entity;

import commons.model.Identifiable;
import jakarta.persistence.EnumType;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;

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

    @Min(1)
    @Column(nullable = false)
    private int quantity;

    @NotNull
    @Column(nullable = false)
    private Integer companyId;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private ProductStatus status = ProductStatus.ACTIVE;

}
