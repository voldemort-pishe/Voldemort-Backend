package io.avand.web.rest;

import com.codahale.metrics.annotation.Timed;

import io.avand.service.InvoiceService;
import io.avand.service.dto.InvoiceDTO;
import io.avand.web.rest.component.InvoiceComponent;
import io.avand.web.rest.errors.ServerErrorException;
import io.avand.web.rest.util.HeaderUtil;
import io.avand.web.rest.vm.response.ResponseVM;
import io.swagger.annotations.ApiParam;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * REST controller for managing InvoiceDTO.
 */
@RestController
@RequestMapping("/api")
public class InvoiceResource {

    private final Logger log = LoggerFactory.getLogger(InvoiceResource.class);

    private static final String ENTITY_NAME = "invoice";

    private final InvoiceService invoiceService;

    private final InvoiceComponent invoiceComponent;

    public InvoiceResource(InvoiceService invoiceService,
                           InvoiceComponent invoiceComponent) {
        this.invoiceService = invoiceService;
        this.invoiceComponent = invoiceComponent;
    }


    /**
     * GET  /invoice : get all the invoiceDTOs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of invoiceDTOs in body
     */
    @GetMapping("/invoice")
    @Timed
    @PreAuthorize("isMember('VIEW_INVOICE')")
    public ResponseEntity<Page<ResponseVM<InvoiceDTO>>> getAllInvoice(@ApiParam Pageable pageable) {
        log.debug("REST request to get all InvoiceDTOs");
        try {
            Page<ResponseVM<InvoiceDTO>> invoiceDTOS = invoiceComponent.findAll(pageable);
            return new ResponseEntity<>(invoiceDTOS, HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

    /**
     * GET  /invoice/:id : get the "id" invoiceDTO.
     *
     * @param id the id of the invoiceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the invoiceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/invoice/{id}")
    @Timed
    @PreAuthorize("isMember(#id,'INVOICE','VIEW_INVOICE')")
    public ResponseEntity<ResponseVM<InvoiceDTO>> getInvoice(@PathVariable Long id) {
        log.debug("REST request to get InvoiceDTO : {}", id);
        try {
            ResponseVM<InvoiceDTO> invoiceDTO = invoiceComponent.findById(id);
            return new ResponseEntity<>(invoiceDTO, HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

    /**
     * DELETE  /invoice/:id : delete the "id" invoiceDTO.
     *
     * @param id the id of the invoiceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/invoice/{id}")
    @Timed
    @PreAuthorize("isMember(#id,'INVOICE','DELETE_INVOICE')")
    public ResponseEntity<Void> deleteInvoice(@PathVariable Long id) {
        log.debug("REST request to delete InvoiceDTO : {}", id);
        invoiceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
