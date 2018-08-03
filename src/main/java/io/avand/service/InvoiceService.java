package io.avand.service;

import io.avand.service.dto.InvoiceDTO;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface InvoiceService {

    InvoiceDTO save(InvoiceDTO invoiceDTO);

    InvoiceDTO update(InvoiceDTO invoiceDTO);

    void delete(Long id);

    Optional<InvoiceDTO> findOneById(Long id);

    Optional<InvoiceDTO> findOneByUserId(Long userId) throws NotFoundException;

    Optional<InvoiceDTO> findOneByTrackingCode(String trackingCode) throws NotFoundException;

    Page<InvoiceDTO> getAll(Pageable pageable);
}
