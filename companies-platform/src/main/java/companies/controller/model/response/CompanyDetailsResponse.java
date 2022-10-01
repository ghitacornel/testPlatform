package companies.controller.model.response;

import lombok.Data;

@Data
public class CompanyDetailsResponse {

    private Integer id;
    private String name;
    private String url;
    private String industry;

}
