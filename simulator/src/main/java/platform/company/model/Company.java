package platform.company.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = {"id", "name"})
public class Company {

    private Integer id;
    private String name;
    private String url;
    private String industry;
    private String country;

}
