package orders.clients.company;

import lombok.Data;

@Data
public class CompanyDto {

    private Integer id;
    private String name;
    private String url;
    private String industry;
    private String country;

}
