package platform.routes.clients;

import lombok.*;

@Data
@EqualsAndHashCode(of = {"id", "name"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    private Integer id;
    private String name;
    private String cardType;
    private String country;

}
