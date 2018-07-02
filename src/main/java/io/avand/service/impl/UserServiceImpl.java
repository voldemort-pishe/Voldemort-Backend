package io.avand.service.impl;

import io.avand.domain.AuthorityEntity;
import io.avand.domain.UserAuthorityEntity;
import io.avand.domain.UserEntity;
import io.avand.domain.UserPermissionEntity;
import io.avand.domain.enumeration.PermissionAction;
import io.avand.repository.AuthorityRepository;
import io.avand.repository.UserRepository;
import io.avand.security.AuthoritiesConstants;
import io.avand.service.UserService;
import io.avand.service.dto.UserDTO;
import io.avand.service.mapper.UserMapper;
import io.avand.service.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           AuthorityRepository authorityRepository,
                           UserMapper userMapper,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDTO save(String login, String firstName, String lastName, String email, String password) {
        log.debug("Request to save user : {}, {}, {}, {}, {}", login, firstName, lastName, email, password);
        Optional<UserEntity> userEntityOptional = userRepository.findByLogin(login);
        UserEntity userEntity;
        if (userEntityOptional.isPresent()) {
            userEntity = userEntityOptional.get();
            userEntity.setFirstName(firstName);
            userEntity.setLastName(lastName);
            userEntity.setEmail(email);
        } else {
            userEntity = new UserEntity();
            userEntity.setLogin(login);
            userEntity.setPasswordHash(passwordEncoder.encode(password));
            userEntity.setFirstName(firstName);
            userEntity.setLastName(lastName);
            userEntity.setEmail(email);
            userEntity.setActivationKey(RandomUtil.generateActivationKey());
            userEntity.setActivated(false);

            UserAuthorityEntity userAuthorityEntity = new UserAuthorityEntity();
            UserPermissionEntity userPermissionEntity = new UserPermissionEntity();
            userPermissionEntity.setAction(PermissionAction.FULL);
            userPermissionEntity.setUserAuthority(userAuthorityEntity);

            Set<UserPermissionEntity> userPermissionEntities = new HashSet<>();
            userPermissionEntities.add(userPermissionEntity);

            AuthorityEntity authorityEntity = authorityRepository.findByName(AuthoritiesConstants.USER);

            userAuthorityEntity.setAuthorityName(authorityEntity.getName());
            userAuthorityEntity.setUserPermissions(userPermissionEntities);
            userAuthorityEntity.setUser(userEntity);

            Set<UserAuthorityEntity> userAuthorityEntities = new HashSet<>();
            userAuthorityEntities.add(userAuthorityEntity);

            userEntity.setUserAuthorities(userAuthorityEntities);
        }

        userEntity = userRepository.save(userEntity);

        return userMapper.toDto(userEntity);
    }

    @Override
    public UserDTO update(UserDTO userDTO) {
        log.debug("Request to update user : {}", userDTO);
        UserEntity userEntity = userMapper.toEntity(userDTO);
        userEntity = userRepository.save(userEntity);

        return userMapper.toDto(userEntity);
    }

    @Override
    public Optional<UserDTO> findById(Long id) {
        log.debug("Request to find user by id : {}", id);
        return userRepository.findById(id)
            .map(userMapper::toDto);
    }

    @Override
    public Optional<UserDTO> findByLogin(String login) {
        log.debug("Request to find user by login : {}", login);
        return userRepository.findByLogin(login)
            .map(userMapper::toDto);
    }

    @Override
    public List<UserDTO> findAll() {
        return userRepository.findAll()
            .stream()
            .map(userMapper::toDto)
            .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Optional<UserDTO> requestToResetPassword(String email) {
        return null;
    }

    @Override
    public Optional<UserDTO> completeResetPassword(String resetKey, String newPassword) {
        return null;
    }

    @Override
    public Optional<UserDTO> activateUser(String login, String activationKey) {
        return null;
    }

    @Override
    public Optional<UserDTO> changePassword(String login, String oldPassword, String newPassword) {
        return null;
    }
}
