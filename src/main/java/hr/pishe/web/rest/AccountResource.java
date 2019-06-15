package hr.pishe.web.rest;

import com.codahale.metrics.annotation.Timed;
import hr.pishe.security.SecurityUtils;
import hr.pishe.security.jwt.JWTConfigurer;
import hr.pishe.service.UserAuthorityService;
import hr.pishe.service.UserService;
import hr.pishe.service.dto.TokenDTO;
import hr.pishe.service.dto.UserDTO;
import hr.pishe.web.rest.errors.ServerErrorConstants;
import hr.pishe.web.rest.errors.ServerErrorException;
import hr.pishe.web.rest.errors.ServerMessage;
import hr.pishe.web.rest.vm.*;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/account")
public class AccountResource {

    private final Logger log = LoggerFactory.getLogger(AccountResource.class);
    private final UserService userService;
    private final UserAuthorityService userAuthorityService;
    private final SecurityUtils securityUtils;

    public AccountResource(UserService userService,
                           UserAuthorityService userAuthorityService,
                           SecurityUtils securityUtils) {
        this.userService = userService;
        this.userAuthorityService = userAuthorityService;
        this.securityUtils = securityUtils;
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid UserRegisterVM userRegisterVM) {
        log.debug("REST Request to register user : {}", userRegisterVM);
        Optional<UserDTO> userDTOOptional = userService.findByLogin(userRegisterVM.getEmail());
        if (userDTOOptional.isPresent()) {
            throw new ServerErrorException(ServerErrorConstants.EMAIL_ALREADY_EXIST);
        } else {
            userService
                .save(
                    userRegisterVM.getEmail(),
                    userRegisterVM.getFirstName(),
                    userRegisterVM.getLastName(),
                    userRegisterVM.getEmail(),
                    userRegisterVM.getPassword(),
                    userRegisterVM.getCellphone()
                );

            ServerMessage serverMessage = new ServerMessage();
            serverMessage.setMessage("حساب کاربری شما با موفقیت ایجاد شد، کد تایید برای شما ارسال شد.");
            return new ResponseEntity<>(serverMessage, HttpStatus.OK);
        }
    }

    @PostMapping("/register-by-invite")
    public ResponseEntity registerByInvite(@RequestBody @Valid UserRegisterInviteVM userRegisterInviteVM) {
        log.debug("REST Request to register user : {}", userRegisterInviteVM);

        Optional<UserDTO> userFound = userService.findByInvitationKey(userRegisterInviteVM.getInvitationKey());

        if (userFound.isPresent()) {
            try {
                userService
                    .saveActive(
                        userFound.get().getEmail(),
                        userRegisterInviteVM.getFirstName(),
                        userRegisterInviteVM.getLastName(),
                        userFound.get().getEmail(),
                        userRegisterInviteVM.getPassword(),
                        userRegisterInviteVM.getCellphone(),
                        true
                    );
                ServerMessage serverMessage = new ServerMessage();
                serverMessage.setMessage("اطلاعات شما با موفقیت ثبت شد");
                return new ResponseEntity<>(serverMessage, HttpStatus.OK);
            }catch (NotFoundException e){
                throw new ServerErrorException("مشکلی در ثبت اطلاعات بوجود آمده است لطفا مجدد تلاش نمایید");
            }
        } else {
            throw new ServerErrorException(ServerErrorConstants.USER_NOT_FOUND);
        }
    }

