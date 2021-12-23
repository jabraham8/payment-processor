package com.abraham.payments.infrastructure.service;

import com.abraham.payments.exception.InvalidPaymentException;
import com.abraham.payments.model.Payment;
import com.abraham.payments.service.ThirdPartyValidationService;
import org.springframework.stereotype.Component;

@Component
public class ThirdPartyValidationServiceImpl implements ThirdPartyValidationService {

  @Override
  public void validate(final Payment payment) throws InvalidPaymentException {
    // Do nothing so far
  }
}
