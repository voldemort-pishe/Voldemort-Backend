package io.avand.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.avand.domain.PaymentTransactionEntity;

import io.avand.repository.PaymentTransactionEntityRepository;
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
 * REST controller for managing PaymentTransactionEntity.
 */
@RestController
@RequestMapping("/api")
public class PaymentTransactionEntityResource {

    private final Logger log = LoggerFactory.getLogger(PaymentTransactionEntityResource.class);

    private static final String ENTITY_NAME = "paymentTransactionEntity";

    private final PaymentTransactionEntityRepository paymentTransactionEntityRepository;

    public PaymentTransactionEntityResource(PaymentTransactionEntityRepository paymentTransactionEntityRepository) {
        this.paymentTransactionEntityRepository = paymentTransactionEntityRepository;
    }

    /**
     * POST  /payment-transaction-entities : Create a new paymentTransactionEntity.
     *
     * @param paymentTransactionEntity the paymentTransactionEntity to create
     * @return the ResponseEntity with status 201 (Created) and with body the new paymentTransactionEntity, or with status 400 (Bad Request) if the paymentTransactionEntity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/payment-transaction-entities")
    @Timed
    public ResponseEntity<PaymentTransactionEntity> createPaymentTransactionEntity(@RequestBody PaymentTransactionEntity paymentTransactionEntity) throws URISyntaxException {
        log.debug("REST request to save PaymentTransactionEntity : {}", paymentTransactionEntity);
        if (paymentTransactionEntity.getId() != null) {
            throw new BadRequestAlertException("A new paymentTransactionEntity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PaymentTransactionEntity result = paymentTransactionEntityRepository.save(paymentTransactionEntity);
        return ResponseEntity.created(new URI("/api/payment-transaction-entities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /payment-transaction-entities : Updates an existing paymentTransactionEntity.
     *
     * @param paymentTransactionEntity the paymentTransactionEntity to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated paymentTransactionEntity,
     * or with status 400 (Bad Request) if the paymentTransactionEntity is not valid,
     * or with status 500 (Internal Server Error) if the paymentTransactionEntity couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/payment-transaction-entities")
    @Timed
    public ResponseEntity<PaymentTransactionEntity> updatePaymentTransactionEntity(@RequestBody PaymentTransactionEntity paymentTransactionEntity) throws URISyntaxException {
        log.debug("REST request to update PaymentTransactionEntity : {}", paymentTransactionEntity);
        if (paymentTransactionEntity.getId() == null) {
            return createPaymentTransactionEntity(paymentTransactionEntity);
        }
        PaymentTransactionEntity result = paymentTransactionEntityRepository.save(paymentTransactionEntity);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, paymentTransactionEntity.getId().toString()))
            .body(result);
    }

    /**
     * GET  /payment-transaction-entities : get all the paymentTransactionEntities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of paymentTransactionEntities in body
     */
    @GetMapping("/payment-transaction-entities")
    @Timed
    public List<PaymentTransactionEntity> getAllPaymentTransactionEntities() {
        log.debug("REST request to get all PaymentTransactionEntities");
        return paymentTransactionEntityRepository.findAll();
        }

    /**
     * GET  /payment-transaction-entities/:id : get the "id" paymentTransactionEntity.
     *
     * @param id the id of the paymentTransactionEntity to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the paymentTransactionEntity, or with status 404 (Not Found)
     */
    @GetMapping("/payment-transaction-entities/{id}")
    @Timed
    public ResponseEntity<PaymentTransactionEntity> getPaymentTransactionEntity(@PathVariable Long id) {
        log.debug("REST request to get PaymentTransactionEntity : {}", id);
        PaymentTransactionEntity paymentTransactionEntity = paymentTransactionEntityRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(paymentTransactionEntity));
    }

    /**
     * DELETE  /payment-transaction-entities/:id : delete the "id" paymentTransactionEntity.
     *
     * @param id the id of the paymentTransactionEntity to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/payment-transaction-entities/{id}")
    @Timed
    public ResponseEntity<Void> deletePaymentTransactionEntity(@PathVariable Long id) {
        log.debug("REST request to delete PaymentTransactionEntity : {}", id);
        paymentTransactionEntityRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
