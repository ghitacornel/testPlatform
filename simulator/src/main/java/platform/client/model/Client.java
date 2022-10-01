package platform.client.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = {"id"})
public class Client {

    private Integer id;
    private String name;
    private String cardType;

}
