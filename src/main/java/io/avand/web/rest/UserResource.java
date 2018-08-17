package io.avand.web.rest;

import com.codahale.metrics.annotation.Timed;

import io.avand.security.SecurityUtils;
import io.avand.service.UserService;
import io.avand.service.dto.UserDTO;
import io.avand.web.rest.errors.ServerErrorException;
import io.avand.web.rest.vm.UserVM;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * REST controller for managing UserEntity.
 */
@RestController
@RequestMapping("/api/user")
public class UserResource {

    private final Logger log = LoggerFactory.getLogger(UserResource.class);

    private final UserService userService;

    private final SecurityUtils securityUtils;

    public UserResource(UserService userService,
                        SecurityUtils securityUtils) {
        this.userService = userService;
        this.securityUtils = securityUtils;
    }

    /**
     * GET  /user: get the  userEntity.
     *
     * @return the ResponseEntity with status 200 (OK) and with body the userEntity, or with status 404 (Not Found)
     */
    @GetMapping
    @Timed
    public ResponseEntity<UserVM> getUserEntity() {
        log.debug("REST request to get User");
        try {
            Optional<UserDTO> userDTO = userService.findById(securityUtils.getCurrentUserId());
            if (userDTO.isPresent()) {
                UserVM userVM = new UserVM();
                userVM.setName(userDTO.get().getFirstName());
                userVM.setLastName(userDTO.get().getLastName());
                userVM.setEmail(userDTO.get().getEmail());
                return new ResponseEntity<>(userVM, HttpStatus.OK);
            } else {
                throw new ServerErrorException("User Not Found");
            }
        } catch (NotFoundException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

}
