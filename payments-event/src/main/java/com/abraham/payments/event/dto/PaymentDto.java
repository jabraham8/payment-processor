package com.abraham.payments.event.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentDto {

  private String paymentId;
  private String accountId;
  private String type;
  private String creditCard;
  private Integer amount;
}
