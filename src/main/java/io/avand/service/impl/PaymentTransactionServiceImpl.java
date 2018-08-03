package io.avand.service.impl;

import io.avand.domain.entity.jpa.InvoiceEntity;
import io.avand.domain.entity.jpa.PaymentTransactionEntity;
import io.avand.repository.jpa.InvoiceRepository;
import io.avand.repository.jpa.PaymentTransactionRepository;
import io.avand.service.PaymentTransactionService;
import io.avand.service.dto.PaymentTransactionDTO;
import io.avand.service.mapper.PaymentTransactionMapper;
import io.avand.web.rest.errors.ServerErrorException;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentTransactionServiceImpl implements PaymentTransactionService {

    private final Logger logger = LoggerFactory.getLogger(PaymentTransactionServiceImpl.class);

    private final PaymentTransactionRepository paymentTransactionRepository;

    private final PaymentTransactionMapper paymentTransactionMapper;

    private final InvoiceRepository invoiceRepository;

    public PaymentTransactionServiceImpl(PaymentTransactionRepository paymentTransactionRepository,
                                         PaymentTransactionMapper paymentTransactionMapper,
                                         InvoiceRepository invoiceRepository) {
        this.paymentTransactionRepository = paymentTransactionRepository;
        this.paymentTransactionMapper = paymentTransactionMapper;
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    public PaymentTransactionDTO save(PaymentTransactionDTO paymentTransactionDTO) throws NotFoundException {
        logger.debug("Request to save payment transaction : {}", paymentTransactionDTO);
        PaymentTransactionEntity paymentTransactionEntity = paymentTransactionMapper.toEntity(paymentTransactionDTO);

        Optional<InvoiceEntity> invoiceEntity = invoiceRepository.findById(paymentTransactionDTO.getInvoiceId());
        if (invoiceEntity.isPresent()) {
            paymentTransactionEntity.setInvoice(invoiceEntity.get());
        } else {
            throw new NotFoundException("Invoice is not avaiable!");
        }

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

    @Override
    public PaymentTransactionDTO findOneByTrackingCode(String trackingCode) throws NotFoundException {
        logger.debug("Request for payment transaction service to find one by tracking code : {}", trackingCode);

        Optional<PaymentTransactionEntity> paymentTransactionEntity = paymentTransactionRepository.findByTrackingCode(trackingCode);
        if (paymentTransactionEntity.isPresent()) {
            return paymentTransactionEntity.map(paymentTransactionMapper::toDto).get();
        } else {
            throw new NotFoundException("Transaction not found!");
        }
    }
}
