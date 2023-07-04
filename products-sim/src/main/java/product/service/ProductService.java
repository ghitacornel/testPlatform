package product.service;

import commons.model.IdResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import product.common.RESTClient;
import product.model.Product;
import product.model.ProductBuy;
import product.model.ProductSell;

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
        log.info("cancelling " + item);
    }

    public IdResponse sale(ProductSell item) {
        IdResponse product = restClient.products()
                .post()
                .uri("/product/sale")
                .bodyValue(item)
                .retrieve()
                .bodyToMono(IdResponse.class)
                .block();
        log.info("selling " + product);
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
        log.info("buying " + item);
    }

}
