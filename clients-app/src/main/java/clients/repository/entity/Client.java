package clients.repository.entity;

import commons.model.Identifiable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import jakarta.validation.constraints.NotBlank;

@Entity
@ToString(callSuper = true, includeFieldNames = false)
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

    @NotNull
    @Enumerated
    private Status status = Status.ACTIVE;

    public void retiring() {
        status = Status.RETIRING;
    }

    public void retired() {
        status = Status.RETIRED;
    }

}
