package com.abraham.payments.infrastructure.persistence;

import com.abraham.payments.exception.PaymentStorageException;
import com.abraham.payments.infrastructure.persistence.dao.AccountDAO;
import com.abraham.payments.infrastructure.persistence.dao.PaymentDAO;
import com.abraham.payments.infrastructure.persistence.dao.dto.PaymentDto;
import com.abraham.payments.infrastructure.persistence.mapper.PaymentDtoMapper;
import com.abraham.payments.model.Payment;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.PersistenceException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaymentRepositoryImplTest {

  @Mock
  private PaymentDAO paymentDAO;

  @Mock
  private AccountDAO accountDAO;

  @Mock
  private PaymentDtoMapper paymentDtoMapper;

  @InjectMocks
  private PaymentRepositoryImpl paymentRepository;

  @Test
  public void payments_are_store() {

    final Payment payment = Payment.builder().build();
    final PaymentDto dto = PaymentDto.builder().accountId(35).build();

    // Given
    when(this.paymentDtoMapper.from(payment)).thenReturn(dto);

    // When
    this.paymentRepository.save(payment);

    // Then
    verify(this.paymentDtoMapper).from(payment);
    verify(this.paymentDAO).save(dto);
    verify(this.accountDAO).updatePaymentDate(any(), any());
  }

  @Test
  public void account_is_not_updated_if_payment_storage_fails() {

    final Payment payment = Payment.builder().build();
    final PaymentDto dto = PaymentDto.builder().accountId(35).build();

    // Given
    when(this.paymentDtoMapper.from(payment)).thenReturn(dto);
    doThrow(PersistenceException.class).when(this.paymentDAO).save(any());

    // When
    assertThrows(PaymentStorageException.class, () -> this.paymentRepository.save(payment));

    // Then
    verify(this.paymentDtoMapper).from(payment);
    verify(this.paymentDAO).save(dto);
    verify(this.accountDAO, never()).updatePaymentDate(any(), any());
  }
}
