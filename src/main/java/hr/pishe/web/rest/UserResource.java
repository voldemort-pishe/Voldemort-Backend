package hr.pishe.web.rest;

import com.codahale.metrics.annotation.Timed;
import hr.pishe.security.SecurityUtils;
import hr.pishe.service.UserAuthorityService;
import hr.pishe.service.UserService;
import hr.pishe.service.dto.UserDTO;
import hr.pishe.service.mapper.UserMapper;
import hr.pishe.web.rest.component.UserComponent;
import hr.pishe.web.rest.errors.ServerErrorException;
import hr.pishe.web.rest.util.HeaderUtil;
import hr.pishe.web.rest.vm.response.ResponseVM;
import hr.pishe.web.rest.vm.response.UserIncludeVM;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;

import static org.hibernate.id.IdentifierGenerator.ENTITY_NAME;

/**
 * REST controller for managing UserEntity.
 */
@RestController
@RequestMapping("/api/user")
public class UserResource {

    private final Logger log = LoggerFactory.getLogger(UserResource.class);

    private final UserService userService;

    private final UserAuthorityService userAuthorityService;

    private final UserComponent userComponent;

    private final SecurityUtils securityUtils;

    private final UserMapper userMapper;

    public UserResource(UserService userService,
                        UserAuthorityService userAuthorityService,
                        UserComponent userComponent,
                        SecurityUtils securityUtils,
                        UserMapper userMapper) {
        this.userService = userService;
        this.userAuthorityService = userAuthorityService;
        this.userComponent = userComponent;
        this.securityUtils = securityUtils;
        this.userMapper = userMapper;
    }

    /**
     * PUT  /user-entities : Updates an existing userEntity.
     *
     * @param userVM the userEntity to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userEntity,
     * or with status 400 (Bad Request) if the userEntity is not valid,
     * or with status 500 (Internal Server Error) if the userEntity couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping
    @Timed
    public ResponseEntity<ResponseVM<UserDTO>> updateUser(@RequestBody UserIncludeVM userVM) throws URISyntaxException {
        log.debug("REST request to update UserEntity : {}", userVM);
        if (userVM.getId() == null) {
            throw new ServerErrorException("UserId Not Available for update");
        }
        UserDTO userDTO = userMapper.vmToDto(userVM);
        try {
            ResponseVM<UserDTO> result = userComponent.save(userDTO);
            return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, result.getData().getId().toString()))
                .body(result);
        } catch (NotFoundException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

    /**
     * GET  /user: get the  userEntity.
     *
     * @return the ResponseEntity with status 200 (OK) and with body the userEntity, or with status 404 (Not Found)
     */
    @GetMapping
    @Timed
    public ResponseEntity<ResponseVM<UserDTO>> getUser() {
        log.debug("REST request to get User");
        try {
            ResponseVM<UserDTO> userDTO = userComponent.findById(securityUtils.getCurrentUserId());
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

}
