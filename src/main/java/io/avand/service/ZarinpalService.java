package io.avand.service;

import io.avand.config.ApplicationProperties;
import io.avand.service.dto.ZarinpalRequestDTO;
import io.avand.service.dto.ZarinpalResponseDTO;
import io.avand.service.dto.ZarinpalVerifyRequestDTO;
import io.avand.service.dto.ZarinpalVerifyResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class ZarinpalService {

    private final Logger logger = LoggerFactory.getLogger(ZarinpalService.class);

    private final static String API_URL = "https://www.zarinpal.com/pg/rest/WebGate/";

    private final static String MERCHANT_ID = "dc28de2a-6553-11e7-9504-005056a205be";

    private final ApplicationProperties applicationProperties;

    public ZarinpalService(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    public ResponseEntity paymentRequest(ZarinpalRequestDTO zarinpalRequestDTO) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", "application/json");
        headers.add("Content-Type", "application/json");
        headers.add("User-Agent", "ZarinPal Rest Api v1");

        zarinpalRequestDTO.setMerchantId(MERCHANT_ID);

        if(zarinpalRequestDTO.getCallbackURL() == null) {
            logger.debug(applicationProperties.getBase().getUrl());
            zarinpalRequestDTO.setCallbackURL(applicationProperties.getBase().getUrl() + "api/payment-callback");
        }

        String amountString = zarinpalRequestDTO.getAmount().toString();
        String removeLeadingZero = amountString.substring(0, amountString.length() -1);
        zarinpalRequestDTO.setAmount(Long.parseLong(removeLeadingZero));

        HttpEntity<ZarinpalRequestDTO> request = new HttpEntity<>(zarinpalRequestDTO,headers);

        try {
            return restTemplate.exchange(API_URL+"PaymentRequest.json", HttpMethod.POST, request, ZarinpalResponseDTO.class);
        }catch (HttpStatusCodeException e){
            Map<String,String> response = new HashMap<>();
            response.put("status", e.getStatusText());
            response.put("message", e.getResponseBodyAsString());
            return new ResponseEntity<Object>(response,e.getStatusCode());
        }
    }

    public ResponseEntity paymentVerify(ZarinpalVerifyRequestDTO zarinpalVerifyRequestDTO){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", "application/json");
        headers.add("Content-Type", "application/json");
        headers.add("User-Agent", "ZarinPal Rest Api v1");

        String amountString = zarinpalVerifyRequestDTO.getAmount().toString();
        String removeLeadingZero = amountString.substring(0, amountString.length() -1);

        zarinpalVerifyRequestDTO.setAmount(Long.parseLong(removeLeadingZero));
        zarinpalVerifyRequestDTO.setMerchantId(MERCHANT_ID);
        HttpEntity<ZarinpalVerifyRequestDTO> request = new HttpEntity<>(zarinpalVerifyRequestDTO,headers);
//callback status option :
//    OK
//    NOK

        try {
            return restTemplate.exchange(API_URL+"PaymentVerification.json", HttpMethod.POST, request, ZarinpalVerifyResponseDTO.class);
        }catch (HttpStatusCodeException e){
            Map<String,String> response = new HashMap<>();
            response.put("status", e.getStatusText());
            response.put("message", e.getResponseBodyAsString());
            return new ResponseEntity<Object>(response,e.getStatusCode());
        }

    }

}
