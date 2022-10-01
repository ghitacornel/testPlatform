package companies.service.mapper;

import companies.controller.model.CompanyDto;
import companies.repository.entity.Company;
import org.mapstruct.Mapper;

@Mapper
public interface CompanyMapper {
    CompanyDto map(Company model);

}
