package clients.mapper;

import clients.repository.entity.Client;
import clients.repository.entity.ClientArchive;
import contracts.clients.ClientDetailsResponse;
import contracts.clients.ClientRegisterRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    ClientDetailsResponse map(Client data);

    ClientArchive mapToArchive(Client data);

    @Mapping(target = "status", ignore = true)
    Client map(ClientRegisterRequest data);

}
