package io.avand.service;

import io.avand.service.dto.TokenDTO;
import io.avand.service.dto.UserDTO;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {

    UserDTO save(String login,String firstName,String lastName,String email,String password);

    UserDTO saveActive(String login,String firstName,String lastName,String email,String password, Boolean activated);

    UserDTO update(UserDTO userDTO) throws NotFoundException;

    Optional<UserDTO> findById(Long id);

    Optional<UserDTO> findByLogin(String login);

    Optional<UserDTO> findByActivationKey(String activationKey);

    Optional<UserDTO> findByInvitationKey(String invitationKey);

    Page<UserDTO> findAll(Pageable pageable);

    void delete(Long id);

    void requestToResendActivationEmail(String email) throws NotFoundException;

    void requestToResetPassword(String email) throws NotFoundException;

    void completeResetPassword(String resetKey,String newPassword) throws NotFoundException;

    UserDTO activate(String activationKey) throws NotFoundException;

    TokenDTO authorize(String username,String password,Boolean isRemember) throws NotFoundException;

    void changePassword(String login,String oldPassword,String newPassword) throws NotFoundException;

}
