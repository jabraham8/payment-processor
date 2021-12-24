package com.abraham.payments.infrastructure.service;

import com.abraham.payments.infrastructure.service.client.ErrorLoggingRestClient;
import com.abraham.payments.infrastructure.service.client.dto.ErrorLogDto;
import com.abraham.payments.usecases.utils.ErrorLoggingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ErrorLoggingServiceImpl implements ErrorLoggingService {

  @Autowired
  private ErrorLoggingRestClient restClient;

  @Override
  public void logError(final String paymentId, final ErrorType errorType, final String description) {

    final ErrorLogDto dto = ErrorLogDto.builder()
            .paymentId(paymentId).type(errorType.getValue()).description(description).build();

    try {
      this.restClient.log(dto);
    } catch (final Exception e) {
      // If there is any error logging the error remotely, the only thing we can do (beyond retrying)
      // is leave a trace so the error can be manually created later in the error log system
      log.error("Error trying to report error for element {}", dto);
    }
  }
}
