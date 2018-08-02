package io.avand.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.avand.domain.enumeration.SubscriptionStatus;
import io.avand.security.SecurityUtils;
import io.avand.service.InvoiceService;
import io.avand.service.PlanService;
import io.avand.service.SubscriptionService;
import io.avand.service.UserService;
import io.avand.service.dto.InvoiceDTO;
import io.avand.service.dto.PlanDTO;
import io.avand.service.dto.SubscriptionDTO;
import io.avand.service.dto.UserDTO;
import io.avand.web.rest.errors.ServerErrorException;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserPlanResource {

    private final Logger logger = LoggerFactory.getLogger(UserPlanResource.class);

    private final UserService userService;

    private final PlanService planService;

    private final InvoiceService invoiceService;

    private final SecurityUtils securityUtils;

    private final SubscriptionService subscriptionService;

    public UserPlanResource(UserService userService,
                            PlanService planService,
                            InvoiceService invoiceService,
                            SecurityUtils securityUtils,
                            SubscriptionService subscriptionService) {
        this.userService = userService;
        this.planService = planService;
        this.invoiceService = invoiceService;
        this.securityUtils = securityUtils;
        this.subscriptionService = subscriptionService;
    }

    @GetMapping("/user-plan/{planId}")
    @Timed
    public ResponseEntity saveUserPlan(@PathVariable Long planId) {
        logger.debug("REST request to save a plan for a user : {}", planId);
        try {
            Long userId = securityUtils.getCurrentUserId();
            Optional<UserDTO> userDTOOptional = userService.findById(userId);

            Optional<PlanDTO> planDTO = planService.findOneById(planId);

            if (!planDTO.isPresent()) {
                throw new ServerErrorException("Your chosen plan has not been found!");
            }

            SubscriptionDTO subscriptionDTO = new SubscriptionDTO();
            subscriptionDTO.setPlanTitle(planDTO.get().getTitle());
            subscriptionDTO.setStartDate(ZonedDateTime.now());
            //TODO: Should get plan period and set it in subscription object
//            subscriptionDTO.setEndDate(ZonedDateTime.now().plus(planDTO.get().getDescription()));
            subscriptionDTO.setStatus(SubscriptionStatus.INITIALIZED);
            subscriptionDTO.setUserId(userId);

            subscriptionService.save(subscriptionDTO);

            if (planDTO.get().getAmount() != 0) {

                InvoiceDTO invoiceDTO = new InvoiceDTO();
                invoiceDTO.setAmount(planDTO.get().getAmount());
                invoiceDTO.setUserId(userId);

                //TODO : should check if application did save the invoice or not.
                invoiceService.save(invoiceDTO);
            }

            UserDTO response = userService.update(userDTOOptional.get());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }
}
