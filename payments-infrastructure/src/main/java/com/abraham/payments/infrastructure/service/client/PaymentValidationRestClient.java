package com.abraham.payments.infrastructure.service.client;

import com.abraham.payments.infrastructure.service.client.dto.PaymentValidationDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
@Slf4j
public class PaymentValidationRestClient {

  private static final String ROOT_URI = "http://localhost:9000";
  private static final String API_PATH_VALIDATE = "/payment";

  private final WebClient webClient = WebClient.builder()
          .baseUrl(ROOT_URI)
          .defaultHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
          .build();

  /**
   * Validates if payment is valid by invoking a third-party api
   *
   * @param paymentValidationDto payment information
   * @return whether the payment is valid or not
   */
  // TODO handle errors & circuit breaker
  public boolean validate(final PaymentValidationDto paymentValidationDto) {

    log.debug("Invoking third party payment validation with body: {}", paymentValidationDto);

    final HttpStatus httpStatusResult = this.webClient
            .post()
            .uri(API_PATH_VALIDATE)
            .bodyValue(paymentValidationDto)
            .retrieve()
            .toBodilessEntity()
            .block()
            .getStatusCode();

    return HttpStatus.OK.equals(httpStatusResult);
  }
}
