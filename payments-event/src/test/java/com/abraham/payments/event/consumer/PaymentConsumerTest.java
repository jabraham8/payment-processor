package com.abraham.payments.event.consumer;

import com.abraham.payments.event.dto.PaymentDto;
import com.abraham.payments.event.mapper.PaymentMapper;
import com.abraham.payments.model.Payment;
import com.abraham.payments.usecases.createPayment.CreatePaymentUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PaymentConsumerTest {

  @Mock
  CreatePaymentUseCase useCase;

  @Mock
  PaymentMapper mapper;

  @InjectMocks
  PaymentConsumer paymentConsumer;

  @Test
  public void payments_are_processed() {

    // Given
    final PaymentDto paymentDto = PaymentDto.builder()
            .paymentId("123456").accountId("123").amount(32).creditCard("some-credit-card").type("online").build();
    when(this.mapper.map(paymentDto)).thenReturn(Payment.builder().paymentId("987").build());

    // When
    this.paymentConsumer.accept(paymentDto);

    final ArgumentCaptor<Payment> paymentCaptor = ArgumentCaptor.forClass(Payment.class);
    verify(this.mapper).map(paymentDto);
    verify(this.useCase).execute(paymentCaptor.capture());
    assertEquals("987", paymentCaptor.getValue().getPaymentId());
  }
}
