package io.avand.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.avand.domain.enumeration.InvoiceStatus;
import io.avand.security.SecurityUtils;
import io.avand.service.InvoiceService;
import io.avand.service.PlanService;
import io.avand.service.UserService;
import io.avand.service.dto.InvoiceDTO;
import io.avand.service.dto.PlanDTO;
import io.avand.service.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserPlanResource {

    private final Logger logger = LoggerFactory.getLogger(UserPlanResource.class);

    private final UserService userService;

    private final PlanService planService;

    private final InvoiceService invoiceService;

    public UserPlanResource(UserService userService,
                            PlanService planService,
                            InvoiceService invoiceService) {
        this.userService = userService;
        this.planService = planService;
        this.invoiceService = invoiceService;
    }

    @GetMapping("/user-plan/{planTitle}")
    @Timed
    public ResponseEntity saveUserPlan(@PathVariable String planTitle) {

        Long userId = SecurityUtils.getCurrentUserId();
        Optional<UserDTO> userDTOOptional = userService.findById(userId);

        Optional<PlanDTO> planDTO = planService.findOneByTitle(planTitle);

        InvoiceDTO invoiceDTO = new InvoiceDTO();
        invoiceDTO.setAmount(planDTO.get().getAmount());
        invoiceDTO.setUserId(userId);

        //TODO : should check if application did save the invoice or not.
        invoiceService.save(invoiceDTO);

        UserDTO response = userService.update(userDTOOptional.get());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
