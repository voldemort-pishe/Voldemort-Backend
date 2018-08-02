package io.avand.service.impl;

import io.avand.domain.entity.jpa.InvoiceEntity;
import io.avand.domain.enumeration.InvoiceStatus;
import io.avand.repository.jpa.InvoiceRepository;
import io.avand.service.InvoiceService;
import io.avand.service.dto.InvoiceDTO;
import io.avand.service.mapper.InvoiceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Override
    public void delete(Long invoiceId) {
        logger.debug("Request for service to delete a invoice : {}", invoiceId);

        invoiceRepository.delete(invoiceId);
    }

    @Override
    public InvoiceDTO findOne(Long invoiceId) {
        logger.debug("Request to find a invoice by id : {}", invoiceId);

        InvoiceEntity invoiceEntity = invoiceRepository.findOne(invoiceId);
        return invoiceMapper.toDto(invoiceEntity);
    }

    @Override
    public Page<InvoiceDTO> getAll(Pageable pageable) {
        logger.debug("Request for service to get invoices");
        Page<InvoiceEntity> invoiceEntities = invoiceRepository.findAll(pageable);
        return invoiceEntities.map(invoiceMapper::toDto);
    }
}
