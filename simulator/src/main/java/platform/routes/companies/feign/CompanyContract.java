package platform.routes.companies.feign;

import commons.model.IdResponse;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import platform.routes.clients.feign.ClientDetailsResponse;
import platform.routes.clients.feign.ClientRegisterRequest;

import java.util.List;

public interface CompanyContract {

    @RequestLine("GET /company/count")
    long count();

    @RequestLine("GET /company")
    List<CompanyDetailsResponse> findAll();

    @RequestLine("POST /company")
    @Headers("Content-Type: application/json")
    IdResponse create(CompanyRegisterRequest inputModel);

    @RequestLine("DELETE /company/{id}")
    void deleteById(@Param("id") Integer id);

}
