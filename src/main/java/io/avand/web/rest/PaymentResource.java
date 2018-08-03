package io.avand.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.avand.config.ApplicationProperties;
import io.avand.domain.enumeration.InvoiceStatus;
import io.avand.domain.enumeration.PaymentTransactionStatus;
import io.avand.domain.enumeration.PaymentType;
import io.avand.domain.enumeration.SubscribeState;
import io.avand.security.SecurityUtils;
import io.avand.service.InvoiceService;
import io.avand.service.PaymentService;
import io.avand.service.PaymentTransactionService;
import io.avand.service.dto.*;
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

    private final PaymentTransactionService paymentTransactionService;

    private final SecurityUtils securityUtils;

    private final ApplicationProperties applicationProperties;

    public PaymentResource(PaymentService paymentService,
                           InvoiceService invoiceService,
                           PaymentTransactionService paymentTransactionService,
                           SecurityUtils securityUtils,
                           ApplicationProperties applicationProperties) {
        this.paymentService = paymentService;
        this.invoiceService = invoiceService;
        this.paymentTransactionService = paymentTransactionService;
        this.securityUtils = securityUtils;
        this.applicationProperties = applicationProperties;
    }

    @PostMapping("/create-payment-token")
    @Timed
    public ResponseEntity createPaymentToken(@RequestBody InvoiceDTO invoiceDTO) {
        logger.debug("REST request to create a zarinpal payment token for a invoice : {}", invoiceDTO);

        Optional<InvoiceDTO> foundInvoice = invoiceService.findOneById(invoiceDTO.getId());

        if (!foundInvoice.isPresent()) {
            throw new ServerErrorException("فاکتور مورد نظر یافت نشد");
        } else {
            PaymentTransactionDTO paymentTransactionDTO = new PaymentTransactionDTO();
            paymentTransactionDTO.setStatus(PaymentTransactionStatus.INITIALIZED);
            paymentTransactionDTO.setAmount(foundInvoice.get().getAmount());
            paymentTransactionDTO.setInvoiceId(foundInvoice.get().getId());
            paymentTransactionDTO.setUserId(foundInvoice.get().getUserId());


            ZarinpalRequestDTO zarinpalRequestDTO = new ZarinpalRequestDTO();

            zarinpalRequestDTO.setAmount((long) foundInvoice.get().getAmount());
            zarinpalRequestDTO.setDescription("user : " + foundInvoice.get().getUserId().toString());

            ResponseEntity responseEntity = paymentService.paymentRequest(zarinpalRequestDTO);
            HashMap<String, String> response = new HashMap<>();

            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                ZarinpalResponseDTO zarinpalResponseDTO = (ZarinpalResponseDTO) responseEntity.getBody();
                if ("100".equals(zarinpalResponseDTO.getStatus())) {
                    response.put("paymentUrl", "https://www.zarinpal.com/pg/pay/" + zarinpalResponseDTO.getAuthority());
                    paymentTransactionDTO.setTrackingCode(zarinpalResponseDTO.getAuthority());
                    try {
                        paymentTransactionService.save(paymentTransactionDTO);
                    } catch (NotFoundException e) {
                        throw new ServerErrorException("Invoice did not found.");
                    }
                    return new ResponseEntity<>(response, HttpStatus.OK);
                } else {
                    throw new ServerErrorException("Cannot do payment right now!");
                }
            } else {
                throw new ServerErrorException("Cannot do payment right now!");
            }
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
            zarinpalVerifyRequestDTO.setAmount((long)foundInvoice.getAmount());

            ZarinpalVerifyResponseDTO zarinpalVerifyResponseDTO = (ZarinpalVerifyResponseDTO) paymentService
                .paymentVerify(zarinpalVerifyRequestDTO).getBody();
            PaymentTransactionDTO paymentTransactionDTO = paymentTransactionService.findOneByTrackingCode(authority);
            if ("OK".equals(status)) {
                if (zarinpalVerifyResponseDTO.getStatus() == 100) {
                    paymentTransactionDTO.setReferenceId(zarinpalVerifyResponseDTO.getRefId());
                    foundInvoice.setStatus(InvoiceStatus.SUCCESS);
                    foundInvoice.setPaymentDate(ZonedDateTime.now());
                    foundInvoice.setPaymentType(PaymentType.ZARINPAL);
                    invoiceService.update(foundInvoice);
                    paymentTransactionService.save(paymentTransactionDTO);
                    try {
                        response.sendRedirect(applicationProperties.getBase().getPanel()+"/#/pages/invoice?id=" + paymentTransactionDTO.getInvoiceId());
                    } catch (IOException e) {
                        throw new ServerErrorException(e.getMessage());
                    }
                }
            } else {
                try {
                    foundInvoice.setStatus(InvoiceStatus.FAILED);
                    foundInvoice.setPaymentDate(ZonedDateTime.now());
                    foundInvoice.setPaymentType(PaymentType.ZARINPAL);
                    invoiceService.update(foundInvoice);
                    response.sendRedirect(applicationProperties.getBase().getPanel() + "/#/pages/invoice?id=" + paymentTransactionDTO.getInvoiceId());
                } catch (IOException e) {
                    throw new ServerErrorException(e.getMessage());
                }
            }
        } else {
            throw new NotFoundException("Sorry, your invoice did not match any!");
        }
    }
}
