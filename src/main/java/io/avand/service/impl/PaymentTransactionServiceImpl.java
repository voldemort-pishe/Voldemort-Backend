package io.avand.service.impl;

import io.avand.domain.entity.jpa.PaymentTransactionEntity;
import io.avand.repository.jpa.PaymentTransactionRepository;
import io.avand.service.PaymentTransactionService;
import io.avand.service.dto.PaymentTransactionDTO;
import io.avand.service.mapper.PaymentTransactionMapper;
import io.avand.web.rest.errors.ServerErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentTransactionServiceImpl implements PaymentTransactionService {

    private final Logger logger = LoggerFactory.getLogger(PaymentTransactionServiceImpl.class);

    private final PaymentTransactionRepository paymentTransactionRepository;

    private final PaymentTransactionMapper paymentTransactionMapper;

    public PaymentTransactionServiceImpl(PaymentTransactionRepository paymentTransactionRepository,
                                         PaymentTransactionMapper paymentTransactionMapper) {
        this.paymentTransactionRepository = paymentTransactionRepository;
        this.paymentTransactionMapper = paymentTransactionMapper;
    }

    @Override
    public PaymentTransactionDTO save(PaymentTransactionDTO paymentTransactionDTO) {
        logger.debug("Request to save payment transaction : {}", paymentTransactionDTO);
        PaymentTransactionEntity paymentTransactionEntity = paymentTransactionMapper.toEntity(paymentTransactionDTO);
        paymentTransactionEntity = paymentTransactionRepository.save(paymentTransactionEntity);

        return paymentTransactionMapper.toDto(paymentTransactionEntity);
    }

    @Override
    public PaymentTransactionDTO update(PaymentTransactionDTO paymentTransactionDTO) {
        Optional<PaymentTransactionEntity> paymentTransactionEntity = paymentTransactionRepository.findById(paymentTransactionDTO.getId());

        if (!paymentTransactionEntity.isPresent()) {
            throw new ServerErrorException("فاکتور مورد نظرتان یافت نشد");
        } else {
            PaymentTransactionDTO paymentTransaction = paymentTransactionMapper.toDto(paymentTransactionEntity.get());
            paymentTransaction.setReferenceId(paymentTransactionDTO.getReferenceId());
            paymentTransaction.setStatus(paymentTransactionDTO.getStatus());
            return paymentTransactionMapper.toDto(
                paymentTransactionRepository.save(
                    paymentTransactionMapper.toEntity(
                        paymentTransaction
                    )
                )
            );
        }
    }
}
