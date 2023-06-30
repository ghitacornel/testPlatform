package companies.mapper;

import companies.controller.model.request.CompanyRegisterRequest;
import companies.controller.model.response.CompanyDetailsResponse;
import companies.repository.entity.Company;
import org.mapstruct.Mapper;

@Mapper
public interface CompanyMapper {
    CompanyDetailsResponse map(Company model);
    Company map(CompanyRegisterRequest model);

}
