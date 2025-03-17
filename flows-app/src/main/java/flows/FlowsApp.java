package flows;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@EnableFeignClients
@EnableScheduling
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class FlowsApp {
    public static void main(String[] args) {
        SpringApplication.run(FlowsApp.class, args);
    }
}
