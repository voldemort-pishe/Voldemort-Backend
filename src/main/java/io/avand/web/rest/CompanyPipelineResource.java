package io.avand.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.avand.domain.entity.jpa.CompanyPipelineEntity;

import io.avand.repository.jpa.CompanyPipelineRepository;
import io.avand.service.CompanyPipelineService;
import io.avand.service.dto.CompanyPipelineDTO;
import io.avand.service.mapper.CompanyPipelineMapper;
import io.avand.web.rest.errors.BadRequestAlertException;
import io.avand.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
import javassist.NotFoundException;
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
 * REST controller for managing CompanyPipelineEntity.
 */
@RestController
@RequestMapping("/api")
public class CompanyPipelineResource {

    private final Logger log = LoggerFactory.getLogger(CompanyPipelineResource.class);

    private static final String ENTITY_NAME = "companyPipelineEntity";

    private final CompanyPipelineRepository companyPipelineRepository;

    private final CompanyPipelineService pipelineService;

    private final CompanyPipelineMapper pipelineMapper;

    public CompanyPipelineResource(CompanyPipelineRepository companyPipelineRepository,
                                   CompanyPipelineService pipelineService,
                                   CompanyPipelineMapper pipelineMapper) {
        this.companyPipelineRepository = companyPipelineRepository;
        this.pipelineService = pipelineService;
        this.pipelineMapper = pipelineMapper;
    }

    /**
     * POST  /company-pipeline : Create a new companyPipelineEntity.
     *
     * @param companyPipelineDTO the companyPipelineEntity to create
     * @return the ResponseEntity with status 201 (Created) and with body the new companyPipelineEntity, or with status 400 (Bad Request) if the companyPipelineEntity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/company-pipeline")
    @Timed
    public ResponseEntity createCompanyPipeline(@RequestBody CompanyPipelineDTO companyPipelineDTO) throws URISyntaxException, NotFoundException {
        log.debug("REST request to save CompanyPipelineEntity : {}", companyPipelineDTO);
        if (companyPipelineDTO.getId() != null) {
            throw new BadRequestAlertException("A new companyPipelineEntity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompanyPipelineDTO result = pipelineService.save(companyPipelineDTO);
        return ResponseEntity.created(new URI("/api/company-pipeline/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /company-pipeline : Updates an existing companyPipelineEntity.
     *
     * @param companyPipelineDTO the companyPipelineEntity to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated companyPipelineEntity,
     * or with status 400 (Bad Request) if the companyPipelineEntity is not valid,
     * or with status 500 (Internal Server Error) if the companyPipelineEntity couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/company-pipeline")
    @Timed
    public ResponseEntity updateCompanyPipeline(@RequestBody CompanyPipelineDTO companyPipelineDTO) throws URISyntaxException, NotFoundException {
        log.debug("REST request to update CompanyPipelineEntity : {}", companyPipelineDTO);
        if (companyPipelineDTO.getId() == null) {
            return this.createCompanyPipeline(companyPipelineDTO);
        }

        CompanyPipelineDTO result = pipelineService.update(companyPipelineDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, companyPipelineDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /company-pipeline : get all the companyPipelineEntities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of companyPipelineEntities in body
     */
    @GetMapping("/company-pipeline")
    @Timed
    public List getAllCompanyPipeline() {
        log.debug("REST request to get all CompanyPipelineEntities");
        return pipelineMapper.toDto(companyPipelineRepository.findAll());
    }

    /**
     * GET  /company-pipeline : get all the companyPipelineEntities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of companyPipelineEntities in body
     */
    @GetMapping("/company-pipeline/company/{id}")
    @Timed
    public ResponseEntity getAllCompanyPipelineByCompany(@PathVariable("id") Long companyId, @ApiParam Pageable pageable) throws NotFoundException {
        log.debug("REST request to get all CompanyPipelineEntities");
        Page<CompanyPipelineDTO> companyPipelineDTOS = pipelineService.getAllByCompanyId(companyId, pageable);
        return new ResponseEntity<>(companyPipelineDTOS, HttpStatus.OK);
    }

    /**
     * GET  /company-pipeline/:id : get the "id" companyPipelineEntity.
     *
     * @param id the id of the companyPipelineEntity to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the companyPipelineEntity, or with status 404 (Not Found)
     */
    @GetMapping("/company-pipeline/{id}")
    @Timed
    public ResponseEntity getCompanyPipeline(@PathVariable Long id) throws NotFoundException {
        log.debug("REST request to get CompanyPipelineEntity : {}", id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(pipelineService.findOne(id)));
    }

    /**
     * DELETE  /company-pipeline/:id : delete the "id" companyPipelineEntity.
     *
     * @param id the id of the companyPipelineEntity to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/company-pipeline/{id}")
    @Timed
    public ResponseEntity<Void> deleteCompanyPipeline(@PathVariable Long id) throws NotFoundException {
        log.debug("REST request to delete CompanyPipelineEntity : {}", id);
        pipelineService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
