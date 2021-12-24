package com.abraham.payments.infrastructure.service;

import com.abraham.payments.infrastructure.service.client.ErrorLoggingRestClient;
import com.abraham.payments.infrastructure.service.client.dto.ErrorLogDto;
import com.abraham.payments.usecases.utils.ErrorLoggingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ErrorLoggingServiceImplTest {

  @Mock
  private ErrorLoggingRestClient restClient;

  @InjectMocks
  private ErrorLoggingServiceImpl errorLoggingService;

  @Test
  public void errors_are_logged() {

    // When
    this.errorLoggingService.logError("1234", ErrorLoggingService.ErrorType.NETWORK,
            "Impossible to validate payment");

    // Then
    final ArgumentCaptor<ErrorLogDto> errorCaptor = ArgumentCaptor.forClass(ErrorLogDto.class);
    verify(this.restClient).log(errorCaptor.capture());
    assertAll(() -> {
      assertEquals("1234", errorCaptor.getValue().getPaymentId());
      assertEquals(ErrorLoggingService.ErrorType.NETWORK.getValue(), errorCaptor.getValue().getType());
      assertEquals("Impossible to validate payment", errorCaptor.getValue().getDescription());
    });
  }
}
