package companies.service.mapper;

import companies.controller.model.CompanyDto;
import companies.repository.entity.Company;
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
