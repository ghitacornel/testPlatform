package contracts.clients;

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

    @RequestLine("GET /client/{id}")
    ClientDetailsResponse findById(@Param("id") Integer id);

    @RequestLine("POST /client")
    @Headers("Content-Type: application/json")
    IdResponse register(ClientRegisterRequest inputModel);

    @RequestLine("DELETE /client/{id}")
    void unregister(@Param("id") Integer id);

}
