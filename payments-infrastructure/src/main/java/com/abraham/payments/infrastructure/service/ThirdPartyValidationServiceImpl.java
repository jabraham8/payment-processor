package com.abraham.payments.infrastructure.service;

import com.abraham.payments.exception.InvalidPaymentException;
import com.abraham.payments.infrastructure.service.client.PaymentValidationRestClient;
import com.abraham.payments.infrastructure.service.client.dto.PaymentValidationDto;
import com.abraham.payments.infrastructure.service.mapper.PaymentValidationDtoMapper;
import com.abraham.payments.model.Payment;
import com.abraham.payments.service.ThirdPartyValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ThirdPartyValidationServiceImpl implements ThirdPartyValidationService {

  @Autowired
  private PaymentValidationDtoMapper mapper;

  @Autowired
  private PaymentValidationRestClient client;

  @Override
  public void validate(final Payment payment) throws InvalidPaymentException {

    final PaymentValidationDto dto = this.mapper.map(payment);

    if (!this.client.validate(dto)) {
      throw new InvalidPaymentException();
    }
  }
}
