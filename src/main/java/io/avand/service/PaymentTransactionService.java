package io.avand.service;

import io.avand.service.dto.PaymentTransactionDTO;
import javassist.NotFoundException;

public interface PaymentTransactionService {

    PaymentTransactionDTO save(PaymentTransactionDTO paymentTransactionDTO) throws NotFoundException;

    PaymentTransactionDTO update(PaymentTransactionDTO paymentTransactionDTO);

    PaymentTransactionDTO findOneByTrackingCode(String
                                                    trackingCode) throws NotFoundException;
}
