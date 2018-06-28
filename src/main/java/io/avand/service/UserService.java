package io.avand.service;

import io.avand.service.dto.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {

    UserDTO save(String login,String firstName,String lastName,String email,String password);

    Optional<UserDTO> findById(Long id);

    Optional<UserDTO> findByLogin(String login);

    List<UserDTO> findAll();

    void delete(Long id);

    Optional<UserDTO> requestToResetPassword(String email);

    Optional<UserDTO> completeResetPassword(String resetKey,String newPassword);

    Optional<UserDTO> activateUser(String login,String activationKey);

    Optional<UserDTO> changePassword(String login,String oldPassword,String newPassword);

}
