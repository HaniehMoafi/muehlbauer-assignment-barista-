package de.muehlbauer.assignment.barista.service.impl;

import de.muehlbauer.assignment.barista.exception.BaristaServiceException;
import de.muehlbauer.assignment.barista.service.OrderGateway;
import de.muehlbauer.assignment.order.api.response.GeneralOrderApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import static de.muehlbauer.assignment.barista.util.Constant.FINISH_ENDPOINT;

@RequiredArgsConstructor
@Service
public class OrderGatewayImpl implements OrderGateway {

    private final RestTemplate restTemplate;

    @Value("${order.service.base.url}")
    private String orderBaseUrl;


    @Override
    public void finishOrder(Integer orderNumber) throws BaristaServiceException {
        String url = urlBuilder(orderBaseUrl + FINISH_ENDPOINT, "orderNumber", orderNumber);
        try {
            GeneralOrderApiResponse response = restTemplate.patchForObject(url, null,
                    GeneralOrderApiResponse.class);
            checkBaristaHttpStatus(response);
        } catch (Throwable t) {
            handleOrderException(t, "barista.order.could.not.finished");
        }
    }

    private <T> String urlBuilder(String url, String paramName, T paramValue) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
                .queryParam(paramName, paramValue);
        return builder.toUriString();
    }

    private void checkBaristaHttpStatus(GeneralOrderApiResponse response) throws BaristaServiceException {
        HttpStatus status = HttpStatus.valueOf(response.getStatusCode());
        if (!status.is2xxSuccessful()) throw new BaristaServiceException(response.getMessage());
    }

    private void handleOrderException(Throwable t, String defaultException) throws BaristaServiceException {
        if (t instanceof BaristaServiceException) throw (BaristaServiceException) t;
        else throw new BaristaServiceException(defaultException);
    }
}
