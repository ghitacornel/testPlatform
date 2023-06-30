package product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ProductsSim {
    public static void main(String[] args) {
        SpringApplication.run(ProductsSim.class, args);
    }
}
