package companies.repositories.entities;

import commons.model.Identifiable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "T_COMPANY")
@ToString(callSuper = true)
@Getter
@Setter
public class Company extends Identifiable {

    @NotBlank
    @Column(nullable = false, unique = true)
    private String name;

    @NotBlank
    @Column(nullable = false)
    private String url;

    @NotBlank
    @Column(nullable = false)
    private String industry;

}
