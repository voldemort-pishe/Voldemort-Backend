package io.avand.web.rest;

import com.codahale.metrics.annotation.Timed;

import io.avand.service.InvoiceService;
import io.avand.service.dto.InvoiceDTO;
import io.avand.web.rest.errors.BadRequestAlertException;
import io.avand.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
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

    public InvoiceResource(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }


    /**
     * GET  /invoice : get all the invoiceDTOs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of invoiceDTOs in body
     */
    @GetMapping("/invoice")
    @Timed
    public ResponseEntity getAllInvoice(@ApiParam Pageable pageable) {
        log.debug("REST request to get all InvoiceDTOs");
        Page<InvoiceDTO> invoiceDTOS = invoiceService.findAll(pageable);
        return new ResponseEntity<>(invoiceDTOS, HttpStatus.OK);
        }

    /**
     * GET  /invoice/:id : get the "id" invoiceDTO.
     *
     * @param id the id of the invoiceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the invoiceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/invoice/{id}")
    @Timed
    public ResponseEntity getInvoice(@PathVariable Long id) {
        log.debug("REST request to get InvoiceDTO : {}", id);
        Optional<InvoiceDTO> invoiceDTO = invoiceService.findOneById(id);
        return new ResponseEntity<>(invoiceDTO.get(), HttpStatus.OK);
    }

    /**
     * DELETE  /invoice/:id : delete the "id" invoiceDTO.
     *
     * @param id the id of the invoiceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/invoice/{id}")
    @Timed
    public ResponseEntity<Void> deleteInvoice(@PathVariable Long id) {
        log.debug("REST request to delete InvoiceDTO : {}", id);
        invoiceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
