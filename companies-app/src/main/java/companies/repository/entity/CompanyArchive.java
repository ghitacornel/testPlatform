package companies.repository.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CompanyArchive {

    @Id
    private Integer id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @NotBlank
    @Column(nullable = false)
    private String url;

    @NotBlank
    @Column(nullable = false)
    private String industry;

    @NotBlank
    @Column(nullable = false)
    private String country;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;

}
