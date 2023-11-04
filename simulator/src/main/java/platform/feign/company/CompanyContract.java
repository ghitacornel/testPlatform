package platform.feign.company;

import commons.model.IdResponse;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

import java.util.List;

public interface CompanyContract {

    @RequestLine("GET /company/count")
    long count();

    @RequestLine("GET /company")
    List<CompanyDetailsResponse> findAll();

    @RequestLine("POST /company")
    @Headers("Content-Type: application/json")
    IdResponse register(CompanyRegisterRequest inputModel);

    @RequestLine("DELETE /company/{id}")
    void unregister(@Param("id") Integer id);

}
