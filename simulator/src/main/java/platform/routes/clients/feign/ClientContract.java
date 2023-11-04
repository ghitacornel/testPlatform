package platform.routes.clients.feign;

import commons.model.IdResponse;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

import java.util.List;

public interface ClientContract {

    @RequestLine("GET /client/count")
    long count();

    @RequestLine("GET /client")
    List<ClientDetailsResponse> findAll();

    @RequestLine("POST /client")
    @Headers("Content-Type: application/json")
    IdResponse create(ClientRegisterRequest inputModel);

    @RequestLine("DELETE /client/{id}")
    void deleteById(@Param("id") Integer id);

}
