package flows.clients;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "invoices-cloud")
public interface InvoiceClient extends contracts.invoices.InvoiceContract {
}
