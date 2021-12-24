package com.abraham.payments.configuration;

import com.abraham.payments.event.dto.PaymentDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.util.Random;
import java.util.UUID;
import java.util.function.Supplier;

/**
 * Class that generates payments for testing the application.
 * Every second it generates three types of payments and publishes it to their respective topics:
 * <li/> an online payment
 * <li/> an offline payment
 * <li/> a failing payment (so it is reported to the logging system)
 */
@Configuration
public class TestProducers {

  private static final Random RANDOM = new Random();

  @Bean("online-producer")
  public Supplier<Message<PaymentDto>> onlineProducer() {
    return () -> {
      return MessageBuilder.withPayload(this.getRandomPayment("online")).build();
    };
  }

  @Bean("offline-producer")
  public Supplier<Message<PaymentDto>> offlineProducer() {
    return () -> {
      return MessageBuilder.withPayload(this.getRandomPayment("offline")).build();
    };
  }

  @Bean("failing-producer")
  public Supplier<Message<PaymentDto>> offlineFailingProducer() {
    return () -> {

      final PaymentDto paymentDto = this.getRandomPayment("offline");
      // As database has a constraint that accountId must be max 4 digits in payments table, these will
      // make payment storage to fail, generating error logging
      paymentDto.setAccountId(String.valueOf(
              RANDOM.ints(1, 10000, 100000).findFirst().getAsInt()));

      return MessageBuilder.withPayload(paymentDto).build();
    };
  }

  /**
   * Generates a payment with random valid values.
   *
   * @param type of the payment. 'online' or 'offline'
   * @return a valid payment of the provided type
   */
  private PaymentDto getRandomPayment(final String type) {
    return  PaymentDto.builder()
            .paymentId(UUID.randomUUID().toString())
            .type(type)
            .creditCard(String.valueOf(RANDOM.nextInt(1000000)))
            .accountId(String.valueOf(RANDOM.ints(1, 1, 101).findFirst().getAsInt()))
            .amount(RANDOM.nextInt(100))
            .build();
  }
}
