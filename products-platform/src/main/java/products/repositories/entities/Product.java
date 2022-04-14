package products.repositories.entities;

import commons.model.Identifiable;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "T_PRODUCT")
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
    @Min(1)
    @Column(nullable = false)
    private Integer quantity;

    @NotNull
    @Column(nullable = false)
    private Integer companyId;

    @NotNull
    @Column(nullable = false)
    private String companyName;

}
