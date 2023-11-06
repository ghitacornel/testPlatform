package flows.feign.invoice;

import feign.Feign;
import feign.Logger;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class InvoiceFeignConfiguration {

    @Bean
    InvoiceContract invoiceContract() {
        return Feign.builder()
                .client(new OkHttpClient())
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .logger(new Slf4jLogger(InvoiceContract.class))
                .logLevel(Logger.Level.FULL)
                .target(InvoiceContract.class, "http://localhost:8094");
    }
}
