package com.abraham.payments.infrastructure.persistence.dao;

import com.abraham.payments.infrastructure.persistence.dao.dto.AccountDto;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public interface AccountDAO extends CrudRepository<AccountDto, Integer> {

  @Modifying
  @Query("UPDATE AccountDto a SET a.lastPaymentDate = :paymentDate WHERE a.accountId = :accountId")
  int updatePaymentDate(@Param("paymentDate") Instant paymentDate, @Param("accountId") Integer accountId);
}
