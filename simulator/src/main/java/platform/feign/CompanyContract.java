package platform.feign;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "companies-cloud")
public interface CompanyContract extends contracts.companies.CompanyContract {
}
