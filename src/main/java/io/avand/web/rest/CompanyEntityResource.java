package io.avand.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.avand.domain.CompanyEntity;

import io.avand.repository.CompanyEntityRepository;
import io.avand.web.rest.errors.BadRequestAlertException;
import io.avand.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing CompanyEntity.
 */
@RestController
@RequestMapping("/api")
public class CompanyEntityResource {

    private final Logger log = LoggerFactory.getLogger(CompanyEntityResource.class);

    private static final String ENTITY_NAME = "companyEntity";

    private final CompanyEntityRepository companyEntityRepository;

    public CompanyEntityResource(CompanyEntityRepository companyEntityRepository) {
        this.companyEntityRepository = companyEntityRepository;
    }

    /**
     * POST  /company-entities : Create a new companyEntity.
     *
     * @param companyEntity the companyEntity to create
     * @return the ResponseEntity with status 201 (Created) and with body the new companyEntity, or with status 400 (Bad Request) if the companyEntity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/company-entities")
    @Timed
    public ResponseEntity<CompanyEntity> createCompanyEntity(@Valid @RequestBody CompanyEntity companyEntity) throws URISyntaxException {
        log.debug("REST request to save CompanyEntity : {}", companyEntity);
        if (companyEntity.getId() != null) {
            throw new BadRequestAlertException("A new companyEntity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompanyEntity result = companyEntityRepository.save(companyEntity);
        return ResponseEntity.created(new URI("/api/company-entities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /company-entities : Updates an existing companyEntity.
     *
     * @param companyEntity the companyEntity to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated companyEntity,
     * or with status 400 (Bad Request) if the companyEntity is not valid,
     * or with status 500 (Internal Server Error) if the companyEntity couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/company-entities")
    @Timed
    public ResponseEntity<CompanyEntity> updateCompanyEntity(@Valid @RequestBody CompanyEntity companyEntity) throws URISyntaxException {
        log.debug("REST request to update CompanyEntity : {}", companyEntity);
        if (companyEntity.getId() == null) {
            return createCompanyEntity(companyEntity);
        }
        CompanyEntity result = companyEntityRepository.save(companyEntity);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, companyEntity.getId().toString()))
            .body(result);
    }

    /**
     * GET  /company-entities : get all the companyEntities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of companyEntities in body
     */
    @GetMapping("/company-entities")
    @Timed
    public List<CompanyEntity> getAllCompanyEntities() {
        log.debug("REST request to get all CompanyEntities");
        return companyEntityRepository.findAll();
        }

    /**
     * GET  /company-entities/:id : get the "id" companyEntity.
     *
     * @param id the id of the companyEntity to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the companyEntity, or with status 404 (Not Found)
     */
    @GetMapping("/company-entities/{id}")
    @Timed
    public ResponseEntity<CompanyEntity> getCompanyEntity(@PathVariable Long id) {
        log.debug("REST request to get CompanyEntity : {}", id);
        CompanyEntity companyEntity = companyEntityRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(companyEntity));
    }

    /**
     * DELETE  /company-entities/:id : delete the "id" companyEntity.
     *
     * @param id the id of the companyEntity to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/company-entities/{id}")
    @Timed
    public ResponseEntity<Void> deleteCompanyEntity(@PathVariable Long id) {
        log.debug("REST request to delete CompanyEntity : {}", id);
        companyEntityRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
