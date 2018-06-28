package io.avand.web.rest;

import io.avand.service.UserService;
import io.avand.service.dto.UserDTO;
import io.avand.web.rest.errors.ServerErrorConstants;
import io.avand.web.rest.errors.ServerErrorException;
import io.avand.web.rest.vm.UserVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/account")
public class AccountResource {

    private final Logger log = LoggerFactory.getLogger(AccountResource.class);
    private final UserService userService;

    public AccountResource(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Validated UserVM userVM) {
        log.debug("REST Request to register user : {}", userVM);
        Optional<UserDTO> userDTOOptional = userService.findByLogin(userVM.getEmail());
        if (userDTOOptional.isPresent()){
            throw new ServerErrorException(ServerErrorConstants.EMAIL_ALREADY_EXIST);
        }else {
            UserDTO userDTO = userService
                .save(
                    userVM.getEmail(),
                    userVM.getFirstName(),
                    userVM.getLastName(),
                    userVM.getEmail(),
                    userVM.getPassword()
                );
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        }
    }
}
