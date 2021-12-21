package com.abraham.payments.model;


import com.abraham.payments.service.ThirdPartyValidationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PaymentTest {

  @Mock
  private ThirdPartyValidationService validationService;

  @Test
  public void online_payments_are_validated() {

    // Given
    final Payment payment = Payment.builder().type(PaymentType.ONLINE).validationService(this.validationService).build();

    // When
    payment.validate();

    // Then
    verify(this.validationService).validate(payment);
  }

  @Test
  public void offline_payments_are_not_validated() {

    // Given
    final Payment payment = Payment.builder().type(PaymentType.OFFLINE).validationService(this.validationService).build();

    // When
    payment.validate();

    // Then
    verify(this.validationService, never()).validate(any());
  }
}
