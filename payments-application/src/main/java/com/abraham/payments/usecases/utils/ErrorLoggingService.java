package com.abraham.payments.usecases.utils;

// Placed in application layer as this should be considered a utility that could be used
// to report errors in different use-cases
public interface ErrorLoggingService {

  void logError(String paymentId, ErrorType errorType, String description);

  public enum ErrorType {
    DATABASE("database"), NETWORK("network"), OTHER("other");

    private final String value;

    ErrorType(final String value) {
      this.value = value;
    }

    public String getValue() {
      return this.value;
    }
  }
}
