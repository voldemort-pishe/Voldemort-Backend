package io.avand.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.avand.service.UserStateService;
import io.avand.service.dto.UserStateDTO;
import io.avand.web.rest.errors.ServerErrorException;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user-state")
public class UserStateResource {
    private final Logger log = LoggerFactory.getLogger(UserStateResource.class);
    private final UserStateService userStateService;

    public UserStateResource(UserStateService userStateService) {
        this.userStateService = userStateService;
    }

    @PostMapping
    @Timed
    public ResponseEntity<UserStateDTO> create(@RequestBody @Valid UserStateDTO userStateDTO) {
        log.debug("REST Request to save userState : {}", userStateDTO);
        try {
            userStateDTO = userStateService.save(userStateDTO);
            return new ResponseEntity<>(userStateDTO, HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }
}
