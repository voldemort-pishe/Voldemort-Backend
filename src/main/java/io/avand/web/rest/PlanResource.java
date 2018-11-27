package io.avand.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.avand.domain.entity.jpa.PlanEntity;

import io.avand.domain.enumeration.PlanType;
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

    private final PlanService planService;

    public PlanResource(PlanService planService) {
        this.planService = planService;
    }

    /**
     * POST  /plan : Create a new planDTO.
     *
     * @param planDTO the planDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new planDTO, or with status 400 (Bad Request) if the planDTO has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/plan")
    @Timed
    public ResponseEntity createPlanEntity(@RequestBody PlanDTO planDTO) throws URISyntaxException {
        log.debug("REST request to save PlanEntity : {}", planDTO);
        if (planDTO.getId() != null) {
            throw new BadRequestAlertException("A new plan cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PlanDTO result = planService.save(planDTO);
        return ResponseEntity.created(new URI("/api/plan/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /plan : Updates an existing planDTO.
     *
     * @param planDTO the planDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated planDTO,
     * or with status 400 (Bad Request) if the planDTO is not valid,
     * or with status 500 (Internal Server Error) if the planEntity couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/plan")
    @Timed
    public ResponseEntity updatePlanEntity(@RequestBody PlanDTO planDTO) throws URISyntaxException {
        log.debug("REST request to update PlanEntity : {}", planDTO);
        if (planDTO.getId() == null) {
            return createPlanEntity(planDTO);
        }
        PlanDTO result = planService.save(planDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, planDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /plan : get all the planDTOs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of planDTOs in body
     */
    @GetMapping("/plan")
    @Timed
    public ResponseEntity getAllPlans(@ApiParam Pageable pageable) {
        log.debug("REST request to get all PlanEntities");
        return new ResponseEntity<>(planService.findAllByType(pageable, PlanType.MONEY), HttpStatus.OK);
    }

    /**
     * GET  /plan/:id : get the "id" planDTO.
     *
     * @param id the id of the planDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the planDTO, or with status 404 (Not Found)
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
        Page<PlanDTO> planDTOS = planService.findActiveByType(pageable, PlanType.MONEY);
        return new ResponseEntity<>(planDTOS, HttpStatus.OK);
    }

    /**
     * DELETE  /plan/:id : delete the "id" planDTO.
     *
     * @param id the id of the planDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/plan/{id}")
    @Timed
    public ResponseEntity<Void> deletePlan(@PathVariable Long id) {
        log.debug("REST request to delete PlanEntity : {}", id);
        planService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
