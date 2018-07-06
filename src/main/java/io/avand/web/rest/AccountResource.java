package io.avand.web.rest;

import io.avand.security.SecurityUtils;
import io.avand.security.jwt.JWTConfigurer;
import io.avand.service.UserService;
import io.avand.service.dto.TokenDTO;
import io.avand.service.dto.UserDTO;
import io.avand.web.rest.errors.ServerErrorConstants;
import io.avand.web.rest.errors.ServerErrorException;
import io.avand.web.rest.errors.ServerMessage;
import io.avand.web.rest.vm.*;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
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
                    userRegisterVM.getPassword()
                );

            ServerMessage serverMessage = new ServerMessage();
            serverMessage.setMessage("حساب کاربری شما با موفقیت ایجاد شد، جهت تایید حساب کاربری خود یک پیغام به آدرس پست الکترونیکی شما ارسال شد.");
            return new ResponseEntity<>(serverMessage, HttpStatus.OK);
        }
    }

    @GetMapping("/activate/{activation-key}")
    public ResponseEntity activate(@PathVariable("activation-key") String activationKey,
                                   HttpServletResponse response,
                                   HttpServletRequest request) {
        log.debug("REST Request to activate user by activationKey : {}", activationKey);
        try {
            TokenDTO tokenDTO = userService.activate(activationKey);
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
            serverMessage.setMessage("پیام فعال سازی حساب به ایمیل شما ارسال گردید.");
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
            TokenDTO tokenDTO = userService.authorize(userLoginVM.getUsername(), userLoginVM.getPassword(), userLoginVM.isRememberMe());
            response.addHeader(JWTConfigurer.AUTHORIZATION_HEADER, "Bearer " + tokenDTO.getToken());
            return new ResponseEntity<>(tokenDTO, HttpStatus.OK);
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
}
