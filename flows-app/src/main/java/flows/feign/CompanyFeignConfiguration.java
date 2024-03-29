package flows.feign;

import contracts.companies.CompanyContract;
import feign.Feign;
import feign.Logger;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class CompanyFeignConfiguration {

    @Bean
    CompanyContract companyContract() {
        return Feign.builder()
                .client(new OkHttpClient())
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .logger(new Slf4jLogger(CompanyContract.class))
                .logLevel(Logger.Level.FULL)
                .target(CompanyContract.class, "http://localhost:8091");
    }
}
