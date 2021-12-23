package com.abraham.payments.infrastructure.service;

import com.abraham.payments.usecases.utils.ErrorLoggingService;
import org.springframework.stereotype.Component;

@Component
public class ErrorLoggingServiceImpl implements ErrorLoggingService {

  @Override
  public void logError(final String paymentId, final ErrorType errorType, final String description) {
    // Do nothing so far
  }
}
