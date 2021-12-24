package com.abraham.payments.infrastructure.dao;

import com.abraham.payments.infrastructure.dto.PaymentDto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface PaymentDAO extends CrudRepository<PaymentDto, String> {
}
