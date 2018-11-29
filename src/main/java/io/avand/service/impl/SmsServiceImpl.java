package io.avand.service.impl;

import io.avand.service.SmsService;
import io.avand.service.dto.SmsSendRequestDTO;
import io.avand.service.dto.SmsSendResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


@Service
public class SmsServiceImpl implements SmsService {

    private final Logger log = LoggerFactory.getLogger(SmsServiceImpl.class);
    private final RestTemplate restTemplate;

    private final String API_KEY = "4F426F766F584A2F4155385A6D7177713345414230436E3465447749472F4D79";
    private final String BASE_URL = "https://api.kavenegar.com/v1/" + API_KEY;

    public SmsServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Boolean send(SmsSendRequestDTO requestDTO) {
        log.debug("Request to send sms : {}", requestDTO);
        requestDTO.setTemplate("AvandVerify");
        HttpEntity<SmsSendRequestDTO> httpEntity = new HttpEntity<>(requestDTO);
        ResponseEntity<SmsSendResponseDTO> responseEntity = restTemplate
            .exchange(
                BASE_URL + "/verify/lookup.json",
                HttpMethod.POST,
                httpEntity,
                SmsSendResponseDTO.class
            );
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            SmsSendResponseDTO smsSendResponseDTO = responseEntity.getBody();
            return smsSendResponseDTO.getStatus() == 1 ||
                smsSendResponseDTO.getStatus() == 2 ||
                smsSendResponseDTO.getStatus() == 4 ||
                smsSendResponseDTO.getStatus() == 5 ||
                smsSendResponseDTO.getStatus() == 10;
        } else {
            throw new HttpClientErrorException(responseEntity.getStatusCode());
        }
    }
}
