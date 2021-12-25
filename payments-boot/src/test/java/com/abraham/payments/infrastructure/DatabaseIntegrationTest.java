package com.abraham.payments.infrastructure;

import com.abraham.payments.infrastructure.persistence.dao.AccountDAO;
import com.abraham.payments.infrastructure.persistence.dao.PaymentDAO;
import com.abraham.payments.infrastructure.persistence.dao.dto.AccountDto;
import com.abraham.payments.infrastructure.persistence.dao.dto.PaymentDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest(properties = {
        "spring.datasource.platform=h2",
        "spring.jpa.hibernate.ddl-auto=none"
})
public class DatabaseIntegrationTest {

  @Autowired
  private PaymentDAO paymentDAO;

  @Autowired
  private AccountDAO accountDAO;

  @Test
  public void payment_is_stored_and_account_updated() {

    // Given
    final String paymentId = UUID.randomUUID().toString();
    final PaymentDto paymentDto = PaymentDto.builder()
      .paymentId(paymentId).accountId(1).amount(32).creditCard("some-credit-card").paymentType("online")
      .build();
    final Instant now = Instant.now();

    // When
    this.paymentDAO.save(paymentDto);
    this.accountDAO.updatePaymentDate(now, paymentDto.getAccountId());

    // Then
    final PaymentDto storedPayment = this.paymentDAO.findById(paymentId).get();
    final AccountDto storedAccount = this.accountDAO.findById(paymentDto.getAccountId()).get();
    assertEquals(paymentDto, storedPayment);

    // Compare the epoch seconds instead of the object as database does not store the same millisecond precision
    assertEquals(now.getEpochSecond(), storedAccount.getLastPaymentDate().getEpochSecond());
  }
}
