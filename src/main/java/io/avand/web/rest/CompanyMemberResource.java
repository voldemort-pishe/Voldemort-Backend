package io.avand.web.rest;

import io.avand.service.CompanyMemberService;
import io.avand.service.dto.CompanyMemberDTO;
import io.avand.web.rest.errors.BadRequestAlertException;
import io.avand.web.rest.errors.ServerErrorException;
import io.avand.web.rest.util.HeaderUtil;
import io.avand.web.rest.vm.CompanyMemberVM;
import io.swagger.annotations.ApiParam;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/company-member")
public class CompanyMemberResource {

    private final static String ENTITY_NAME = "CompanyMemberEntity";
    private final Logger log = LoggerFactory.getLogger(CompanyMemberResource.class);
    private final CompanyMemberService companyMemberService;

    public CompanyMemberResource(CompanyMemberService companyMemberService) {
        this.companyMemberService = companyMemberService;
    }

    @PostMapping
    public ResponseEntity save(@RequestBody @Valid CompanyMemberVM companyMemberVM,
                               @RequestAttribute("companyId") Long companyId) throws URISyntaxException {
        log.debug("REST Request to save company member : {}", companyMemberVM);

        try {
            List<CompanyMemberDTO> result = companyMemberService.save(companyMemberVM.getEmails(), companyId);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (NotFoundException | SecurityException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

    @GetMapping("/company")
    public ResponseEntity getAll(@RequestAttribute("companyId") Long companyId, @ApiParam Pageable pageable) {
        log.debug("Request to find all company member by company id : {}", companyId);
        try {
            Page<CompanyMemberDTO> companyMemberDTOS = companyMemberService.findAll(companyId, pageable);
            return new ResponseEntity<>(companyMemberDTOS, HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable("id") Long id) {
        log.debug("REST Request to find company member by id : {}", id);
        try {
            CompanyMemberDTO companyMemberDTO = companyMemberService.findById(id);
            return new ResponseEntity<>(companyMemberDTO, HttpStatus.OK);
        } catch (NotFoundException | SecurityException e) {
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

    @GetMapping("/emails")
    public ResponseEntity getMemberEmails() {
        List<String> emails = new ArrayList<>();
        emails.add("farhang.darzi@gmail.com");
        emails.add("majid.khoshnasib@gmail.com");
        emails.add("mortezamrd75@gmail.com");
        emails.add("ehdi.sh@gmail.com");
        emails.add("pouyaashna@gmail.com");
        return new ResponseEntity<>(emails, HttpStatus.OK);
    }
}
