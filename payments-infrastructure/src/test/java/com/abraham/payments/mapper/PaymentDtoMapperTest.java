package com.abraham.payments.mapper;

import com.abraham.payments.infrastructure.dto.PaymentDto;
import com.abraham.payments.infrastructure.mapper.PaymentDtoMapper;
import com.abraham.payments.model.Payment;
import com.abraham.payments.model.PaymentType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaymentDtoMapperTest {

  private final PaymentDtoMapper mapper = new PaymentDtoMapper();

  @Test
  public void payment_dto_is_mapped() {

    // Given
    final Payment payment = Payment.builder()
            .paymentId("123456").accountId("123").amount(32).creditCard("some-credit-card").type(PaymentType.ONLINE)
            .build();

    // When
    final PaymentDto dto = this.mapper.from(payment);

    // Then
    assertAll(() -> {
      assertEquals(payment.getPaymentId(), dto.getPaymentId());
      assertEquals(payment.getAccountId(), String.valueOf(dto.getAccountId()));
      assertEquals(payment.getAmount(), dto.getAmount());
      assertEquals(payment.getCreditCard(), dto.getCreditCard());
      assertEquals(payment.getType().getValue(), dto.getPaymentType());
    });
  }
}
