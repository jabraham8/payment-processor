package com.abraham.payments;

import com.abraham.payments.event.dto.PaymentDto;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.util.Random;
import java.util.function.Supplier;

@SpringBootApplication
public class Application {
  private static final Random RANDOM = new Random();

  public static void main(final String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Bean("online-producer")
  public Supplier<Message<PaymentDto>> onlineProducer() {
    return () -> {
      return MessageBuilder.withPayload(
              PaymentDto.builder()
                      .paymentId(String.valueOf(RANDOM.nextInt()))
                      .type("online")
                      .creditCard("138497813")
                      .accountId("32")
                      .amount(32)
                      .build()
      ).build();
    };
  }

  @Bean("offline-producer")
  public Supplier<Message<PaymentDto>> offlineProducer() {
    return () -> {
      return MessageBuilder.withPayload(
              PaymentDto.builder()
                      .paymentId(String.valueOf(RANDOM.nextInt()))
                      .type("offline")
                      .creditCard("1987349")
                      .accountId("1")
                      .amount(43)
                      .build()
      ).build();
    };
  }
}
