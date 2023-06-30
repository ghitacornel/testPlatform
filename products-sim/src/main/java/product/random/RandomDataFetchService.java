package product.random;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import product.common.AbstractActor;
import product.model.Product;
import product.service.ProductService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RandomDataFetchService extends AbstractActor {

    private final ProductService productService;

    public Product findRandomProduct() {
        List<Product> all = productService.findAll();
        if (all.isEmpty()) return null;
        int index = random.nextInt(all.size());
        return all.get(index);
    }

}
