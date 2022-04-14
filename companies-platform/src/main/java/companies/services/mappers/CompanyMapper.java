package companies.services.mappers;

import companies.controllers.models.CompanyDto;
import companies.repositories.entities.Company;
import org.springframework.stereotype.Component;

@Component
public class CompanyMapper {
    public CompanyDto map(Company model) {
        return CompanyDto.builder()
                .id(model.getId())
                .name(model.getName())
                .url(model.getUrl())
                .industry(model.getIndustry())
                .build();
    }

}
