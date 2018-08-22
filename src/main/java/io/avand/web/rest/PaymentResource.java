package io.avand.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.avand.config.ApplicationProperties;
import io.avand.domain.enumeration.InvoiceStatus;
import io.avand.domain.enumeration.PaymentType;
import io.avand.security.AuthoritiesConstants;
import io.avand.service.*;
import io.avand.service.dto.*;
import io.avand.web.rest.errors.ServerErrorException;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

    @GetMapping("/{invoiceId}")
    @Timed
    public ResponseEntity createPaymentToken(@PathVariable("invoiceId") Long invoiceId) {
        logger.debug("REST request to create a zarinpal payment token for a invoice : {}", invoiceId);
        try {
            Optional<InvoiceDTO> foundInvoiceDTOOptional = invoiceService.findOneById(invoiceId);

            if (!foundInvoiceDTOOptional.isPresent()) {
                throw new ServerErrorException("فاکتور مورد نظر یافت نشد");
            } else {
                InvoiceDTO foundInvoice = foundInvoiceDTOOptional.get();
                ZarinpalRequestDTO zarinpalRequestDTO = new ZarinpalRequestDTO();

                zarinpalRequestDTO.setAmount(foundInvoice.getTotal());
                zarinpalRequestDTO.setDescription(String.format("پرداخت فاکتور شماره %s", foundInvoice.getId()));

                ResponseEntity responseEntity = paymentService.paymentRequest(zarinpalRequestDTO);
                HashMap<String, String> response = new HashMap<>();

                if (responseEntity.getStatusCode() == HttpStatus.OK) {
                    ZarinpalResponseDTO zarinpalResponseDTO = (ZarinpalResponseDTO) responseEntity.getBody();
                    if ("100".equals(zarinpalResponseDTO.getStatus())) {
                        response.put("paymentUrl", "https://www.zarinpal.com/pg/pay/" + zarinpalResponseDTO.getAuthority());
                        foundInvoice.setTrackingCode(zarinpalResponseDTO.getAuthority());
                        try {
                            invoiceService.save(foundInvoice);
                        } catch (NotFoundException e) {
                            throw new ServerErrorException(e.getMessage());
                        }
                        return new ResponseEntity<>(response, HttpStatus.OK);
                    } else {
                        throw new ServerErrorException("Cannot do payment right now!");
                    }
                } else {
                    throw new ServerErrorException("Cannot do payment right now!");
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

        ZarinpalVerifyRequestDTO zarinpalVerifyRequestDTO = new ZarinpalVerifyRequestDTO();
        zarinpalVerifyRequestDTO.setAuthority(authority);
        Optional<InvoiceDTO> invoiceDTO = invoiceService.findOneByTrackingCode(authority);
        if (invoiceDTO.isPresent()) {
            InvoiceDTO foundInvoice = invoiceDTO.get();
            zarinpalVerifyRequestDTO.setAmount(foundInvoice.getTotal());

            ZarinpalVerifyResponseDTO zarinpalVerifyResponseDTO = (ZarinpalVerifyResponseDTO) paymentService
                .paymentVerify(zarinpalVerifyRequestDTO).getBody();
            if ("OK".equals(status)) {
                if (zarinpalVerifyResponseDTO.getStatus() == -21) {
                    foundInvoice.setReferenceId(zarinpalVerifyResponseDTO.getRefId());
                    foundInvoice.setStatus(InvoiceStatus.SUCCESS);
                    foundInvoice.setPaymentDate(ZonedDateTime.now());
                    foundInvoice.setPaymentType(PaymentType.ZARINPAL);
                    invoiceService.save(foundInvoice);
                    Optional<UserPlanDTO> userPlanDTO = userPlanService.findByInvoiceId(foundInvoice.getId());

                    SubscriptionDTO subscriptionDTO = new SubscriptionDTO();
                    subscriptionDTO.setPlanId(userPlanDTO.get().getId());
                    subscriptionDTO.setUserId(foundInvoice.getUserId());
                    subscriptionDTO.setStartDate(ZonedDateTime.now());
                    subscriptionDTO.setEndDate(ZonedDateTime.now().plusDays(userPlanDTO.get().getLength()));
                    subscriptionService.save(subscriptionDTO);

                    userAuthorityService.grantAuthority(AuthoritiesConstants.SUBSCRIPTION, foundInvoice.getUserId());
                }
            } else {
                foundInvoice.setStatus(InvoiceStatus.FAILED);
                foundInvoice.setPaymentDate(ZonedDateTime.now());
                foundInvoice.setPaymentType(PaymentType.ZARINPAL);
                invoiceService.save(foundInvoice);
            }
            try {
                response.sendRedirect(applicationProperties.getBase().getPanel() + "/#/pages/invoice?id=" + foundInvoice.getId());
            } catch (IOException e) {
                throw new ServerErrorException(e.getMessage());
            }
        } else {
            throw new ServerErrorException("Sorry, your invoice did not match any!");
        }
    }
}
