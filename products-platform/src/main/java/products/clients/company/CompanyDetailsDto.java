package products.clients.company;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CompanyDetailsDto {

    private Integer id;

    @NotBlank
    private String name;

    @NotBlank
    private String url;

    @NotBlank
    private String industry;

}
