package companies.mapper;

import companies.controller.model.request.CompanyRegisterRequest;
import companies.controller.model.response.CompanyDetailsResponse;
import companies.repository.entity.Company;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CompanyMapper {
    CompanyDetailsResponse map(Company model);

    @Mapping(target = "status", ignore = true)
    Company map(CompanyRegisterRequest model);

}
