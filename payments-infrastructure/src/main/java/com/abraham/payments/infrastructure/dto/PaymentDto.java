package com.abraham.payments.infrastructure.dto;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "payments")
public class PaymentDto {

  @Id
  @Column(name = "payment_id", unique = true)
  private String paymentId;

  @Column(name = "account_id")
  private Integer accountId;

  @Column(name = "payment_type")
  private String paymentType;

  @Column(name = "credit_card")
  private String creditCard;

  @Column
  private Integer amount;
}
