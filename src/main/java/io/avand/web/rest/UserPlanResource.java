package io.avand.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.avand.config.ApplicationProperties;
import io.avand.service.*;
import io.avand.service.dto.InvoiceDTO;
import io.avand.web.rest.component.InvoiceComponent;
import io.avand.web.rest.errors.ServerErrorException;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/api/user-plan")
public class UserPlanResource {

    private final Logger logger = LoggerFactory.getLogger(UserPlanResource.class);

    private final UserPlanService userPlanService;

    private final InvoiceService invoiceService;

    private final ApplicationProperties applicationProperties;

    public UserPlanResource(UserPlanService userPlanService,
                            InvoiceService invoiceService,
                            ApplicationProperties applicationProperties) {
        this.userPlanService = userPlanService;
        this.invoiceService = invoiceService;
        this.applicationProperties = applicationProperties;
    }

    @PostMapping("{planId}")
    @Timed
    public ResponseEntity<InvoiceDTO> saveUserPlan(@PathVariable Long planId, HttpServletResponse response) throws IOException {
        logger.debug("REST request to save a plan for a user : {}", planId);
        try {
            InvoiceDTO invoiceDTO = invoiceService.saveByPlanId(planId);
            userPlanService.save(planId, invoiceDTO.getId());
            return new ResponseEntity<>(invoiceDTO, HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

    @GetMapping("{planId}")
    @Timed
    public ResponseEntity<InvoiceDTO> getUserPlan(@PathVariable Long planId, HttpServletResponse response) throws IOException {
        logger.debug("REST request to get a plan for a user : {}", planId);
        try {
            Optional<InvoiceDTO> oneById = invoiceService.findOneById(planId);
            return new ResponseEntity<>(oneById.orElse(null), HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

}
