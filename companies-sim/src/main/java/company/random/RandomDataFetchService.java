package company.random;

import company.common.AbstractActor;
import company.model.Company;
import company.service.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RandomDataFetchService extends AbstractActor {

    private final CompanyService companyService;

    public Company findRandomCompany() {
        List<Company> all = companyService.findAll();
        if (all.isEmpty()) return null;
        int index = random.nextInt(all.size());
        return all.get(index);
    }

}
