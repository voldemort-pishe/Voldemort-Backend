package io.avand.service;

import io.avand.service.dto.InvoiceDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface InvoiceService {

    InvoiceDTO save(InvoiceDTO invoiceDTO);

    void delete(Long invoiceId);

    InvoiceDTO findOne(Long invoiceId);

    Page<InvoiceDTO> getAll(Pageable pageable);
}
