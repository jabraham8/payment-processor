package com.abraham.payments.service;

import com.abraham.payments.exception.InvalidPaymentException;
import com.abraham.payments.model.Payment;

public interface ThirdPartyValidationService {

   void validate(Payment payment) throws InvalidPaymentException;
}
