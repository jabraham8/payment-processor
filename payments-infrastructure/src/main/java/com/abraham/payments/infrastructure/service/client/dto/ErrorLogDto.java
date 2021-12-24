package com.abraham.payments.infrastructure.service.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorLogDto {

  @JsonProperty("payment_id")
  private String paymentId;

  @JsonProperty("error_type")
  private String type;

  @JsonProperty("error_description")
  private String description;
}
