package io.avand.web.rest;

import com.codahale.metrics.annotation.Timed;

import io.avand.security.AuthoritiesConstants;
import io.avand.service.CompanyPipelineService;
import io.avand.service.dto.CompanyPipelineDTO;
import io.avand.web.rest.component.CompanyPipelineComponent;
import io.avand.web.rest.errors.BadRequestAlertException;
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
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * REST controller for managing CompanyPipelineEntity.
 */
@RestController
@RequestMapping("/api/company-pipeline")
@Secured(AuthoritiesConstants.SUBSCRIPTION)
public class CompanyPipelineResource {

    private final Logger log = LoggerFactory.getLogger(CompanyPipelineResource.class);

    private static final String ENTITY_NAME = "companyPipelineEntity";

    private final CompanyPipelineService pipelineService;

    private final CompanyPipelineComponent pipelineComponent;

    public CompanyPipelineResource(CompanyPipelineService pipelineService,
                                   CompanyPipelineComponent pipelineComponent) {
        this.pipelineService = pipelineService;
        this.pipelineComponent = pipelineComponent;
    }

    /**
     * POST  /company-pipeline : Create a new companyPipelineEntity.
     *
     * @param companyPipelineDTO the companyPipelineEntity to create
     * @return the ResponseEntity with status 201 (Created) and with body the new companyPipelineEntity, or with status 400 (Bad Request) if the companyPipelineEntity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping
    @Timed
    @PreAuthorize("isMember(#companyPipelineDTO.companyId,'COMPANY','ADD_PIPELINE')")
    public ResponseEntity<ResponseVM<CompanyPipelineDTO>> createCompanyPipeline
    (@RequestBody CompanyPipelineDTO companyPipelineDTO)
        throws URISyntaxException, NotFoundException {
        log.debug("REST request to save CompanyPipelineEntity : {}", companyPipelineDTO);
        if (companyPipelineDTO.getId() != null) {
            throw new BadRequestAlertException("A new companyPipelineEntity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ResponseVM<CompanyPipelineDTO> result = pipelineComponent.save(companyPipelineDTO);
        return ResponseEntity.created(new URI("/api/company-pipeline/" + result.getData().getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getData().getId().toString()))
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
    @PutMapping
    @Timed
    @PreAuthorize("isMember(#companyPipelineDTO.companyId,'COMPANY','ADD_PIPELINE')")
    public ResponseEntity<ResponseVM<CompanyPipelineDTO>> updateCompanyPipeline
    (@RequestBody CompanyPipelineDTO companyPipelineDTO)
        throws URISyntaxException, NotFoundException {
        log.debug("REST request to update CompanyPipelineEntity : {}", companyPipelineDTO);
        if (companyPipelineDTO.getId() == null) {
            return this.createCompanyPipeline(companyPipelineDTO);
        }
        ResponseVM<CompanyPipelineDTO> result = pipelineComponent.save(companyPipelineDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, companyPipelineDTO.getId().toString()))
            .body(result);
    }


    /**
     * GET  /company-pipeline/:id : get the "id" companyPipelineEntity.
     *
     * @param id the id of the companyPipelineEntity to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the companyPipelineEntity, or with status 404 (Not Found)
     */
    @GetMapping("/{id}")
    @Timed
    @PreAuthorize("isMember(#id,'COMPANY_PIPELINE','VIEW_PIPELINE')")
    public ResponseEntity<ResponseVM<CompanyPipelineDTO>> getCompanyPipeline(@PathVariable Long id) {
        log.debug("REST request to get CompanyPipelineEntity : {}", id);
        try {
            ResponseVM<CompanyPipelineDTO> companyPipelineDTO = pipelineComponent.findById(id);
            return new ResponseEntity<>(companyPipelineDTO, HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }


    /**
     * GET  /company-pipeline : get all the companyPipelineEntities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of companyPipelineEntities in body
     */
    @GetMapping
    @Timed
    @PreAuthorize("isMember('VIEW_PIPELINE')")
    public ResponseEntity<Page<ResponseVM<CompanyPipelineDTO>>> getAllCompanyPipeline(@ApiParam Pageable pageable) {
        log.debug("REST request to get all CompanyPipelineEntities");
        try {
            Page<ResponseVM<CompanyPipelineDTO>> companyPipelineDTOS = pipelineComponent.findAll(pageable);
            return new ResponseEntity<>(companyPipelineDTOS, HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

    /**
     * DELETE  /company-pipeline/:id : delete the "id" companyPipelineEntity.
     *
     * @param id the id of the companyPipelineEntity to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/{id}")
    @Timed
    @PreAuthorize("isMember(#id,'COMPANY_PIPELINE','DELETE_PIPELINE')")
    public ResponseEntity<Void> deleteCompanyPipeline(@PathVariable Long id) throws NotFoundException {
        log.debug("REST request to delete CompanyPipelineEntity : {}", id);
        pipelineService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
