package com.abraham.payments.service;

import com.abraham.payments.exception.PaymentStorageException;
import com.abraham.payments.model.Payment;

public interface PaymentRepository {

  void save(Payment payment) throws PaymentStorageException;
}
