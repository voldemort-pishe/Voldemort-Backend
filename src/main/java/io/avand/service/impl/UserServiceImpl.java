package io.avand.service.impl;

import io.avand.domain.AuthorityEntity;
import io.avand.domain.UserAuthorityEntity;
import io.avand.domain.UserEntity;
import io.avand.domain.UserPermissionEntity;
import io.avand.domain.enumeration.PermissionAction;
import io.avand.repository.AuthorityRepository;
import io.avand.repository.UserRepository;
import io.avand.security.AuthoritiesConstants;
import io.avand.security.UserNotActivatedException;
import io.avand.security.jwt.TokenProvider;
import io.avand.service.MailService;
import io.avand.service.UserService;
import io.avand.service.dto.TokenDTO;
import io.avand.service.dto.UserDTO;
import io.avand.service.mapper.UserMapper;
import io.avand.service.util.RandomUtil;
import io.avand.web.rest.errors.ServerErrorException;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    private final TokenProvider tokenProvider;

    private final AuthenticationManager authenticationManager;

    private final MailService mailService;

    public UserServiceImpl(UserRepository userRepository,
                           AuthorityRepository authorityRepository,
                           UserMapper userMapper,
                           PasswordEncoder passwordEncoder,
                           TokenProvider tokenProvider,
                           AuthenticationManager authenticationManager,
                           MailService mailService) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
        this.mailService = mailService;
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

        mailService.sendActivationEmail(userEntity);

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
        log.debug("Request to delete user : {}", id);
        userRepository.delete(id);
    }

    @Override
    public void requestToResendActivationEmail(String email) throws NotFoundException {
        log.debug("Request to resend activation email : {}", email);
        Optional<UserEntity> userOptional = userRepository.findByLogin(email);
        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();

            if (!user.isActivated()) {
                user.setActivated(false);
                user.setActivationKey(RandomUtil.generateActivationKey());
                user = userRepository.save(user);

                mailService.sendActivationEmail(user);
            } else {
                throw new IllegalStateException("User Is Active");
            }
        } else {
            throw new NotFoundException("User Not Available");
        }
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
    public TokenDTO activate(String activationKey) throws NotFoundException {
        log.debug("Request to activate user by activationKey : {}", activationKey);
        Optional<UserEntity> userOptional = userRepository.findByActivationKey(activationKey);
        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            if (!user.isActivated()) {
                user.setActivated(true);
                user.setActivationKey(null);
                user = userRepository.save(user);
                return authorize(user.getLogin(), user.getPasswordHash(), true);
            } else {
                throw new IllegalStateException("User Is Active");
            }
        } else {
            throw new NotFoundException("User Not Found By Activation Key");
        }
    }

    @Override
    public TokenDTO authorize(String username, String password, Boolean isRemember) throws NotFoundException {
        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(username, password);
        try {
            Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            boolean rememberMe = (isRemember == null) ? false : isRemember;
            String jwt = tokenProvider.createToken(authentication, rememberMe);
            TokenDTO tokenDTO = new TokenDTO();
            tokenDTO.setToken(jwt);
            return tokenDTO;
        } catch (AuthenticationException ae) {
            throw new NotFoundException("اطلاعات کاربری صحیح نمی باشد");
        }
    }

    @Override
    public void changePassword(String login, String oldPassword, String newPassword) throws NotFoundException {
        log.debug("Request to change password by login : {}", login);
        Optional<UserEntity> userOptional = userRepository.findByLogin(login);
        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            if (passwordEncoder.matches(oldPassword, user.getPasswordHash())) {
                user.setPasswordHash(passwordEncoder.encode(newPassword));
                userRepository.save(user);
            } else {
                throw new ServerErrorException("پسورد وارد شده اشتباه است");
            }
        } else {
            throw new NotFoundException("User Not Found");
        }
    }
}
