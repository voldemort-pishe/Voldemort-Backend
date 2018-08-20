package io.avand.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.avand.service.*;
import io.avand.service.dto.InvoiceDTO;
import io.avand.service.dto.UserPlanDTO;
import io.avand.web.rest.errors.ServerErrorException;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserPlanResource {

    private final Logger logger = LoggerFactory.getLogger(UserPlanResource.class);

    private final UserPlanService userPlanService;

    private final InvoiceService invoiceService;

    public UserPlanResource(UserPlanService userPlanService,
                            InvoiceService invoiceService) {
        this.userPlanService = userPlanService;
        this.invoiceService = invoiceService;
    }

    @GetMapping("/user-plan-invoice/{planId}")
    @Timed
    public ResponseEntity saveUserPlan(@PathVariable Long planId) {
        logger.debug("REST request to save a plan for a user : {}", planId);
        try {
            InvoiceDTO invoiceDTO = invoiceService.saveByPlanId(planId);
            userPlanService.save(planId, invoiceDTO.getId());
            return new ResponseEntity<>(invoiceDTO, HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }
}
