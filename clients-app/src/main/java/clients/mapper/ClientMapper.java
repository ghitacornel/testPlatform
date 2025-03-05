package clients.mapper;

import clients.repository.entity.Client;
import contracts.clients.ClientDetailsResponse;
import contracts.clients.ClientRegisterRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    ClientDetailsResponse map(Client data);
    Client map(ClientRegisterRequest data);

}
