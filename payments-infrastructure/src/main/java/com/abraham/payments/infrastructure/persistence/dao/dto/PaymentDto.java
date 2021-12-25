package com.abraham.payments.infrastructure.persistence.dao.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
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
