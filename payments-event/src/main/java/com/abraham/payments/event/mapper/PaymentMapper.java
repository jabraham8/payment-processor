package com.abraham.payments.event.mapper;


import com.abraham.payments.event.dto.PaymentDto;
import com.abraham.payments.model.Payment;
import com.abraham.payments.model.PaymentType;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class PaymentMapper {

  // Done this way to keep it simple, for production projects we should use a mapping library like mapstruct
  public Payment map(final PaymentDto dto) {
    return Payment.builder()
            .type(PaymentType.valueOf(dto.getType().toUpperCase(Locale.ROOT)))
            .paymentId(dto.getPaymentId())
            .accountId(dto.getAccountId())
            .amount(dto.getAmount())
            .creditCard(dto.getCreditCard())
            .build();
  }
}
