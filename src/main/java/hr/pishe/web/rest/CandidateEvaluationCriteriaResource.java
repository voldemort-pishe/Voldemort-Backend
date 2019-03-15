package hr.pishe.web.rest;

import com.codahale.metrics.annotation.Timed;
import hr.pishe.service.CandidateEvaluationCriteriaService;
import hr.pishe.service.dto.CandidateEvaluationCriteriaDTO;
import hr.pishe.web.rest.component.CandidateEvaluationCriteriaComponent;
import hr.pishe.web.rest.errors.BadRequestAlertException;
import hr.pishe.web.rest.errors.ServerErrorException;
import hr.pishe.web.rest.util.HeaderUtil;
import hr.pishe.web.rest.vm.response.ResponseVM;
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

import java.net.URI;
import java.net.URISyntaxException;


/**
 * REST controller for managing CandidateEvaluationCriteriaDTO.
 */
@RestController
@RequestMapping("/api/candidate-evaluation-criteria")
public class CandidateEvaluationCriteriaResource {

    private final Logger log = LoggerFactory.getLogger(CandidateEvaluationCriteriaResource.class);

    private static final String ENTITY_NAME = "candidateEvaluationCriteriaDTO";

    private final CandidateEvaluationCriteriaService candidateEvaluationCriteriaService;
    private final CandidateEvaluationCriteriaComponent candidateEvaluationCriteriaComponent;

    public CandidateEvaluationCriteriaResource(CandidateEvaluationCriteriaService candidateEvaluationCriteriaService,
                                               CandidateEvaluationCriteriaComponent candidateEvaluationCriteriaComponent) {
        this.candidateEvaluationCriteriaService = candidateEvaluationCriteriaService;
        this.candidateEvaluationCriteriaComponent = candidateEvaluationCriteriaComponent;
    }

    /**
     * POST  /candidate-evaluation-criteria : Create a new candidateEvaluationCriteriaDTO.
     *
     * @param candidateEvaluationCriteriaDTO the candidateEvaluationCriteriaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new candidateEvaluationCriteriaDTO, or with status 400 (Bad Request) if the candidateEvaluationCriteriaDTO has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping
    @Timed
    @PreAuthorize("isMember(#candidateEvaluationCriteriaDTO.candidateId,'CANDIDATE','ADD_CANDIDATE_CRITERIA')")
    public ResponseEntity<ResponseVM<CandidateEvaluationCriteriaDTO>> create(@RequestBody CandidateEvaluationCriteriaDTO candidateEvaluationCriteriaDTO) throws URISyntaxException {
        log.debug("REST request to save CandidateEvaluationCriteriaDTO : {}", candidateEvaluationCriteriaDTO);
        if (candidateEvaluationCriteriaDTO.getId() != null) {
            throw new BadRequestAlertException("A new candidateEvaluationCriteriaDTO cannot already have an ID", ENTITY_NAME, "idexists");
        }
        try {
            ResponseVM<CandidateEvaluationCriteriaDTO> result = candidateEvaluationCriteriaComponent.save(candidateEvaluationCriteriaDTO);
            return ResponseEntity.created(new URI("/api/candidate-evaluation-criteria/" + result.getData().getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getData().getId().toString()))
                .body(result);
        } catch (NotFoundException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

    /**
     * PUT  /candidate-evaluation-criteria : Updates an existing candidateEvaluationCriteriaDTO.
     *
     * @param candidateEvaluationCriteriaDTO the candidateEvaluationCriteriaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated candidateEvaluationCriteriaDTO,
     * or with status 400 (Bad Request) if the candidateEvaluationCriteriaDTO is not valid,
     * or with status 500 (Internal Server Error) if the candidateEvaluationCriteriaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping
    @Timed
    @PreAuthorize("isMember(#candidateEvaluationCriteriaDTO.id,'CANDIDATE_CRITERIA','EDIT_CANDIDATE_CRITERIA')")
    public ResponseEntity<ResponseVM<CandidateEvaluationCriteriaDTO>> update(@RequestBody CandidateEvaluationCriteriaDTO candidateEvaluationCriteriaDTO) throws URISyntaxException {
        log.debug("REST request to update CandidateEvaluationCriteriaDTO : {}", candidateEvaluationCriteriaDTO);
        if (candidateEvaluationCriteriaDTO.getId() == null) {
            return create(candidateEvaluationCriteriaDTO);
        }
        try {
            ResponseVM<CandidateEvaluationCriteriaDTO> result = candidateEvaluationCriteriaComponent.save(candidateEvaluationCriteriaDTO);
            return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, candidateEvaluationCriteriaDTO.getId().toString()))
                .body(result);
        } catch (NotFoundException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

    /**
     * GET  /candidate-evaluation-criteria : get all the candidateEvaluationCriteriaEntities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of candidateEvaluationCriteriaEntities in body
     */
    @GetMapping("/candidate/{id}")
    @Timed
    @PreAuthorize("isMember(#candidateId,'CANDIDATE','VIEW_CANDIDATE_CRITERIA')")
    public ResponseEntity<Page<ResponseVM<CandidateEvaluationCriteriaDTO>>> getAllByCandidateId(@PathVariable("id") Long candidateId, @ApiParam Pageable pageable) {
        log.debug("REST request to get all CandidateEvaluationCriteriaEntities");
        try {
            Page<ResponseVM<CandidateEvaluationCriteriaDTO>> responseVMS = candidateEvaluationCriteriaComponent.findAllByCandidateId(candidateId, pageable);
            return new ResponseEntity<>(responseVMS, HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

    /**
     * GET  /candidate-evaluation-criteria/:id : get the "id" candidateEvaluationCriteriaDTO.
     *
     * @param id the id of the candidateEvaluationCriteriaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the candidateEvaluationCriteriaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/{id}")
    @Timed
    @PreAuthorize("isMember(#id,'CANDIDATE_CRITERIA','VIEW_CANDIDATE_CRITERIA')")
    public ResponseEntity<ResponseVM<CandidateEvaluationCriteriaDTO>> getById(@PathVariable Long id) {
        log.debug("REST request to get CandidateEvaluationCriteriaDTO : {}", id);
        try {
            ResponseVM<CandidateEvaluationCriteriaDTO> candidateEvaluationCriteriaDTO = candidateEvaluationCriteriaComponent.findById(id);
            return new ResponseEntity<>(candidateEvaluationCriteriaDTO, HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

    /**
     * DELETE  /candidate-evaluation-criteria/:id : delete the "id" candidateEvaluationCriteriaDTO.
     *
     * @param id the id of the candidateEvaluationCriteriaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/{id}")
    @Timed
    @PreAuthorize("isMember(#id,'CANDIDATE_CRITERIA','DELETE_CANDIDATE_CRITERIA')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request to delete CandidateEvaluationCriteriaDTO : {}", id);
        try {
            candidateEvaluationCriteriaService.delete(id);
            return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
        } catch (NotFoundException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }
}
