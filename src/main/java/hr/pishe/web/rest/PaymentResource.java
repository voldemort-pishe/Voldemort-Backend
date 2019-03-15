package hr.pishe.web.rest;

import com.codahale.metrics.annotation.Timed;
import hr.pishe.config.ApplicationProperties;
import hr.pishe.domain.enumeration.InvoiceStatus;
import hr.pishe.domain.enumeration.PaymentType;
import hr.pishe.payment.service.PaymentService;
import hr.pishe.payment.service.dto.PaymentDTO;
import hr.pishe.payment.service.error.PaymentException;
import hr.pishe.service.CompanyPlanService;
import hr.pishe.service.InvoiceService;
import hr.pishe.service.SubscriptionService;
import hr.pishe.service.UserAuthorityService;
import hr.pishe.service.dto.CompanyPlanDTO;
import hr.pishe.service.dto.InvoiceDTO;
import hr.pishe.web.rest.errors.ServerErrorException;
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

    private final CompanyPlanService companyPlanService;

    private final SubscriptionService subscriptionService;

    private final ApplicationProperties applicationProperties;

    private final UserAuthorityService userAuthorityService;

    public PaymentResource(PaymentService paymentService,
                           InvoiceService invoiceService,
                           CompanyPlanService companyPlanService,
                           SubscriptionService subscriptionService,
                           ApplicationProperties applicationProperties,
                           UserAuthorityService userAuthorityService) {
        this.paymentService = paymentService;
        this.invoiceService = invoiceService;
        this.companyPlanService = companyPlanService;
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
                Optional<CompanyPlanDTO> companyPlanDTO = companyPlanService.findByInvoiceId(foundInvoice.getId());

                subscriptionService.save(companyPlanDTO.get().getId(), foundInvoice.getCompanyId());

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
