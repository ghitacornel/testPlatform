package contracts.companies;

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

    @RequestLine("GET /company/{id}")
    @Headers("Content-Type: application/json")
    CompanyDetailsResponse findById(@Param("id") Integer id);

    @RequestLine("POST /company")
    @Headers("Content-Type: application/json")
    IdResponse create(CompanyRegisterRequest inputModel);

    @RequestLine("DELETE /company/{id}")
    void delete(@Param("id") Integer id);

    @RequestLine("PATCH /company/retire/{id}")
    void retire(@Param("id") Integer id);
}
