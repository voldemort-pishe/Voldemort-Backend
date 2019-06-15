package hr.pishe.service.impl;

import hr.pishe.service.CloudflareService;
import hr.pishe.service.dto.CloudflareRequestDTO;
import hr.pishe.service.dto.CloudflareResponseDTO;
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

    private final String ZONE_ID = "3b1af3d36f68a3379a11b0235a590525";
    private final String API_KEY = "011f1fcd6c189c2432380bce939be0defcdeb";
    private final String BASE_URL = "https://api.cloudflare.com/client/v4/";

    @Override
    public void createDNSRecord(CloudflareRequestDTO requestDTO) {
        log.debug("Request to create dns record : {}", requestDTO);

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("X-Auth-Email", "i@ehdi.in");
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
