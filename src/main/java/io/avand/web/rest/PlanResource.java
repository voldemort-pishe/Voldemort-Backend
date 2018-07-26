package io.avand.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.avand.domain.entity.jpa.PlanEntity;

import io.avand.repository.jpa.PlanRepository;
import io.avand.service.PlanService;
import io.avand.service.dto.PlanDTO;
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
 * REST controller for managing PlanEntity.
 */
@RestController
@RequestMapping("/api")
public class PlanResource {

    private final Logger log = LoggerFactory.getLogger(PlanResource.class);

    private static final String ENTITY_NAME = "planEntity";

    private final PlanRepository planRepository;

    private final PlanService planService;

    public PlanResource(PlanRepository planRepository,
                        PlanService planService) {
        this.planRepository = planRepository;
        this.planService = planService;
    }

    /**
     * POST  /plan-entities : Create a new planEntity.
     *
     * @param planEntity the planEntity to create
     * @return the ResponseEntity with status 201 (Created) and with body the new planEntity, or with status 400 (Bad Request) if the planEntity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/plan")
    @Timed
    public ResponseEntity<PlanEntity> createPlanEntity(@RequestBody PlanEntity planEntity) throws URISyntaxException {
        log.debug("REST request to save PlanEntity : {}", planEntity);
        if (planEntity.getId() != null) {
            throw new BadRequestAlertException("A new planEntity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PlanEntity result = planRepository.save(planEntity);
        return ResponseEntity.created(new URI("/api/plan-entities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /plan-entities : Updates an existing planEntity.
     *
     * @param planEntity the planEntity to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated planEntity,
     * or with status 400 (Bad Request) if the planEntity is not valid,
     * or with status 500 (Internal Server Error) if the planEntity couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/plan")
    @Timed
    public ResponseEntity<PlanEntity> updatePlanEntity(@RequestBody PlanEntity planEntity) throws URISyntaxException {
        log.debug("REST request to update PlanEntity : {}", planEntity);
        if (planEntity.getId() == null) {
            return createPlanEntity(planEntity);
        }
        PlanEntity result = planRepository.save(planEntity);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, planEntity.getId().toString()))
            .body(result);
    }

    /**
     * GET  /plan-entities : get all the planEntities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of planEntities in body
     */
    @GetMapping("/plan")
    @Timed
    public List<PlanEntity> getAllPlanEntities() {
        log.debug("REST request to get all PlanEntities");
        return planRepository.findAll();
    }

    /**
     * GET  /plan-entities/:id : get the "id" planEntity.
     *
     * @param id the id of the planEntity to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the planEntity, or with status 404 (Not Found)
     */
    @GetMapping("/plan/{id}")
    @Timed
    public ResponseEntity getPlanEntity(@PathVariable Long id) {
        log.debug("REST request to get PlanEntity : {}", id);
        Optional<PlanDTO> planDTO = planService.findOneById(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(planDTO));
    }

    @GetMapping("/active-plans")
    @Timed
    public ResponseEntity getActivePlans(@ApiParam Pageable pageable) {
        log.debug("REST request to get active plans");
        Page<PlanDTO> planDTOS = planService.getActivePlans(pageable);
        return new ResponseEntity<>(planDTOS, HttpStatus.OK);
    }

    /**
     * DELETE  /plan-entities/:id : delete the "id" planEntity.
     *
     * @param id the id of the planEntity to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/plan/{id}")
    @Timed
    public ResponseEntity<Void> deletePlanEntity(@PathVariable Long id) {
        log.debug("REST request to delete PlanEntity : {}", id);
        planRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
