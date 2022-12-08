package platform.product.service;

import commons.model.IdResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import platform.common.RESTClient;
import platform.product.model.Product;
import platform.product.model.ProductBuy;
import platform.product.model.ProductSale;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final RESTClient restClient;

    public List<Product> findAll() {
        return restClient.products()
                .get()
                .uri("/product")
                .retrieve()
                .bodyToFlux(Product.class)
                .collectList()
                .block();
    }

    public Long countAll() {
        return restClient.products()
                .get()
                .uri("product/count")
                .retrieve()
                .bodyToMono(Long.class)
                .block();
    }

    public void cancel(Product item) {
        restClient.products()
                .delete()
                .uri("/product/" + item.getId())
                .retrieve()
                .toBodilessEntity()
                .block();
        log.debug("cancelling " + item);
    }

    public IdResponse sale(ProductSale item) {
        IdResponse product = restClient.products()
                .post()
                .uri("/product/sale")
                .bodyValue(item)
                .retrieve()
                .bodyToMono(IdResponse.class)
                .block();
        log.debug("selling " + product);
        return product;
    }

    public void buy(ProductBuy item) {
        restClient.products()
                .put()
                .uri("/product/buy")
                .bodyValue(item)
                .retrieve()
                .toBodilessEntity()
                .block();
        log.debug("buying " + item);
    }

}
