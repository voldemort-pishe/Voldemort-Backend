package hr.pishe.web.rest;

import com.codahale.metrics.annotation.Timed;
import hr.pishe.security.SecurityUtils;
import hr.pishe.service.CloudflareService;
import hr.pishe.service.CompanyService;
import hr.pishe.service.dto.CompanyDTO;
import hr.pishe.web.rest.component.CompanyComponent;
import hr.pishe.web.rest.errors.BadRequestAlertException;
import hr.pishe.web.rest.errors.ServerErrorException;
import hr.pishe.web.rest.util.HeaderUtil;
import hr.pishe.web.rest.vm.response.ResponseVM;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * REST controller for managing CompanyEntity.
 */
@RestController
@RequestMapping("/api/company")
public class CompanyResource {

    private final Logger log = LoggerFactory.getLogger(CompanyResource.class);

    private static final String ENTITY_NAME = "companyEntity";

    private final CompanyService companyService;
    private final CloudflareService cloudflareService;
    private final SecurityUtils securityUtils;

    private final CompanyComponent companyComponent;

    public CompanyResource(CompanyService companyService,
                           CloudflareService cloudflareService,
                           SecurityUtils securityUtils,
                           CompanyComponent companyComponent) {
        this.companyService = companyService;
        this.cloudflareService = cloudflareService;
        this.securityUtils = securityUtils;
        this.companyComponent = companyComponent;
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
    @PreAuthorize("isMember('ADD_COMPANY')")
    public ResponseEntity<ResponseVM<CompanyDTO>> createCompany(@Valid @RequestBody CompanyDTO companyDTO)
        throws URISyntaxException {
        log.debug("REST request to save Company : {}", companyDTO);
        if (companyDTO.getId() != null) {
            throw new BadRequestAlertException("A new companyEntity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        try {
            ResponseVM<CompanyDTO> result = companyComponent.save(companyDTO);
            return ResponseEntity.created(new URI("/api/company/" + result.getData().getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getData().getId().toString()))
                .body(result);
        } catch (NotFoundException e) {
            throw new ServerErrorException(e.getMessage());
        } catch (HttpClientErrorException e) {
            throw new ServerErrorException("مشگلی در ایجاد دامنه پیش آمده است لطفا مجدد تلاش نمایید");
        }
    }


    /**
     * GET  /company/:id : get the "id" companyEntity.
     *
     * @return the ResponseEntity with status 200 (OK) and with body the companyEntity, or with status 404 (Not Found)
     */
    @GetMapping
    @Timed
    @PreAuthorize("isMember('VIEW_COMPANY')")
    public ResponseEntity<ResponseVM<CompanyDTO>> getCompany() {
        log.debug("REST request to get CompanyEntity");
        try {
            ResponseVM<CompanyDTO> companyDTO = companyComponent.findById(securityUtils.getCurrentCompanyId());
            return new ResponseEntity<>(companyDTO, HttpStatus.OK);
        } catch (NotFoundException | SecurityException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }


    /**
     * PUT  /company/:id : update the companyEntity.
     *
     * @return the ResponseEntity with status 200 (OK) and with body the companyEntity, or with status 404 (Not Found)
     */
    @PutMapping("/{id}")
    @Timed
    @PreAuthorize("isMember(#id,'COMPANY','EDIT_COMPANY')")
    public ResponseEntity<CompanyDTO> updateCompany(@PathVariable Long id,
                                                    @Valid @RequestBody CompanyDTO companyDTO){
        log.debug("REST request to get CompanyEntity");
        try {
            CompanyDTO update = companyService.update(companyDTO);
            return new ResponseEntity<>(update, HttpStatus.OK);
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
    @PreAuthorize("isMember(#id,'COMPANY','DELETE_COMPANY')")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
        log.debug("REST request to delete CompanyEntity : {}", id);
        try {
            companyService.delete(id);
            return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
        } catch (NotFoundException | SecurityException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }
}
