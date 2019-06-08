package hr.pishe.web.rest;

import hr.pishe.security.SecurityUtils;
import hr.pishe.service.SubscriptionService;
import hr.pishe.service.dto.SubscriptionDTO;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/subscription")
public class SubscriptionResource {
    private final Logger log = LoggerFactory.getLogger(SubscriptionResource.class);

    private final SubscriptionService subscriptionService;

    private final SecurityUtils securityUtils;

    public SubscriptionResource(SubscriptionService subscriptionService,
                                SecurityUtils securityUtils) {
        this.subscriptionService = subscriptionService;
        this.securityUtils = securityUtils;
    }

    @GetMapping("/check")
    public ResponseEntity checkSubscription() {
        log.debug("REST Request to check user subscription");
        try {
            SubscriptionDTO subscriptionDTO = subscriptionService.checkSubscription(securityUtils.getCurrentCompanyId());
            return new ResponseEntity<>(subscriptionDTO, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}
