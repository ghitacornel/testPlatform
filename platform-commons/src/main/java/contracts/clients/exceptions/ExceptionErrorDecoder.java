package contracts.clients.exceptions;

import commons.exceptions.BusinessException;
import commons.exceptions.RestTechnicalException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;

public class ExceptionErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        String requestUrl = response.request().url();
        HttpStatus responseStatus = HttpStatus.valueOf(response.status());

        if (responseStatus.is5xxServerError()) {
            return new RestTechnicalException(requestUrl, toMessage(response));
        }
        if (responseStatus.is4xxClientError()) {
            return new BusinessException(toMessage(response));
        }
        return new RestTechnicalException(requestUrl, toMessage(response));
    }

    private static String toMessage(Response response) {
        try {
            return new String(response.body().asInputStream().readAllBytes(), response.charset());
        } catch (Exception e) {
            return response.reason();
        }
    }

}