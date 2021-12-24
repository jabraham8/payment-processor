package com.abraham.payments.infrastructure.service.mapper;

import com.abraham.payments.infrastructure.service.client.dto.PaymentValidationDto;
import com.abraham.payments.model.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentValidationDtoMapper {

  public PaymentValidationDto map(final Payment payment) {
    return PaymentValidationDto.builder()
            .accountId(Integer.valueOf(payment.getAccountId()))
            .paymentId(payment.getPaymentId())
            .amount(payment.getAmount())
            .creditCard(payment.getCreditCard())
            .paymentType(payment.getType().getValue())
            .build();
  }
}
