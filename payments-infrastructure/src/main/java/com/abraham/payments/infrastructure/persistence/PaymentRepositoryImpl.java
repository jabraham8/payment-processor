package com.abraham.payments.infrastructure.persistence;

import com.abraham.payments.exception.PaymentStorageException;
import com.abraham.payments.infrastructure.persistence.dao.AccountDAO;
import com.abraham.payments.infrastructure.persistence.dao.PaymentDAO;
import com.abraham.payments.infrastructure.persistence.dao.dto.PaymentDto;
import com.abraham.payments.infrastructure.persistence.mapper.PaymentDtoMapper;
import com.abraham.payments.infrastructure.persistence.utils.TransactionComposer;
import com.abraham.payments.model.Payment;
import com.abraham.payments.service.PaymentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

  @Autowired
  private TransactionComposer transactionComposer;

  @Override
  public void save(final Payment payment) throws PaymentStorageException {

    log.debug("Proceeding to save payment {}", payment);
    final PaymentDto paymentDto = this.paymentMapper.from(payment);

    try {
      this.transactionComposer.execute(
              () -> this.paymentDAO.save(paymentDto),
              () -> this.accountDAO.updatePaymentDate(Instant.now(), paymentDto.getAccountId()));
    } catch (final Exception e) {
      // Wrap any internal database exception in the domain exception for persistence
      throw new PaymentStorageException("Some error occurred during the persistence of the payment", e);
    }
  }
}
