package io.avand.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.avand.domain.CompanyPipelineEntity;

import io.avand.repository.CompanyPipelineRepository;
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
 * REST controller for managing CompanyPipelineEntity.
 */
@RestController
@RequestMapping("/api")
public class CompanyPipelineResource {

    private final Logger log = LoggerFactory.getLogger(CompanyPipelineResource.class);

    private static final String ENTITY_NAME = "companyPipelineEntity";

    private final CompanyPipelineRepository companyPipelineRepository;

    public CompanyPipelineResource(CompanyPipelineRepository companyPipelineRepository) {
        this.companyPipelineRepository = companyPipelineRepository;
    }

    /**
     * POST  /company-pipeline-entities : Create a new companyPipelineEntity.
     *
     * @param companyPipelineEntity the companyPipelineEntity to create
     * @return the ResponseEntity with status 201 (Created) and with body the new companyPipelineEntity, or with status 400 (Bad Request) if the companyPipelineEntity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/company-pipeline-entities")
    @Timed
    public ResponseEntity<CompanyPipelineEntity> createCompanyPipelineEntity(@RequestBody CompanyPipelineEntity companyPipelineEntity) throws URISyntaxException {
        log.debug("REST request to save CompanyPipelineEntity : {}", companyPipelineEntity);
        if (companyPipelineEntity.getId() != null) {
            throw new BadRequestAlertException("A new companyPipelineEntity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompanyPipelineEntity result = companyPipelineRepository.save(companyPipelineEntity);
        return ResponseEntity.created(new URI("/api/company-pipeline-entities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /company-pipeline-entities : Updates an existing companyPipelineEntity.
     *
     * @param companyPipelineEntity the companyPipelineEntity to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated companyPipelineEntity,
     * or with status 400 (Bad Request) if the companyPipelineEntity is not valid,
     * or with status 500 (Internal Server Error) if the companyPipelineEntity couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/company-pipeline-entities")
    @Timed
    public ResponseEntity<CompanyPipelineEntity> updateCompanyPipelineEntity(@RequestBody CompanyPipelineEntity companyPipelineEntity) throws URISyntaxException {
        log.debug("REST request to update CompanyPipelineEntity : {}", companyPipelineEntity);
        if (companyPipelineEntity.getId() == null) {
            return createCompanyPipelineEntity(companyPipelineEntity);
        }
        CompanyPipelineEntity result = companyPipelineRepository.save(companyPipelineEntity);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, companyPipelineEntity.getId().toString()))
            .body(result);
    }

    /**
     * GET  /company-pipeline-entities : get all the companyPipelineEntities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of companyPipelineEntities in body
     */
    @GetMapping("/company-pipeline-entities")
    @Timed
    public List<CompanyPipelineEntity> getAllCompanyPipelineEntities() {
        log.debug("REST request to get all CompanyPipelineEntities");
        return companyPipelineRepository.findAll();
        }

    /**
     * GET  /company-pipeline-entities/:id : get the "id" companyPipelineEntity.
     *
     * @param id the id of the companyPipelineEntity to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the companyPipelineEntity, or with status 404 (Not Found)
     */
    @GetMapping("/company-pipeline-entities/{id}")
    @Timed
    public ResponseEntity<CompanyPipelineEntity> getCompanyPipelineEntity(@PathVariable Long id) {
        log.debug("REST request to get CompanyPipelineEntity : {}", id);
        CompanyPipelineEntity companyPipelineEntity = companyPipelineRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(companyPipelineEntity));
    }

    /**
     * DELETE  /company-pipeline-entities/:id : delete the "id" companyPipelineEntity.
     *
     * @param id the id of the companyPipelineEntity to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/company-pipeline-entities/{id}")
    @Timed
    public ResponseEntity<Void> deleteCompanyPipelineEntity(@PathVariable Long id) {
        log.debug("REST request to delete CompanyPipelineEntity : {}", id);
        companyPipelineRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
