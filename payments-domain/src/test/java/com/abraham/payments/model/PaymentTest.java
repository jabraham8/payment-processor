package com.abraham.payments.model;


import com.abraham.payments.exception.InvalidPaymentException;
import com.abraham.payments.service.ThirdPartyValidationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaymentTest {

  @Mock
  private ThirdPartyValidationService validationService;

  @Test
  public void online_payments_are_validated() {

    // Given
    final Payment payment = Payment.builder().type(PaymentType.ONLINE).build();

    // When
    payment.validate(this.validationService);

    // Then
    verify(this.validationService).validate(payment);
  }

  @Test
  public void offline_payments_are_not_validated() {

    // Given
    final Payment payment = Payment.builder().type(PaymentType.OFFLINE).build();

    // When
    payment.validate(this.validationService);

    // Then
    verify(this.validationService, never()).validate(any());
  }

  @Test()
  public void invalid_payment_is_thrown() {

    // Given
    final Payment payment = Payment.builder().type(PaymentType.ONLINE).build();
    doThrow(InvalidPaymentException.class).when(this.validationService).validate(any());

    // When
    assertThrows(InvalidPaymentException.class, () -> payment.validate(this.validationService));

    // Then
    verify(this.validationService).validate(payment);
  }
}
