package platform.random;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import platform.client.model.Client;
import platform.client.service.ClientService;
import platform.common.AbstractActor;
import platform.company.model.Company;
import platform.company.service.CompanyService;
import platform.order.model.Order;
import platform.order.service.OrderService;
import platform.product.model.Product;
import platform.product.service.ProductService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RandomDataFetchService extends AbstractActor {

    private final ProductService productService;
    private final CompanyService companyService;
    private final ClientService clientService;

    private final OrderService orderService;

    public Company findRandomCompany() {
        List<Company> all = companyService.findAll();
        if (all.isEmpty()) return null;
        int index = random.nextInt(all.size());
        return all.get(index);
    }

    public Client findRandomClient() {
        List<Client> all = clientService.findAll();
        if (all.isEmpty()) return null;
        int index = random.nextInt(all.size());
        return all.get(index);
    }

    public Product findRandomProduct() {
        List<Product> all = productService.findAll();
        if (all.isEmpty()) return null;
        int index = random.nextInt(all.size());
        return all.get(index);
    }

    public Order findRandomOrder() {
        List<Order> all = orderService.findAll();
        if (all.isEmpty()) return null;
        int index = random.nextInt(all.size());
        return all.get(index);
    }

}
