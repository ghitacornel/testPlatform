package flows.config;

import contracts.clients.exceptions.ExceptionErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfiguration {

    @Bean
    ExceptionErrorDecoder errorDecoder() {
        return new ExceptionErrorDecoder();
    }

}