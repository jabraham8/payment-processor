package com.abraham.payments.infrastructure.service.client;

import com.abraham.payments.infrastructure.service.client.dto.ErrorLogDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
@Slf4j
public class ErrorLoggingRestClient {

  private static final String ROOT_URI = "http://localhost:9000";
  private static final String API_PATH_LOG = "/log";

  private final WebClient webClient = WebClient.builder()
          .baseUrl(ROOT_URI)
          .defaultHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
          .build();

  // TODO handle errors & circuit breaker
  public void log(final ErrorLogDto errorLogDto) {

    log.debug("Proceeding to log error with body: {}", errorLogDto);

    this.webClient
         .post()
         .uri(API_PATH_LOG)
         .bodyValue(errorLogDto)
         .retrieve()
         .toBodilessEntity()
         .block();
  }
}
