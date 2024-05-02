package platform.clients;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "companies-cloud")
public interface CompanyClient extends contracts.companies.CompanyContract {
}
