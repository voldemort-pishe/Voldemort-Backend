package io.avand.web.rest;

import com.codahale.metrics.annotation.Timed;

import io.avand.service.CompanyService;
import io.avand.service.dto.CompanyDTO;
import io.avand.web.rest.errors.BadRequestAlertException;
import io.avand.web.rest.errors.ServerErrorException;
import io.avand.web.rest.util.HeaderUtil;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;

/**
 * REST controller for managing CompanyEntity.
 */
@RestController
@RequestMapping("/api/company")
public class CompanyResource {

    private final Logger log = LoggerFactory.getLogger(CompanyResource.class);

    private static final String ENTITY_NAME = "companyEntity";

    private final CompanyService companyService;

    public CompanyResource(CompanyService companyService) {
        this.companyService = companyService;
    }


    /**
     * POST  /company : Create a new companyEntity.
     *
     * @param companyDTO the companyEntity to create
     * @return the ResponseEntity with status 201 (Created) and with body the new companyEntity, or with status 400 (Bad Request) if the companyEntity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping
    @Timed
    public ResponseEntity createCompany(@Valid @RequestBody CompanyDTO companyDTO) throws URISyntaxException {
        log.debug("REST request to save Company : {}", companyDTO);
        if (companyDTO.getId() != null) {
            throw new BadRequestAlertException("A new companyEntity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        try {
            CompanyDTO result = companyService.save(companyDTO);
            return ResponseEntity.created(new URI("/api/company/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
        } catch (NotFoundException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

    /**
     * PUT  /company : Updates an existing companyEntity.
     *
     * @param companyDTO the companyEntity to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated companyEntity,
     * or with status 400 (Bad Request) if the companyEntity is not valid,
     * or with status 500 (Internal Server Error) if the companyEntity couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping
    @Timed
    public ResponseEntity updateCompany(@Valid @RequestBody CompanyDTO companyDTO) throws URISyntaxException {
        log.debug("REST request to update Company : {}", companyDTO);
        if (companyDTO.getId() == null) {
            return createCompany(companyDTO);
        }
        try {
            CompanyDTO result = companyService.save(companyDTO);
            return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, companyDTO.getId().toString()))
                .body(result);
        } catch (NotFoundException e) {
            throw new ServerErrorException(e.getMessage());
        }

    }

    /**
     * GET  /company-entities : get all the companyEntities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of companyEntities in body
     */
    @GetMapping
    @Timed
    public ResponseEntity getAllCompany() {
        log.debug("REST request to get all CompanyEntities");
        try {
            List<CompanyDTO> companyDTOS = companyService.findAll();
            return new ResponseEntity<>(companyDTOS, HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

    /**
     * GET  /company/:id : get the "id" companyEntity.
     *
     * @param id the id of the companyEntity to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the companyEntity, or with status 404 (Not Found)
     */
    @GetMapping("/{id}")
    @Timed
    public ResponseEntity getCompany(@PathVariable Long id) {
        log.debug("REST request to get CompanyEntity : {}", id);
        CompanyDTO companyDTO = null;
        try {
            companyDTO = companyService.findById(id);
            return new ResponseEntity<>(companyDTO, HttpStatus.OK);
        } catch (NotFoundException | SecurityException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

    /**
     * DELETE  /company/:id : delete the "id" companyEntity.
     *
     * @param id the id of the companyEntity to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/{id}")
    @Timed
    public ResponseEntity<Void> deleteCompanyEntity(@PathVariable Long id) {
        log.debug("REST request to delete CompanyEntity : {}", id);
        try {
            companyService.delete(id);
            return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
        } catch (NotFoundException | SecurityException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }
}
