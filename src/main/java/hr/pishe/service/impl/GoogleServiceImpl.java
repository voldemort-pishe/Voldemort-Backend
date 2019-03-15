package hr.pishe.service.impl;

import hr.pishe.service.GoogleService;
import hr.pishe.service.dto.GoogleSearchDTO;
import hr.pishe.service.dto.GoogleSearchItemDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GoogleServiceImpl implements GoogleService {

    private final Logger log = LoggerFactory.getLogger(GoogleServiceImpl.class);

    private final RestTemplate restTemplate;

    private final String API_KEY = "AIzaSyD2qSwYhPy1-JJoiSE94Dpyneklfx1Zz0M";
    private final String SEARCH_ENGINE_ID = "018233006496906383928:jjvmxlghxgm";

    public GoogleServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Map<String, List<GoogleSearchItemDTO>> search(String query) {
        log.debug("Request to search via google : {}", query);

        String url = "https://www.googleapis.com/customsearch/v1"
            + "?key=" + API_KEY
            + "&cx=" + SEARCH_ENGINE_ID
            + "&fields=items(htmlTitle,link,htmlSnippet)"
            + "&q=" + query;

        ResponseEntity<GoogleSearchDTO> responseEntity = restTemplate
            .getForEntity(
                url,
                GoogleSearchDTO.class);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            GoogleSearchDTO googleSearchDTO = responseEntity.getBody();
            List<GoogleSearchItemDTO> linkedin = new ArrayList<>();
            List<GoogleSearchItemDTO> github = new ArrayList<>();
            List<GoogleSearchItemDTO> instagram = new ArrayList<>();
            List<GoogleSearchItemDTO> facebook = new ArrayList<>();
            List<GoogleSearchItemDTO> twitter = new ArrayList<>();
            for (GoogleSearchItemDTO item : googleSearchDTO.getItems()) {
                if (item.getLink().contains("linkedin")) {
                    linkedin.add(item);
                } else if (item.getLink().contains("github")) {
                    github.add(item);
                } else if (item.getLink().contains("instagram")) {
                    instagram.add(item);
                } else if (item.getLink().contains("facebook")) {
                    facebook.add(item);
                } else if (item.getLink().contains("twitter")) {
                    twitter.add(item);
                }
            }
            Map<String, List<GoogleSearchItemDTO>> items = new HashMap<>();
            items.put("linkedin", linkedin);
            items.put("github", github);
            items.put("instagram", instagram);
            items.put("facebook", facebook);
            items.put("twitter", twitter);

            return items;
        } else {
            return null;
        }
    }
}
