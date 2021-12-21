package com.abraham.payments.usecases.createPayment;

import com.abraham.payments.exception.InvalidPaymentException;
import com.abraham.payments.exception.PaymentStorageException;
import com.abraham.payments.model.Payment;
import com.abraham.payments.service.PaymentRepository;
import com.abraham.payments.usecases.utils.ErrorLoggingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreatePaymentUseCase {

  @Autowired
  private PaymentRepository paymentRepository;

  @Autowired
  private ErrorLoggingService errorService;

  public void execute(final Payment payment) {

    try {
      payment.validate();
      this.paymentRepository.save(payment);
    } catch (final Exception e) {
      this.handleError(payment.getPaymentId(), e);
    }
  }

  private void handleError(final String paymentId, final Exception e) {

    final ErrorLoggingService.ErrorType errorType;
    final String message;

    if (e instanceof InvalidPaymentException) {
      errorType = ErrorLoggingService.ErrorType.NETWORK;
      message = "Invalid payment";
    } else if (e instanceof PaymentStorageException) {
      errorType = ErrorLoggingService.ErrorType.DATABASE;
      message = "Error storing payment: " + e.getMessage() + "\n" + e.getStackTrace();
    } else {
      errorType = ErrorLoggingService.ErrorType.OTHER;
      message = "Unexpected exception: " + e.getMessage() + "\n" + e.getStackTrace();
    }

    this.errorService.logError(paymentId, errorType, message);
  }
}
