package io.avand.service.impl;

import io.avand.service.PaymentService;
import io.avand.service.ZarinpalService;
import io.avand.service.dto.ZarinpalRequestDTO;
import io.avand.service.dto.ZarinpalVerifyRequestDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final Logger logger = LoggerFactory.getLogger(PaymentServiceImpl.class);

    @Autowired
    private final ZarinpalService zarinpalService;

    public PaymentServiceImpl(ZarinpalService zarinpalService) {
        this.zarinpalService = zarinpalService;
    }

    @Override
    public ResponseEntity paymentRequest(ZarinpalRequestDTO zarinpalRequestDTO) {
        logger.debug("Request to payment service to payment request : {}", zarinpalRequestDTO);
        return zarinpalService.paymentRequest(zarinpalRequestDTO);
    }

    @Override
    public ResponseEntity paymentVerify(ZarinpalVerifyRequestDTO zarinpalVerifyRequestDTO) {
        logger.debug("Request to payment service to verfy paymenr : {}", zarinpalVerifyRequestDTO);
        return zarinpalService.paymentVerify(zarinpalVerifyRequestDTO);
    }
}
