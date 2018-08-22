package io.avand.web.rest;

import io.avand.security.AuthoritiesConstants;
import io.avand.service.ProvinceService;
import io.avand.service.dto.ProvinceDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/province")
@Secured(AuthoritiesConstants.SUBSCRIPTION)
public class ProvinceResource {

    private final Logger log = LoggerFactory.getLogger(ProvinceResource.class);
    private final ProvinceService provinceService;

    public ProvinceResource(ProvinceService provinceService) {
        this.provinceService = provinceService;
    }

    @GetMapping
    public ResponseEntity getAllProvince() {
        log.debug("REST Request to get all province");
        List<ProvinceDTO> provinceDTOS = provinceService.findAll();
        return new ResponseEntity<>(provinceDTOS, HttpStatus.OK);
    }

    @GetMapping("/name")
    public ResponseEntity getProvinceByName(@RequestParam("name") String name) {
        log.debug("REST Request to get province by name : {}", name);
        return new ResponseEntity<>(provinceService.findByName(name), HttpStatus.OK);
    }

}
