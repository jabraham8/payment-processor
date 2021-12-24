package com.abraham.payments.infrastructure.persistence.mapper;

import com.abraham.payments.infrastructure.persistence.dao.dto.PaymentDto;
import com.abraham.payments.model.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentDtoMapper {

  public PaymentDto from(final Payment payment) {
    return PaymentDto.builder()
            .paymentId(payment.getPaymentId())
            .accountId(Integer.valueOf(payment.getAccountId()))
            .amount(payment.getAmount())
            .creditCard(payment.getCreditCard())
            .paymentType(payment.getType().getValue()).build();
  }
}
