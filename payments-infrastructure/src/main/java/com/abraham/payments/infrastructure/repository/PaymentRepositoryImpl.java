package com.abraham.payments.infrastructure.repository;

import com.abraham.payments.exception.PaymentStorageException;
import com.abraham.payments.infrastructure.dao.AccountDAO;
import com.abraham.payments.infrastructure.dao.PaymentDAO;
import com.abraham.payments.infrastructure.dto.PaymentDto;
import com.abraham.payments.infrastructure.mapper.PaymentDtoMapper;
import com.abraham.payments.model.Payment;
import com.abraham.payments.service.PaymentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Component
@Slf4j
public class PaymentRepositoryImpl implements PaymentRepository {

  @Autowired
  private PaymentDAO paymentDAO;

  @Autowired
  private AccountDAO accountDAO;

  @Autowired
  private PaymentDtoMapper paymentMapper;

  @Override
  @Transactional
  public void save(final Payment payment) throws PaymentStorageException {

    log.info("Received payment {}", payment);
    final PaymentDto paymentDto = this.paymentMapper.from(payment);

    try {
      this.paymentDAO.save(paymentDto);
      this.accountDAO.updatePaymentDate(Instant.now(), paymentDto.getAccountId());
    } catch (final Exception e) {
      throw new PaymentStorageException("Some error occurred during the persistence of the payment", e);
    }
  }
}
