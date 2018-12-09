package io.avand.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.avand.security.SecurityUtils;
import io.avand.service.CompanyPlanService;
import io.avand.service.InvoiceService;
import io.avand.service.dto.InvoiceDTO;
import io.avand.web.rest.errors.ServerErrorException;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/api/company-plan")
public class CompanyPlanResource {

    private final Logger logger = LoggerFactory.getLogger(CompanyPlanResource.class);

    private final CompanyPlanService companyPlanService;

    private final InvoiceService invoiceService;

    private final SecurityUtils securityUtils;

    public CompanyPlanResource(CompanyPlanService companyPlanService,
                               InvoiceService invoiceService,
                               SecurityUtils securityUtils) {
        this.companyPlanService = companyPlanService;
        this.invoiceService = invoiceService;
        this.securityUtils = securityUtils;
    }

    @PostMapping("{planId}")
    @Timed
    public ResponseEntity<InvoiceDTO> save(@PathVariable Long planId, HttpServletResponse response) throws IOException {
        logger.debug("REST request to save a plan for a user : {}", planId);
        try {
            InvoiceDTO invoiceDTO = invoiceService.saveByPlanId(planId, securityUtils.getCurrentCompanyId());
            companyPlanService.save(planId, invoiceDTO.getId(), securityUtils.getCurrentCompanyId());
            return new ResponseEntity<>(invoiceDTO, HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

    @GetMapping("{planId}")
    @Timed
    public ResponseEntity<InvoiceDTO> get(@PathVariable Long planId, HttpServletResponse response) throws IOException {
        logger.debug("REST request to get a plan for a user : {}", planId);
        try {
            Optional<InvoiceDTO> oneById = invoiceService.findOneById(planId);
            return new ResponseEntity<>(oneById.orElse(null), HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

}
