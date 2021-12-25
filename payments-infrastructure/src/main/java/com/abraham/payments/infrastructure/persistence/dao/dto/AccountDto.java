package com.abraham.payments.infrastructure.persistence.dao.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "accounts")
public class AccountDto {

  @Id
  @Column(name = "account_id")
  private Integer accountId;

  @Column(name = "last_payment_date")
  private Instant lastPaymentDate;
}
