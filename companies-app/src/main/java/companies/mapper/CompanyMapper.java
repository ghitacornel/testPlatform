package companies.mapper;

import companies.repository.entity.Company;
import contracts.companies.CompanyDetailsResponse;
import contracts.companies.CompanyRegisterRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    CompanyDetailsResponse map(Company model);

    @Mapping(target = "status", ignore = true)
    Company map(CompanyRegisterRequest model);

}
