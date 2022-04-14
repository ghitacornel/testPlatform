package clients.services.mappers;

import clients.controllers.models.ClientDto;
import clients.repositories.entities.Client;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper {

    public ClientDto map(Client data) {
        return ClientDto.builder()
                .id(data.getId())
                .name(data.getName())
                .cardType(data.getCardType())
                .build();
    }
}
