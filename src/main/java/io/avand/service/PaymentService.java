package io.avand.service;

import io.avand.service.dto.ZarinpalRequestDTO;
import io.avand.service.dto.ZarinpalVerifyRequestDTO;
import org.springframework.http.ResponseEntity;

public interface PaymentService {

    ResponseEntity paymentRequest(ZarinpalRequestDTO zarinpalRequestDTO);

    ResponseEntity paymentVerify(ZarinpalVerifyRequestDTO zarinpalVerifyRequestDTO);
}
