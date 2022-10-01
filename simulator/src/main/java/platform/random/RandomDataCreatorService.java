package platform.random;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import platform.client.model.Client;
import platform.common.AbstractActor;
import platform.company.model.Company;
import platform.product.model.Product;
import platform.product.model.ProductBuy;
import platform.product.model.ProductSale;

@Service
@RequiredArgsConstructor
@Slf4j
public class RandomDataCreatorService extends AbstractActor {

    private final RandomDataFetchService randomDataFetchService;

    public Client createClient() {
        Client item = new Client();
        item.setName(faker.name().username());
        item.setCardType(faker.business().creditCardType());
        return item;
    }

    public Company createCompany() {
        Company item = new Company();
        item.setName(faker.company().name());
        item.setIndustry(faker.company().industry());
        item.setUrl(faker.company().url());
        return item;
    }

    public ProductSale createProductSale() {
        Company company = randomDataFetchService.findRandomCompany();
        if (company == null) return null;

        ProductSale item = new ProductSale();
        item.setName(faker.commerce().productName());
        item.setColor(faker.commerce().color());
        item.setPrice(Double.valueOf(faker.commerce().price(1, 100)));
        item.setQuantity(random.nextInt(10000) + 100);
        item.setCompanyId(company.getId());
        return item;
    }

    public ProductBuy createProductBuy() {
        Client client = randomDataFetchService.findRandomClient();
        if (client == null) return null;
        Product product = randomDataFetchService.findRandomProduct();
        if (product == null) return null;

        ProductBuy item = new ProductBuy();
        item.setUserName(client.getName());
        Integer existingQuantity = product.getQuantity();
        item.setProductId(product.getId());
        int quantity = random.nextInt(existingQuantity + 1);
        if (quantity == 0) quantity++;
        item.setQuantity(quantity);
        return item;
    }

}
