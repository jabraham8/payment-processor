package com.abraham.payments.event.mapper;


import com.abraham.payments.event.dto.PaymentDto;
import com.abraham.payments.model.Payment;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaymentMapperTest {

  PaymentMapper paymentMapper = new PaymentMapper();

  @Test
  public void paymentdto_is_mapped() {

    // Given
    final PaymentDto paymentDto = PaymentDto.builder()
            .paymentId("123456").accountId("123").amount(32).creditCard("some-credit-card").type("online").build();

    final Payment payment = this.paymentMapper.map(paymentDto);

    assertAll(() -> {
      assertEquals(paymentDto.getPaymentId(), payment.getPaymentId());
      assertEquals(paymentDto.getAccountId(), payment.getAccountId());
      assertEquals(paymentDto.getCreditCard(), payment.getCreditCard());
      assertEquals(paymentDto.getType(), payment.getType().getValue());
      assertEquals(paymentDto.getAmount(), payment.getAmount());
    });
  }
}
