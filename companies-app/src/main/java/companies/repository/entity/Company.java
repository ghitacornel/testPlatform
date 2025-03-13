package companies.repository.entity;

import commons.model.Identifiable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;

@Entity
@ToString(callSuper = true, includeFieldNames = false)
@Getter
@Setter
public class Company extends Identifiable {

    @NotBlank
    @Column(nullable = false, unique = true)
    private String name;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String url;

    @NotBlank
    @Column(nullable = false)
    private String industry;

    @NotBlank
    @Column(nullable = false)
    private String country;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status = Status.ACTIVE;

    public void retire() {
        status = Status.RETIRED;
    }

}
