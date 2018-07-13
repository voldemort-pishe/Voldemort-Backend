package io.avand.service.impl;

import io.avand.domain.InvoiceEntity;
import io.avand.domain.enumeration.InvoiceStatus;
import io.avand.repository.jpa.InvoiceRepository;
import io.avand.service.InvoiceService;
import io.avand.service.dto.InvoiceDTO;
import io.avand.service.mapper.InvoiceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private Logger logger = LoggerFactory.getLogger(InvoiceServiceImpl.class);

    private final InvoiceRepository invoiceRepository;

    private final InvoiceMapper invoiceMapper;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository,
                              InvoiceMapper invoiceMapper) {
        this.invoiceRepository = invoiceRepository;
        this.invoiceMapper = invoiceMapper;
    }

    @Override
    public InvoiceDTO save(InvoiceDTO invoiceDTO) {
        logger.debug("Request for service to save an invoice : {}", invoiceDTO);
        invoiceDTO.setStatus(InvoiceStatus.INITIALIZED);

        InvoiceEntity invoiceEntity = invoiceRepository.save(invoiceMapper.toEntity(invoiceDTO));
        return invoiceMapper.toDto(invoiceEntity);
    }
}
