package com.abraham.payments.infrastructure.repository;

import com.abraham.payments.exception.PaymentStorageException;
import com.abraham.payments.model.Payment;
import com.abraham.payments.service.PaymentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class PaymentRepositoryImpl implements PaymentRepository {

  @Override
  public void save(final Payment payment) throws PaymentStorageException {
    log.info("Saved payment: {}", payment);
  }
}
