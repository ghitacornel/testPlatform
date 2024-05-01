package flows;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class FlowsApp {
    public static void main(String[] args) {
        SpringApplication.run(FlowsApp.class, args);
    }
}
