package io.avand.service.impl;

import io.avand.service.CloudflareService;
import io.avand.service.dto.CloudflareRequestDTO;
import io.avand.service.dto.CloudflareResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class CloudflareServiceImpl implements CloudflareService {

    private final Logger log = LoggerFactory.getLogger(CloudflareServiceImpl.class);
    private final RestTemplate restTemplate;

    public CloudflareServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private final String ZONE_ID = "08b90316fb474eac3054040dc54a0e4b";
    private final String API_KEY = "e755afbbea1b14a5b570539f1b39a18935c7c";
    private final String BASE_URL = "https://api.cloudflare.com/client/v4/";

    @Override
    public void createDNSRecord(CloudflareRequestDTO requestDTO) {
        log.debug("Request to create dns record : {}", requestDTO);

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("X-Auth-Email", "parham.contact@gmail.com");
        headers.add("X-Auth-Key", API_KEY);
        headers.add("Content-Type", "application/json");

        HttpEntity<CloudflareRequestDTO> httpEntity = new HttpEntity<>(requestDTO, headers);

        ResponseEntity<CloudflareResponseDTO> responseEntity = restTemplate
            .exchange(BASE_URL + "zones/" + ZONE_ID + "/dns_records",
                HttpMethod.POST,
                httpEntity,
                CloudflareResponseDTO.class);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            if (!responseEntity.getBody().getSuccess())
                throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "DNS Record Don't Create");
        } else {
            throw new HttpClientErrorException(responseEntity.getStatusCode());
        }
    }
}
