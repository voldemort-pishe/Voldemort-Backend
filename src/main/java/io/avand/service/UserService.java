package io.avand.service;

import io.avand.domain.UserEntity;
import io.avand.service.dto.TokenDTO;
import io.avand.service.dto.UserDTO;
import javassist.NotFoundException;

import java.util.List;
import java.util.Optional;

public interface UserService {

    UserDTO save(String login,String firstName,String lastName,String email,String password);

    Optional<UserDTO> findById(Long id);

    Optional<UserDTO> findByLogin(String login);

    List<UserDTO> findAll();

    void delete(Long id);

    void requestToResendActivationEmail(String email) throws NotFoundException;

    Optional<UserDTO> requestToResetPassword(String email);

    Optional<UserDTO> completeResetPassword(String resetKey,String newPassword);

    TokenDTO activate(String activationKey) throws NotFoundException;

    TokenDTO authorize(String username,String password,Boolean isRemember) throws NotFoundException;

    Optional<UserDTO> changePassword(String login,String oldPassword,String newPassword);

}
