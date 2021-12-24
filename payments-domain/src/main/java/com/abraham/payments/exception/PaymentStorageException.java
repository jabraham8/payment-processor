package com.abraham.payments.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PaymentStorageException extends RuntimeException {

  private static final long serialVersionUID = 8959647128123556612L;

  public PaymentStorageException(final String message, final Throwable cause) {
    super(message, cause);
  }
}
