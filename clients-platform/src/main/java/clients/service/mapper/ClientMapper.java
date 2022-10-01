package clients.service.mapper;

import clients.controller.model.response.ClientDetailsResponse;
import clients.controller.model.request.ClientRegisterRequest;
import clients.repository.entity.Client;
import org.mapstruct.Mapper;

@Mapper
public interface ClientMapper {

    ClientDetailsResponse map(Client data);
     Client map(ClientRegisterRequest data);

}
