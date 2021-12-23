package com.abraham.payments.model;

import com.abraham.payments.exception.InvalidPaymentException;
import com.abraham.payments.service.ThirdPartyValidationService;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class Payment {

  private String paymentId;
  private String accountId;
  private PaymentType type;
  private String creditCard;
  private Integer amount;

  public void validate(final ThirdPartyValidationService validationService) throws InvalidPaymentException {
    if (PaymentType.ONLINE.equals(this.type)) {
      validationService.validate(this);
    }
  }
}
