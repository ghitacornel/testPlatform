package flows.feign;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "invoices-cloud")
public interface InvoiceContract extends contracts.invoices.InvoiceContract {
}
