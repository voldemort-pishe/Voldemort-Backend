package io.avand.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.avand.config.ApplicationProperties;
import io.avand.domain.enumeration.InvoiceStatus;
import io.avand.domain.enumeration.PaymentType;
import io.avand.payment.service.PaymentService;
import io.avand.payment.service.dto.PaymentDTO;
import io.avand.payment.service.error.PaymentException;
import io.avand.security.AuthoritiesConstants;
import io.avand.service.InvoiceService;
import io.avand.service.SubscriptionService;
import io.avand.service.UserAuthorityService;
import io.avand.service.UserPlanService;
import io.avand.service.dto.InvoiceDTO;
import io.avand.service.dto.SubscriptionDTO;
import io.avand.service.dto.UserPlanDTO;
import io.avand.web.rest.errors.ServerErrorException;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("/api/payment")
public class PaymentResource {

    private Logger logger = LoggerFactory.getLogger(PaymentResource.class);

    private final PaymentService paymentService;

    private final InvoiceService invoiceService;

    private final UserPlanService userPlanService;

    private final SubscriptionService subscriptionService;

    private final ApplicationProperties applicationProperties;

    private final UserAuthorityService userAuthorityService;

    public PaymentResource(PaymentService paymentService,
                           InvoiceService invoiceService,
                           UserPlanService userPlanService,
                           SubscriptionService subscriptionService,
                           ApplicationProperties applicationProperties,
                           UserAuthorityService userAuthorityService) {
        this.paymentService = paymentService;
        this.invoiceService = invoiceService;
        this.userPlanService = userPlanService;
        this.subscriptionService = subscriptionService;
        this.applicationProperties = applicationProperties;
        this.userAuthorityService = userAuthorityService;
    }

    @PostMapping("/{invoiceId}")
    @Timed
    public ResponseEntity createPaymentToken(@PathVariable("invoiceId") Long invoiceId) {
        logger.debug("REST request to create a zarinpal payment token for a invoice : {}", invoiceId);
        try {
            Optional<InvoiceDTO> foundInvoiceDTOOptional = invoiceService.findOneById(invoiceId);

            if (!foundInvoiceDTOOptional.isPresent()) {
                throw new ServerErrorException("فاکتور مورد نظر یافت نشد");
            } else {
                InvoiceDTO foundInvoice = foundInvoiceDTOOptional.get();
                try {
                    HashMap<String, String> response = new HashMap<>();
                    PaymentDTO paymentDTO = paymentService
                        .createPaymentLink(foundInvoice.getTotal(), String.format("پرداخت فاکتور شماره %s", foundInvoice.getId()));
                    foundInvoice.setTrackingCode(paymentDTO.getRefId());
                    response.put("paymentUrl", paymentDTO.getUrl());
                    try {
                        invoiceService.save(foundInvoice);
                    } catch (NotFoundException e) {
                        throw new ServerErrorException(e.getMessage());
                    }
                    return new ResponseEntity<>(response, HttpStatus.OK);
                } catch (PaymentException e) {
                    throw new ServerErrorException(e.getMessage());
                }
            }
        } catch (NotFoundException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

    @GetMapping("/callback")
    @Timed
    public void paymentCallback(@RequestParam("Authority") String authority,
                                @RequestParam("Status") String status,
                                HttpServletResponse response) throws NotFoundException {

        Optional<InvoiceDTO> invoiceDTO = invoiceService.findOneByTrackingCode(authority);
        if (invoiceDTO.isPresent()) {
            InvoiceDTO foundInvoice = invoiceDTO.get();

            try {
                String refId = paymentService.verifyPayment(foundInvoice.getTotal(), authority);
                foundInvoice.setReferenceId(refId);
                foundInvoice.setStatus(InvoiceStatus.SUCCESS);
                foundInvoice.setPaymentDate(ZonedDateTime.now());
                foundInvoice.setPaymentType(PaymentType.ZARINPAL);
                invoiceService.save(foundInvoice);
                Optional<UserPlanDTO> userPlanDTO = userPlanService.findByInvoiceId(foundInvoice.getId());

                SubscriptionDTO subscriptionDTO = new SubscriptionDTO();
                subscriptionDTO.setUserPlanId(userPlanDTO.get().getId());
                subscriptionDTO.setUserId(foundInvoice.getUserId());
                subscriptionDTO.setStartDate(ZonedDateTime.now());
                subscriptionDTO.setEndDate(ZonedDateTime.now().plusDays(userPlanDTO.get().getLength()));
                subscriptionService.save(subscriptionDTO);

                userAuthorityService.grantAuthority(AuthoritiesConstants.SUBSCRIPTION, foundInvoice.getUserId());
            } catch (PaymentException e) {
                foundInvoice.setStatus(InvoiceStatus.FAILED);
                foundInvoice.setPaymentDate(ZonedDateTime.now());
                foundInvoice.setPaymentType(PaymentType.ZARINPAL);
                invoiceService.save(foundInvoice);
            }
            try {
                response.sendRedirect(applicationProperties.getBase().getPanel() + "/#/invoice/" + foundInvoice.getId());
            } catch (IOException e) {
                throw new ServerErrorException(e.getMessage());
            }
        } else {
            throw new ServerErrorException("Sorry, your invoice did not match any!");
        }
    }
}
