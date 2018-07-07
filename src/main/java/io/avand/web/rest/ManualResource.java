package io.avand.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.avand.security.AuthoritiesConstants;
import io.avand.service.PaymentService;
import io.avand.service.dto.ZarinpalRequestDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/manual")
public class ManualResource {

    private final Logger logger = LoggerFactory.getLogger(ManualResource.class);

    private final PaymentService paymentService;

    public ManualResource(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/payment-test")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity paymentTest(@RequestBody Long amount) {
        logger.debug("REST request for manual payment : {}", amount);

        ZarinpalRequestDTO zarinpalRequestDTO = new ZarinpalRequestDTO();

        zarinpalRequestDTO.setAmount(amount);
        zarinpalRequestDTO.setDescription("MorteZA Raees Dana Test");

        return paymentService.paymentRequest(zarinpalRequestDTO);
    }
}
