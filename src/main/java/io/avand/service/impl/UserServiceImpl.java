package io.avand.service.impl;

import io.avand.domain.entity.jpa.*;
import io.avand.repository.jpa.AuthorityRepository;
import io.avand.repository.jpa.FileRepository;
import io.avand.repository.jpa.UserRepository;
import io.avand.security.AuthoritiesConstants;
import io.avand.security.SecurityUtils;
import io.avand.service.*;
import io.avand.service.dto.*;
import io.avand.service.mapper.UserMapper;
import io.avand.service.util.RandomUtil;
import io.avand.web.rest.errors.ServerErrorException;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    private final AuthorityRepository authorityRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    private final MailService mailService;

    private final TokenService tokenService;

    private final FileRepository fileRepository;

    private final PlanService planService;
    private final UserPlanService userPlanService;
    private final InvoiceService invoiceService;
    private final SubscriptionService subscriptionService;
    private final UserAuthorityService userAuthorityService;
    private final SmsService smsService;

    public UserServiceImpl(UserRepository userRepository,
                           AuthorityRepository authorityRepository,
                           UserMapper userMapper,
                           PasswordEncoder passwordEncoder,
                           MailService mailService,
                           TokenService tokenService,
                           FileRepository fileRepository,
                           PlanService planService,
                           UserPlanService userPlanService,
                           InvoiceService invoiceService,
                           SubscriptionService subscriptionService,
                           UserAuthorityService userAuthorityService,
                           SmsService smsService) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.mailService = mailService;
        this.tokenService = tokenService;
        this.fileRepository = fileRepository;
        this.planService = planService;
        this.userPlanService = userPlanService;
        this.invoiceService = invoiceService;
        this.subscriptionService = subscriptionService;
        this.userAuthorityService = userAuthorityService;
        this.smsService = smsService;
    }

    @Override
    @Transactional
    public UserDTO save(String login, String firstName, String lastName, String email, String password, String cellphone) {
        log.debug("Request to save user : {}, {}, {}, {}, {}", login, firstName, lastName, email, password);
        Optional<UserEntity> userEntityOptional = userRepository.findByLogin(login);
        UserEntity userEntity;
        if (userEntityOptional.isPresent()) {
            userEntity = userEntityOptional.get();
            userEntity.setFirstName(firstName);
            userEntity.setLastName(lastName);
            userEntity.setEmail(email);
            userEntity.setCellphone(cellphone);
        } else {
            userEntity = new UserEntity();
            userEntity.setLogin(login);
            userEntity.setPasswordHash(passwordEncoder.encode(password));
            userEntity.setFirstName(firstName);
            userEntity.setLastName(lastName);
            userEntity.setEmail(email);
            userEntity.setCellphone(cellphone);
            userEntity.setActivationKey(RandomUtil.generateActivationKey());
            userEntity.setActivated(false);

            UserAuthorityEntity userAuthorityEntity = new UserAuthorityEntity();
            AuthorityEntity authorityEntity = authorityRepository.findByName(AuthoritiesConstants.USER);

            userAuthorityEntity.setAuthorityName(authorityEntity.getName());
            userAuthorityEntity.setUser(userEntity);

            Set<UserAuthorityEntity> userAuthorityEntities = new HashSet<>();
            userAuthorityEntities.add(userAuthorityEntity);

            userEntity.setUserAuthorities(userAuthorityEntities);
        }

        userEntity = userRepository.save(userEntity);

        try {
            this.addSubscription(userEntity);
        } catch (NotFoundException ignore) {
        }

        boolean bool = smsService.send(userEntity.getCellphone(), userEntity.getActivationKey());
        if (!bool) {
            mailService.sendActivationEmail(userEntity);
        }
        return userMapper.toDto(userEntity);
    }

    @Override
    @Transactional
    public UserDTO saveActive(String login, String firstName, String lastName, String email, String password, String cellphone, Boolean active) {
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

        UserAuthorityEntity userAuthorityEntity = new UserAuthorityEntity();
        AuthorityEntity authorityEntity = authorityRepository.findByName(AuthoritiesConstants.USER);

        userAuthorityEntity.setAuthorityName(authorityEntity.getName());
        userAuthorityEntity.setUser(userEntity);

        Set<UserAuthorityEntity> userAuthorityEntities = new HashSet<>();
        userAuthorityEntities.add(userAuthorityEntity);

        userEntity.setUserAuthorities(userAuthorityEntities);

        userEntity = userRepository.save(userEntity);

        try {
            this.addSubscription(userEntity);
        } catch (NotFoundException ignore) {
        }

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
        return tokenService.createAccessTokenByUserName(userDTO);
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

    private void addSubscription(UserEntity userEntity) throws NotFoundException {
        Optional<PlanDTO> planDTO = planService.findFreePlan();
        if (planDTO.isPresent()) {
            InvoiceDTO invoiceDTO = invoiceService.saveByPlanId(planDTO.get().getId(), userEntity.getId());

            UserPlanDTO userPlanDTO = userPlanService.save(planDTO.get().getId(), invoiceDTO.getId(), userEntity.getId());

            SubscriptionDTO subscriptionDTO = new SubscriptionDTO();
            subscriptionDTO.setUserPlanId(userPlanDTO.getId());
            subscriptionDTO.setUserId(userEntity.getId());
            subscriptionDTO.setStartDate(ZonedDateTime.now());
            subscriptionDTO.setEndDate(ZonedDateTime.now().plusDays(planDTO.get().getLength()));
            subscriptionService.save(subscriptionDTO);

            userAuthorityService.grantAuthority(AuthoritiesConstants.SUBSCRIPTION, userEntity.getId());
        }
    }
}
