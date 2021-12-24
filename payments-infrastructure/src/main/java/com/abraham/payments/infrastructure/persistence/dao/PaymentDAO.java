package com.abraham.payments.infrastructure.persistence.dao;

import com.abraham.payments.infrastructure.persistence.dao.dto.PaymentDto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface PaymentDAO extends CrudRepository<PaymentDto, String> {
}
