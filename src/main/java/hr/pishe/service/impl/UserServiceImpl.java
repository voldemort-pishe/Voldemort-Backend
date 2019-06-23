package hr.pishe.service.impl;

import hr.pishe.domain.entity.jpa.FileEntity;
import hr.pishe.domain.entity.jpa.UserEntity;
import hr.pishe.domain.enumeration.UserStateType;
import hr.pishe.repository.jpa.FileRepository;
import hr.pishe.repository.jpa.UserRepository;
import hr.pishe.security.AuthoritiesConstants;
import hr.pishe.security.SecurityUtils;
import hr.pishe.service.*;
import hr.pishe.service.dto.TokenDTO;
import hr.pishe.service.dto.UserDTO;
import hr.pishe.service.mapper.UserMapper;
import hr.pishe.service.util.RandomUtil;
import hr.pishe.web.rest.errors.ServerErrorException;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;
    private final TokenService tokenService;
    private final FileRepository fileRepository;
    private final UserAuthorityService userAuthorityService;
    private final SmsService smsService;
    private final UserStateService userStateService;

    public UserServiceImpl(UserRepository userRepository,
                           UserMapper userMapper,
                           PasswordEncoder passwordEncoder,
                           MailService mailService,
                           TokenService tokenService,
                           FileRepository fileRepository,
                           UserAuthorityService userAuthorityService,
                           SmsService smsService,
                           UserStateService userStateService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.mailService = mailService;
        this.tokenService = tokenService;
        this.fileRepository = fileRepository;
        this.userAuthorityService = userAuthorityService;
        this.smsService = smsService;
        this.userStateService = userStateService;
    }

    @Override
    @Transactional
    public UserDTO save(String login, String firstName, String lastName, String email, String password, String cellphone) {
        log.debug("Request to save user : {}, {}, {}, {}, {}", login, firstName, lastName, email, password);
        Optional<UserEntity> userEntityOptional = userRepository.findByLogin(login);
        UserEntity userEntity;
        if (userEntityOptional.isPresent()) {
            userEntity = userEntityOptional.get();
        } else {
            userEntity = new UserEntity();
            userEntity.setLogin(login);
            userEntity.setPasswordHash(passwordEncoder.encode(password));
            userEntity.setActivationKey(RandomUtil.generateActivationKey());
            userEntity.setActivated(false);
        }
        userEntity.setFirstName(firstName);
        userEntity.setLastName(lastName);
        userEntity.setEmail(email);
        userEntity.setCellphone(cellphone);
        userEntity = userRepository.save(userEntity);

        try {
            List<String> authorities = new ArrayList<>();
            authorities.add(AuthoritiesConstants.ADMIN);
            authorities.add(AuthoritiesConstants.USER);
            userAuthorityService.grantAuthority(authorities, userEntity.getId());
        } catch (NotFoundException ignore) {
        }

        userStateService.updateState(UserStateType.COMPANY, userEntity.getId());

        boolean bool = smsService.send(userEntity.getCellphone(), userEntity.getActivationKey());
        if (!bool) {
            mailService.sendActivationEmail(userEntity);
        }
        return userMapper.toDto(userEntity);
    }

    @Override
    @Transactional
    public UserDTO saveActive(String login, String firstName, String lastName, String email, String password, String cellphone, Boolean active) throws NotFoundException {
        log.debug("Request to save user : {}, {}, {}, {}, {}", login, firstName, lastName, email, password);
        Optional<UserEntity> userEntityOptional = userRepository.findByLogin(login);

        UserEntity userEntity = userEntityOptional.get();
        userEntity.setLogin(login);
        userEntity.setPasswordHash(passwordEncoder.encode(password));
        userEntity.setFirstName(firstName);
        userEntity.setLastName(lastName);
        userEntity.setEmail(email);
        userEntity.setCellphone(cellphone);
        userEntity.setActivated(active);
        userEntity.setInvitationKey(null);
        userEntity = userRepository.save(userEntity);
        userAuthorityService.grantAuthority(AuthoritiesConstants.USER, userEntity.getId());
        return userMapper.toDto(userEntity);
    }

    @Override
    public UserDTO update(UserDTO userDTO) throws NotFoundException {
        log.debug("Request to update user : {}", userDTO);
        UserEntity userEntity = userRepository.findOne(userDTO.getId());
        if (userEntity != null) {
            if (userDTO.getFileId() != null) {
                Optional<FileEntity> fileEntityOptional = fileRepository.findById(userDTO.getFileId());
                if (fileEntityOptional.isPresent())
                    userEntity.setFile(fileEntityOptional.get());
            }
            userEntity.setFirstName(userDTO.getFirstName());
            userEntity.setLastName(userDTO.getLastName());
            userEntity.setEmail(userDTO.getEmail());
            userEntity = userRepository.save(userEntity);

            return userMapper.toDto(userEntity);
        } else {
            throw new NotFoundException("User Not Available");
        }

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
    public Optional<UserDTO> findByEmail(String email) {
        log.debug("Request to find user by email : {}", email);
        return userRepository.findByEmail(email)
            .map(userMapper::toDto);
    }

    @Override
    public Optional<UserDTO> findByActivationKey(String activationKey) {
        log.debug("Request to find user by activation key : {}", activationKey);
        return userRepository.findByActivationKey(activationKey)
            .map(userMapper::toDto);
    }

    @Override
    public Optional<UserDTO> findByInvitationKey(String invitationKey) {
        log.debug("Request to find user by invitation key : {}", invitationKey);
        return userRepository.findByInvitationKey(invitationKey)
            .map(userMapper::toDto);
    }

    @Override
    public Page<UserDTO> findAll(Pageable pageable) {
        return userRepository.findAll(pageable)
            .map(userMapper::toDto);
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

                boolean bool = smsService.send(user.getCellphone(), user.getActivationKey());
                if (!bool) {
                    mailService.sendActivationEmail(user);
                }

            } else {
                throw new IllegalStateException("User Is Active");
            }
        } else {
            throw new NotFoundException("User Not Available");
        }

    }

    @Override
    public void requestToResetPassword(String email) throws NotFoundException {
        log.debug("Request to reset password by email : {}", email);
        Optional<UserEntity> userOptional = userRepository.findByLogin(email);
        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            if (user.isActivated()) {
                user.setResetKey(RandomUtil.generateResetKey());
                user.setResetDate(ZonedDateTime.now());
                userRepository.save(user);
                mailService.sendPasswordResetMail(user);
            } else {
                throw new IllegalStateException("User Isn't Active");
            }
        } else {
            throw new NotFoundException("User Not Found By Email");
        }
    }

    @Override
    public void completeResetPassword(String resetKey, String newPassword) throws NotFoundException {
        log.debug("Request to change password by reset password : {}, {}", resetKey, newPassword);
        Optional<UserEntity> userOptional = userRepository.findByResetKey(resetKey);
        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            user.setPasswordHash(passwordEncoder.encode(newPassword));
            user.setResetKey(null);
            user.setResetDate(null);
            userRepository.save(user);
        } else {
            throw new NotFoundException("User Not Found By Reset Key");
        }
    }

    @Override
    public UserDTO activate(String activationKey) throws NotFoundException {
        log.debug("Request to activate user by activationKey : {}", activationKey);
        Optional<UserEntity> userOptional = userRepository.findByActivationKey(activationKey);
        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            if (!user.isActivated()) {
                user.setActivated(true);
                user.setActivationKey(null);
                user = userRepository.save(user);
                return userMapper.toDto(user);
            } else {
                throw new IllegalStateException("User Is Active");
            }
        } else {
            throw new NotFoundException("کد وارد شده اشتباه است");
        }
    }

    @Override
    public TokenDTO authorize(String username, String password, Boolean isRemember) throws NotFoundException {
        log.debug("Request to authorize user : {}, {}, {}", username, password, isRemember);
        return tokenService.createAccessTokenByUserNameAndPassword(username, password, isRemember);
    }

    @Override
    public TokenDTO authorizeWithoutPassword(UserDTO userDTO) {
        log.debug("Request to authorize user : {}, {}, {}", userDTO.getEmail());
        return tokenService.createAccessTokenByUserName(userDTO.getLogin());
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

    @Transactional(readOnly = true)
    public Optional<UserDTO> getUserWithAuthorities() {
        return SecurityUtils.getCurrentUserLogin()
            .flatMap(userRepository::findOneWithAuthoritiesByLogin)
            .map(userMapper::toDto);
    }

}
