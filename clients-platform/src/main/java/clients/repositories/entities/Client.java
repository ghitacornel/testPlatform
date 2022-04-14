package clients.repositories.entities;

import commons.model.Identifiable;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "T_CLIENT")
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

}
