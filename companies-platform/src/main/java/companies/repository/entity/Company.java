package companies.repository.entity;

import commons.model.Identifiable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
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

    @NotNull
    @Enumerated
    private CompanyStatus status = CompanyStatus.ACTIVE;

}
