package com.abraham.payments.infrastructure.service;

import com.abraham.payments.exception.InvalidPaymentException;
import com.abraham.payments.infrastructure.service.client.PaymentValidationRestClient;
import com.abraham.payments.infrastructure.service.client.dto.PaymentValidationDto;
import com.abraham.payments.infrastructure.service.mapper.PaymentValidationDtoMapper;
import com.abraham.payments.model.Payment;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ThirdPartyValidationServiceImplTest {

  @Mock
  private PaymentValidationDtoMapper mapper;

  @Mock
  private PaymentValidationRestClient restClient;

  @InjectMocks
  private ThirdPartyValidationServiceImpl validationService;

  @Test
  public void payment_is_validated() {

    final Payment payment = Payment.builder().build();
    final PaymentValidationDto validationDto = PaymentValidationDto.builder().build();

    // Given
    when(this.mapper.map(payment)).thenReturn(validationDto);
    when(this.restClient.validate(validationDto)).thenReturn(true);

    // When
    this.validationService.validate(payment);

    // Then
    verify(this.mapper).map(payment);
    verify(this.restClient).validate(validationDto);
  }

  @Test
  public void invalid_payment_throws_an_exception() {

    final Payment payment = Payment.builder().build();
    final PaymentValidationDto validationDto = PaymentValidationDto.builder().build();

    // Given
    when(this.mapper.map(payment)).thenReturn(validationDto);
    when(this.restClient.validate(validationDto)).thenReturn(false);

    // When
    assertThrows(InvalidPaymentException.class, () -> this.validationService.validate(payment));

    // Then
    verify(this.mapper).map(payment);
    verify(this.restClient).validate(validationDto);
  }
}
