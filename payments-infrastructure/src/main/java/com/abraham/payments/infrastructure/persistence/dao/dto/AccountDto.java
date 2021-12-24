package com.abraham.payments.infrastructure.persistence.dao.dto;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "accounts")
public class AccountDto {

  @Id
  @Column(name = "account_id")
  private Integer accountId;

  @Column(name = "last_payment_date")
  private Instant lastPaymentDate;
}