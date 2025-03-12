package clients.repository.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "CLIENT_ARCHIVE")
@ToString(callSuper = true)
@Getter
@Setter
public class ClientArchive {

    @Id
    private Integer id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @NotBlank
    @Column(nullable = false)
    private String cardType;

    @NotBlank
    @Column(nullable = false)
    private String country;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;

}
