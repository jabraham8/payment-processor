package com.abraham.payments.infrastructure.service.mapper;

import com.abraham.payments.infrastructure.service.client.dto.PaymentValidationDto;
import com.abraham.payments.model.Payment;
import com.abraham.payments.model.PaymentType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaymentValidationDtoMapperTest {

  private final PaymentValidationDtoMapper mapper = new PaymentValidationDtoMapper();

  @Test
  public void payment_validation_dto_is_mapped() {

    // Given
    final Payment payment = Payment.builder()
            .paymentId("123456").accountId("123").amount(32).creditCard("some-credit-card").type(PaymentType.ONLINE)
            .build();

    // When
    final PaymentValidationDto dto = this.mapper.map(payment);

    // Then
    assertAll(() -> {
      assertEquals(payment.getPaymentId(), dto.getPaymentId());
      assertEquals(payment.getAccountId(), String.valueOf(dto.getAccountId()));
      assertEquals(payment.getCreditCard(), dto.getCreditCard());
      assertEquals(payment.getAmount(), dto.getAmount());
      assertEquals(payment.getType().getValue(), dto.getPaymentType());
    });
  }
}
