package io.avand.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.avand.domain.InvoiceEntity;

import io.avand.repository.InvoiceRepository;
import io.avand.web.rest.errors.BadRequestAlertException;
import io.avand.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing InvoiceEntity.
 */
@RestController
@RequestMapping("/api")
public class InvoiceResource {

    private final Logger log = LoggerFactory.getLogger(InvoiceResource.class);

    private static final String ENTITY_NAME = "invoiceEntity";

    private final InvoiceRepository invoiceRepository;

    public InvoiceResource(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    /**
     * POST  /invoice-entities : Create a new invoiceEntity.
     *
     * @param invoiceEntity the invoiceEntity to create
     * @return the ResponseEntity with status 201 (Created) and with body the new invoiceEntity, or with status 400 (Bad Request) if the invoiceEntity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/invoice-entities")
    @Timed
    public ResponseEntity<InvoiceEntity> createInvoiceEntity(@RequestBody InvoiceEntity invoiceEntity) throws URISyntaxException {
        log.debug("REST request to save InvoiceEntity : {}", invoiceEntity);
        if (invoiceEntity.getId() != null) {
            throw new BadRequestAlertException("A new invoiceEntity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InvoiceEntity result = invoiceRepository.save(invoiceEntity);
        return ResponseEntity.created(new URI("/api/invoice-entities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /invoice-entities : Updates an existing invoiceEntity.
     *
     * @param invoiceEntity the invoiceEntity to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated invoiceEntity,
     * or with status 400 (Bad Request) if the invoiceEntity is not valid,
     * or with status 500 (Internal Server Error) if the invoiceEntity couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/invoice-entities")
    @Timed
    public ResponseEntity<InvoiceEntity> updateInvoiceEntity(@RequestBody InvoiceEntity invoiceEntity) throws URISyntaxException {
        log.debug("REST request to update InvoiceEntity : {}", invoiceEntity);
        if (invoiceEntity.getId() == null) {
            return createInvoiceEntity(invoiceEntity);
        }
        InvoiceEntity result = invoiceRepository.save(invoiceEntity);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, invoiceEntity.getId().toString()))
            .body(result);
    }

    /**
     * GET  /invoice-entities : get all the invoiceEntities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of invoiceEntities in body
     */
    @GetMapping("/invoice-entities")
    @Timed
    public List<InvoiceEntity> getAllInvoiceEntities() {
        log.debug("REST request to get all InvoiceEntities");
        return invoiceRepository.findAll();
        }

    /**
     * GET  /invoice-entities/:id : get the "id" invoiceEntity.
     *
     * @param id the id of the invoiceEntity to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the invoiceEntity, or with status 404 (Not Found)
     */
    @GetMapping("/invoice-entities/{id}")
    @Timed
    public ResponseEntity<InvoiceEntity> getInvoiceEntity(@PathVariable Long id) {
        log.debug("REST request to get InvoiceEntity : {}", id);
        InvoiceEntity invoiceEntity = invoiceRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(invoiceEntity));
    }

    /**
     * DELETE  /invoice-entities/:id : delete the "id" invoiceEntity.
     *
     * @param id the id of the invoiceEntity to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/invoice-entities/{id}")
    @Timed
    public ResponseEntity<Void> deleteInvoiceEntity(@PathVariable Long id) {
        log.debug("REST request to delete InvoiceEntity : {}", id);
        invoiceRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
