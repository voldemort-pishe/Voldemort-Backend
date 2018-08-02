package io.avand.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.avand.domain.enumeration.PaymentTransactionStatus;
import io.avand.security.SecurityUtils;
import io.avand.service.InvoiceService;
import io.avand.service.PaymentService;
import io.avand.service.PaymentTransactionService;
import io.avand.service.dto.InvoiceDTO;
import io.avand.service.dto.PaymentTransactionDTO;
import io.avand.service.dto.ZarinpalRequestDTO;
import io.avand.service.dto.ZarinpalVerifyRequestDTO;
import io.avand.web.rest.errors.ServerErrorException;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/payment")
public class PaymentResource {

    private Logger logger = LoggerFactory.getLogger(PaymentResource.class);

    private final PaymentService paymentService;

    private final InvoiceService invoiceService;

    private final PaymentTransactionService paymentTransactionService;

    private final SecurityUtils securityUtils;

    public PaymentResource(PaymentService paymentService,
                           InvoiceService invoiceService,
                           PaymentTransactionService paymentTransactionService,
                           SecurityUtils securityUtils) {
        this.paymentService = paymentService;
        this.invoiceService = invoiceService;
        this.paymentTransactionService = paymentTransactionService;
        this.securityUtils = securityUtils;
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

            paymentTransactionService.save(paymentTransactionDTO);

            ZarinpalRequestDTO zarinpalRequestDTO = new ZarinpalRequestDTO();

            zarinpalRequestDTO.setAmount((long) foundInvoice.get().getAmount());
            zarinpalRequestDTO.setDescription(foundInvoice.get().getUserId().toString());

            return paymentService.paymentRequest(zarinpalRequestDTO);
        }
    }

    @PostMapping("/callback")
    @Timed
    public ResponseEntity paymentCallback(@RequestParam("Authority") String authority,
                                          @RequestParam("Status") String status) throws NotFoundException {

        System.out.println(status);

        ZarinpalVerifyRequestDTO zarinpalVerifyRequestDTO = new ZarinpalVerifyRequestDTO();
        zarinpalVerifyRequestDTO.setAuthority(authority);
        Optional<InvoiceDTO> invoiceDTO = invoiceService.findOneByUserId(securityUtils.getCurrentUserId());
        if (invoiceDTO.isPresent()) {
            zarinpalVerifyRequestDTO.setAmount((long)invoiceDTO.get().getAmount());

            return paymentService.paymentVerify(zarinpalVerifyRequestDTO);
        } else {
            throw new NotFoundException("Sorry, your invoice did not match any!");
        }
    }
}
