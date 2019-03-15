package hr.pishe.web.rest.component;

import hr.pishe.service.dto.InvoiceDTO;
import hr.pishe.web.rest.vm.response.ResponseVM;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InvoiceComponent {
    ResponseVM<InvoiceDTO> findById(Long invoiceId) throws NotFoundException;

    Page<ResponseVM<InvoiceDTO>> findAll(Pageable pageable) throws NotFoundException;

}
