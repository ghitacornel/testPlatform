package companies.mapper;

import companies.repository.entity.Company;
import companies.repository.entity.CompanyArchive;
import contracts.companies.CompanyDetailsResponse;
import contracts.companies.CompanyRegisterRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    CompanyDetailsResponse map(Company model);

    CompanyArchive mapToArchive(Company model);

    @Mapping(target = "status", ignore = true)
    Company map(CompanyRegisterRequest model);

}
