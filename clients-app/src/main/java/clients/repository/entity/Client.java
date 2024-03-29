package clients.repository.entity;

import commons.model.Identifiable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;

@Entity
@ToString(callSuper = true)
@Getter
@Setter
public class Client extends Identifiable {

    @NotBlank
    @Column(nullable = false, unique = true)
    private String name;

    @NotBlank
    @Column(nullable = false)
    private String cardType;

    @NotBlank
    @Column(nullable = false)
    private String country;

}
