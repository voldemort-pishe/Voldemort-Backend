package hr.pishe.web.rest.component.impl;

import hr.pishe.service.CompanyService;
import hr.pishe.service.InvoiceService;
import hr.pishe.service.dto.CompanyDTO;
import hr.pishe.service.dto.InvoiceDTO;
import hr.pishe.service.mapper.CompanyMapper;
import hr.pishe.web.rest.component.InvoiceComponent;
import hr.pishe.web.rest.util.PageMaker;
import hr.pishe.web.rest.vm.response.ResponseVM;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class InvoiceComponentImpl implements InvoiceComponent {

    private final Logger log = LoggerFactory.getLogger(InvoiceComponentImpl.class);
    private final InvoiceService invoiceService;
    private final CompanyService companyService;
    private final CompanyMapper companyMapper;

    public InvoiceComponentImpl(InvoiceService invoiceService,
                                CompanyService companyService,
                                CompanyMapper companyMapper) {
        this.invoiceService = invoiceService;
        this.companyService = companyService;
        this.companyMapper = companyMapper;
    }

    @Override
    public ResponseVM<InvoiceDTO> findById(Long invoiceId) throws NotFoundException {
        log.debug("Request to find invoice by id : {}", invoiceId);
        Optional<InvoiceDTO> invoiceDTOOptional = invoiceService.findOneById(invoiceId);
        if (invoiceDTOOptional.isPresent()) {
            ResponseVM<InvoiceDTO> responseVM = new ResponseVM<>();
            responseVM.setData(invoiceDTOOptional.get());
            responseVM.setInclude(this.createIncluded(invoiceDTOOptional.get()));
            return responseVM;
        } else {
            throw new NotFoundException("Invoice Not Found");
        }
    }

    @Override
    public Page<ResponseVM<InvoiceDTO>> findAll(Pageable pageable) throws NotFoundException {
        log.debug("Request to findAll invoices");
        Page<InvoiceDTO> invoiceDTOS = invoiceService.findAll(pageable);
        List<ResponseVM<InvoiceDTO>> responseVMS = new ArrayList<>();
        for (InvoiceDTO invoiceDTO : invoiceDTOS) {
            ResponseVM<InvoiceDTO> responseVM = new ResponseVM<>();
            responseVM.setData(invoiceDTO);
            responseVM.setInclude(this.createIncluded(invoiceDTO));
            responseVMS.add(responseVM);
        }
        return new PageMaker<>(responseVMS, invoiceDTOS);
    }

    private Map<String, Object> createIncluded(InvoiceDTO invoiceDTO) throws NotFoundException {
        Map<String, Object> included = new HashMap<>();

        CompanyDTO companyDTO = companyService.findById(invoiceDTO.getCompanyId());
        included.put("company", companyMapper.dtoToVm(companyDTO));

        return included;
    }
}
