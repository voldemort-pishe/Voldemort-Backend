package io.avand.service;

import io.avand.service.dto.PaymentTransactionDTO;

public interface PaymentTransactionService {

    PaymentTransactionDTO save(PaymentTransactionDTO paymentTransactionDTO);

    PaymentTransactionDTO update(PaymentTransactionDTO paymentTransactionDTO);
}