    @GetMapping("/activate/{activation-key}")
    public ResponseEntity activate(@PathVariable("activation-key") String activationKey,
                                   HttpServletResponse response,
                                   HttpServletRequest request) throws IOException {
        log.debug("REST Request to activate user by activationKey : {}", activationKey);
        try {
            UserDTO activatedUser = userService.activate(activationKey);
            TokenDTO tokenDTO = userService.authorizeWithoutPassword(activatedUser);
            response.addHeader(JWTConfigurer.AUTHORIZATION_HEADER, "Bearer " + tokenDTO.getToken());
            return new ResponseEntity<>(tokenDTO, HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

    @PostMapping("/activate")
    public ResponseEntity resendActivationEmail(@RequestBody @Valid UserActivationVM activationVM) {
        log.debug("REST Request to resend activation email : {}", activationVM);
        try {
            userService.requestToResendActivationEmail(activationVM.getEmail());
            ServerMessage serverMessage = new ServerMessage();
            serverMessage.setMessage("پیام فعال سازی حساب برای شما ارسال گردید.");
            return new ResponseEntity<>(serverMessage, HttpStatus.OK);
        } catch (NotFoundException | IllegalStateException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }


    @PostMapping("/authenticate")
    public ResponseEntity authorize(@Valid @RequestBody UserLoginVM userLoginVM,
                                    HttpServletResponse response,
                                    HttpServletRequest request) {
        log.debug("REST Request to authorize user : {}", userLoginVM);
        try {
            TokenDTO tokenDTO = userService.authorize(userLoginVM.getEmail(), userLoginVM.getPassword(), userLoginVM.isRememberMe());
            response.addHeader(JWTConfigurer.AUTHORIZATION_HEADER, "Bearer " + tokenDTO.getToken());
            return new ResponseEntity<>(tokenDTO, HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

    /**
     * GET  /account : get the current user.
     *
     * @return the current user
     * @throws RuntimeException 500 (Internal Server Error) if the user couldn't be returned
     */
    @GetMapping
    @Timed
    public ResponseEntity<UserVM> getUser() {
        log.debug("REST request to get User");
        try {
            Optional<UserDTO> userDTOOptional = userService.findById(securityUtils.getCurrentUserId());
            if (userDTOOptional.isPresent()) {
                UserDTO userDTO = userDTOOptional.get();
                UserVM userVM = new UserVM();
                userVM.setId(userDTO.getId());
                userVM.setFirstName(userDTO.getFirstName());
                userVM.setLastName(userDTO.getLastName());
                userVM.setEmail(userDTO.getEmail());
                userVM.setCellphone(userDTO.getCellphone());
                userVM.setFileId(userDTO.getFileId());

                List<UserAuthorityVM> userAuthorityVMS = userAuthorityService.findByUserId(securityUtils.getCurrentUserId());
                userVM.setAuthorities(userAuthorityVMS);

                return new ResponseEntity<>(userVM, HttpStatus.OK);
            } else {
                throw new SecurityException("Login First");
            }
        } catch (NotFoundException e) {
            throw new ServerErrorException(e.getMessage());
        }

    }

    @PostMapping("/change-password")
    public ResponseEntity changePassword(@RequestBody @Valid UserChangePasswordVM changePasswordVM) {
        log.debug("REST Request to change password");
        Optional<String> login = SecurityUtils.getCurrentUserLogin();
        if (login.isPresent()) {
            try {
                userService.changePassword(login.get(), changePasswordVM.getOldPassword(), changePasswordVM.getNewPassword());
                return new ResponseEntity(HttpStatus.OK);
            } catch (NotFoundException e) {
                throw new ServerErrorException(e.getMessage());
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/reset-password/init")
    public ResponseEntity requestToInitResetPassword(@RequestBody @Valid UserResetPasswordInitVM resetPasswordInitVM) {
        log.debug("REST Request to init reset password by : {}", resetPasswordInitVM);
        try {
            userService.requestToResetPassword(resetPasswordInitVM.getEmail());
            ServerMessage serverMessage = new ServerMessage();
            serverMessage.setMessage("برای ادامه فرآیند بازیابی رمز عبور به پست الکترونیکی خود مراجعه کنید.");
            return new ResponseEntity<>(serverMessage, HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

    @PostMapping("/reset-password/finish")
    public ResponseEntity requestToFinishResetPassword(@RequestBody @Valid UserResetPasswordFinishVM resetPasswordFinishVM) {
        log.debug("REST Request to finish reset password by : {}", resetPasswordFinishVM);
        try {
            userService.completeResetPassword(resetPasswordFinishVM.getKey(), resetPasswordFinishVM.getPassword());
            ServerMessage serverMessage = new ServerMessage();
            serverMessage.setMessage("عملیات تغییر رمز عبور با موفقیت انجام شد");
            return new ResponseEntity<>(serverMessage, HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ServerErrorException(e.getMessage());
        }
    }

    @GetMapping("/logout")
    public ResponseEntity logout() {
        return new ResponseEntity(HttpStatus.OK);
    }
}
