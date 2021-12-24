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
                      .paymentId(String.valueOf(RANDOM.nextInt(Integer.MAX_VALUE)))
                      .type("online")
                      .creditCard("138497813")
                      .accountId(this.getValidAccountId())
                      .amount(RANDOM.nextInt(10000))
                      .build()
      ).build();
    };
  }

  @Bean("offline-producer")
  public Supplier<Message<PaymentDto>> offlineProducer() {
    return () -> {
      return MessageBuilder.withPayload(
              PaymentDto.builder()
                      .paymentId(String.valueOf(RANDOM.nextInt(Integer.MAX_VALUE)))
                      .type("offline")
                      .creditCard("1987349")
                      .accountId(this.getValidAccountId())
                      .amount(RANDOM.nextInt(10000))
                      .build()
      ).build();
    };
  }

  private String getValidAccountId() {
    return String.valueOf(RANDOM.ints(1, 1, 101).findFirst().getAsInt());
  }
}
