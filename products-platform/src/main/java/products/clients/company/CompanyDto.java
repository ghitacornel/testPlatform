package products.clients.company;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDto {

    private Integer id;

    @NotBlank
    private String name;

    @NotBlank
    private String url;

    @NotBlank
    private String industry;

}
