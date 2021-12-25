package com.abraham.payments;

import com.abraham.payments.event.dto.PaymentDto;
import com.abraham.payments.infrastructure.persistence.dao.AccountDAO;
import com.abraham.payments.infrastructure.persistence.dao.PaymentDAO;
import com.abraham.payments.infrastructure.service.client.ErrorLoggingRestClient;
import com.abraham.payments.infrastructure.service.client.PaymentValidationRestClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
@Import(TestChannelBinderConfiguration.class)
public class ConsumerIntegrationTest {

  @Autowired
  private InputDestination input;

  @SpyBean
  private PaymentDAO paymentDAO;

  @SpyBean
  private AccountDAO accountDAO;

  @MockBean
  private PaymentValidationRestClient paymentValidationRestClient;

  @MockBean
  private ErrorLoggingRestClient errorLoggingRestClient;

  @Test
  public void online_payments_are_processed() {

    // The H2 database contains an account whose id is 1
    final PaymentDto payment = PaymentDto.builder()
            .paymentId("123456").accountId("1").amount(32).creditCard("some-credit-card").type("online").build();

    // Given
    when(this.paymentValidationRestClient.validate(any())).thenReturn(true);

    // When
    this.input.send(new GenericMessage<PaymentDto>(payment));

    // Then
    verify(this.paymentValidationRestClient).validate(any());
    verify(this.paymentDAO).save(any());
    verify(this.accountDAO).updatePaymentDate(any(), any());
    verify(this.errorLoggingRestClient, never()).log(any());
  }

  @Test
  public void invalid_payments_are_registered_in_error_log() {

    // Given
    when(this.paymentValidationRestClient.validate(any())).thenReturn(false);

    // When
    this.input.send(new GenericMessage<PaymentDto>(PaymentDto.builder()
            .paymentId("98765").accountId("1").amount(32).creditCard("some-credit-card").type("online").build()));

    // Then
    verify(this.paymentValidationRestClient).validate(any());
    verify(this.paymentDAO, never()).save(any());
    verify(this.accountDAO, never()).updatePaymentDate(any(), any());
    verify(this.errorLoggingRestClient).log(any());
  }
}
