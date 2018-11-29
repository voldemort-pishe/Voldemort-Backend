package io.avand.web.rest;

import io.avand.security.AuthoritiesConstants;
import io.avand.security.SecurityUtils;
import io.avand.service.CompanyMemberService;
import io.avand.service.dto.CompanyMemberDTO;
import io.avand.web.rest.component.CompanyMemberComponent;
import io.avand.web.rest.errors.ServerErrorException;
import io.avand.web.rest.vm.CompanyMemberFilterVM;
import io.avand.web.rest.vm.CompanyMemberVM;
import io.avand.web.rest.vm.response.ResponseVM;
import io.swagger.annotations.ApiParam;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api/company-member")
@Secured(AuthoritiesConstants.SUBSCRIPTION)
public class CompanyMemberResource {

    private final static String ENTITY_NAME = "CompanyMemberEntity";
    private final Logger log = LoggerFactory.getLogger(CompanyMemberResource.class);
    private final CompanyMemberService companyMemberService;
    private final CompanyMemberComponent companyMemberComponent;

    public CompanyMemberResource(CompanyMemberService companyMemberService,
                                 CompanyMemberComponent companyMemberComponent) {
        this.companyMemberService = companyMemberService;
        this.companyMemberComponent = companyMemberComponent;
    }

    @PostMapping
    public ResponseEntity<ResponseVM<CompanyMemberDTO>> save(@RequestBody @Valid CompanyMemberDTO companyMemberDTO)
        throws URISyntaxException {
        log.debug("REST Request to save company member : {}", companyMemberDTO);

        try {
            ResponseVM<CompanyMemberDTO> result = companyMemberComponent.save(companyMemberDTO);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            throw new ServerErrorException("شماه قبلا عضو شده‌اید");
        } catch (NotFoundException | SecurityException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

    @PostMapping("/all")
    public ResponseEntity<List<ResponseVM<CompanyMemberDTO>>> saveAll(@RequestBody @Valid CompanyMemberVM companyMemberVM)
        throws URISyntaxException {
        log.debug("REST Request to save company member : {}", companyMemberVM);

        try {
            List<ResponseVM<CompanyMemberDTO>> result = companyMemberComponent.saveAll(companyMemberVM.getEmails());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            throw new ServerErrorException("شماه قبلا عضو شده‌اید");
        } catch (NotFoundException | SecurityException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseVM<CompanyMemberDTO>> getById(@PathVariable("id") Long id) {
        log.debug("REST Request to find company member by id : {}", id);
        try {
            ResponseVM<CompanyMemberDTO> companyMemberDTO = companyMemberComponent.findById(id);
            return new ResponseEntity<>(companyMemberDTO, HttpStatus.OK);
        } catch (NotFoundException | SecurityException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<Page<ResponseVM<CompanyMemberDTO>>> getAll
        (@ApiParam Pageable pageable, CompanyMemberFilterVM filterVM) {
        log.debug("Request to find all company member");
        try {
            Page<ResponseVM<CompanyMemberDTO>> companyMemberDTOS = companyMemberComponent.findAllByFilter(filterVM, pageable);
            return new ResponseEntity<>(companyMemberDTOS, HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

    @GetMapping("/active")
    public ResponseEntity<Page<ResponseVM<CompanyMemberDTO>>> getAllActive(@ApiParam Pageable pageable) {
        log.debug("Request to find active company member");
        try {
            Page<ResponseVM<CompanyMemberDTO>> companyMemberDTOS = companyMemberComponent.findAllActiveMember(pageable);
            return new ResponseEntity<>(companyMemberDTOS, HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        log.debug("REST Request to delete company member by id : {}", id);
        try {
            companyMemberService.delete(id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (NotFoundException | SecurityException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }
}
