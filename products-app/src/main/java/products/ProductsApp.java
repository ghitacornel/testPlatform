package products;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ProductsApp {
    public static void main(String[] args) {
        SpringApplication.run(ProductsApp.class, args);
    }
}
