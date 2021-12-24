package com.abraham.payments.infrastructure.service.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentValidationDto {

  @JsonProperty("payment_id")
  private String paymentId;

  @JsonProperty("account_id")
  private Integer accountId;

  @JsonProperty("payment_type")
  private String paymentType;

  @JsonProperty("credit_card")
  private String creditCard;

  @JsonProperty
  private Integer amount;
}
