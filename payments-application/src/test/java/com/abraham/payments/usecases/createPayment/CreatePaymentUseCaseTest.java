package com.abraham.payments.usecases.createPayment;

import com.abraham.payments.exception.InvalidPaymentException;
import com.abraham.payments.exception.PaymentStorageException;
import com.abraham.payments.model.Payment;
import com.abraham.payments.service.PaymentRepository;
import com.abraham.payments.service.ThirdPartyValidationService;
import com.abraham.payments.usecases.utils.ErrorLoggingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.InvalidParameterException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
public class CreatePaymentUseCaseTest {

  @Mock
  private ErrorLoggingService errorLoggingService;

  @Mock
  private PaymentRepository paymentRepository;

  @Mock
  private ThirdPartyValidationService validationService;

  @InjectMocks
  private CreatePaymentUseCase useCase;

  @Test
  void valid_payment_are_stored(@Mock final Payment payment) {

    // When
    this.useCase.execute(payment);

    // Then
    verify(payment).validate(this.validationService);
    verify(this.paymentRepository).save(payment);
    verify(this.errorLoggingService, never()).logError(any(), any(), any());
  }

  @Test
  void invalid_payments_are_logged(@Mock final Payment payment) {

    //Given
    doReturn("123456").when(payment).getPaymentId();
    doThrow(InvalidPaymentException.class).when(payment).validate(any());

    // When
    this.useCase.execute(payment);

    // Then
    verify(payment).validate(this.validationService);
    verify(this.paymentRepository, never()).save(payment);
    verify(this.errorLoggingService).logError(eq("123456"), eq(ErrorLoggingService.ErrorType.NETWORK), any());
  }

  @Test
  void database_failures_are_logged(@Mock final Payment payment) {

    //Given
    doReturn("123456").when(payment).getPaymentId();
    doThrow(new PaymentStorageException("some-message", new InvalidParameterException())).when(this.paymentRepository).save(payment);

    // When
    this.useCase.execute(payment);

    // Then
    verify(payment).validate(this.validationService);
    verify(this.paymentRepository).save(payment);
    verify(this.errorLoggingService).logError(eq("123456"), eq(ErrorLoggingService.ErrorType.DATABASE), any());
  }
}
