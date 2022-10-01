package clients.service.mapper;

import clients.controller.model.ClientDto;
import clients.repository.entity.Client;
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
