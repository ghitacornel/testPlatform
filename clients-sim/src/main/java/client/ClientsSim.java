package client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ClientsSim {
    public static void main(String[] args) {
        SpringApplication.run(ClientsSim.class, args);
    }
}
