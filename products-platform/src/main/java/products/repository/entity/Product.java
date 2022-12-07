package products.repository.entity;

import commons.model.Identifiable;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Version;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Entity
@Getter
@Setter
public class Product extends Identifiable {

    @NotBlank
    @Column(nullable = false)
    private String name;

    @NotBlank
    @Column(nullable = false)
    private String color;

    @NotNull
    @Min(1)
    @Column(nullable = false)
    private Double price;

    @NotNull
    @PositiveOrZero
    @Column(nullable = false)
    private Integer quantity;

    @NotNull
    @Column(nullable = false)
    private Integer companyId;

    @NotNull
    @Enumerated
    private ProductStatus status = ProductStatus.ACTIVE;

    @Version
    private Integer version;

}
