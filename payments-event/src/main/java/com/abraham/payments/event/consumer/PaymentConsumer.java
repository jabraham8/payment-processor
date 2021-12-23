package com.abraham.payments.event.consumer;

import com.abraham.payments.event.dto.PaymentDto;
import com.abraham.payments.event.mapper.PaymentMapper;
import com.abraham.payments.model.Payment;
import com.abraham.payments.usecases.createPayment.CreatePaymentUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Slf4j
@Component("input")
public class PaymentConsumer implements Consumer<PaymentDto> {

  @Autowired
  private CreatePaymentUseCase useCase;

  @Autowired
  private PaymentMapper mapper;

  @Override
  public void accept(final PaymentDto paymentDto) {

    log.debug("Consumed payment {}", paymentDto);

    final Payment payment = this.mapper.map(paymentDto);

    this.useCase.execute(payment);
  }
}
