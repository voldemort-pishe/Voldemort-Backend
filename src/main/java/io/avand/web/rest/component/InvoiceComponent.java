package io.avand.web.rest.component;

import io.avand.service.dto.InvoiceDTO;
import io.avand.web.rest.vm.response.ResponseVM;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InvoiceComponent {
    ResponseVM<InvoiceDTO> findById(Long invoiceId) throws NotFoundException;

    Page<ResponseVM<InvoiceDTO>> findAll(Pageable pageable) throws NotFoundException;

}
