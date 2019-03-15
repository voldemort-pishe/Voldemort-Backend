package hr.pishe.service;

import hr.pishe.service.dto.InvoiceDTO;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface InvoiceService {

    InvoiceDTO save(InvoiceDTO invoiceDTO) throws NotFoundException;

    InvoiceDTO saveByPlanId(Long planId,Long userId) throws NotFoundException;

    Optional<InvoiceDTO> findOneById(Long id) throws NotFoundException;

    Optional<InvoiceDTO> findOneByTrackingCode(String trackingCode) throws NotFoundException;

    Page<InvoiceDTO> findAll(Pageable pageable) throws NotFoundException;

    void delete(Long id);
}
