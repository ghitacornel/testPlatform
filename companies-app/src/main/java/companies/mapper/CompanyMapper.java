package companies.mapper;

import companies.repository.entity.Company;
import contracts.companies.CompanyDetailsResponse;
import contracts.companies.CompanyRegisterRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface CompanyMapper {

    CompanyDetailsResponse map(Company model);

    @Mapping(target = "status", ignore = true)
    Company map(CompanyRegisterRequest model);

}
