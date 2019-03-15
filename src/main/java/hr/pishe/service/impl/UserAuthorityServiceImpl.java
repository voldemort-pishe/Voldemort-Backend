package hr.pishe.service.impl;

import hr.pishe.domain.entity.jpa.AuthorityEntity;
import hr.pishe.domain.entity.jpa.PermissionEntity;
import hr.pishe.domain.entity.jpa.UserAuthorityEntity;
import hr.pishe.domain.entity.jpa.UserEntity;
import hr.pishe.repository.jpa.AuthorityRepository;
import hr.pishe.repository.jpa.UserAuthorityRepository;
import hr.pishe.repository.jpa.UserRepository;
import hr.pishe.service.UserAuthorityService;
import hr.pishe.service.mapper.UserAuthorityMapper;
import hr.pishe.web.rest.vm.UserAuthorityVM;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserAuthorityServiceImpl implements UserAuthorityService {

    private final Logger log = LoggerFactory.getLogger(UserAuthorityServiceImpl.class);

    private final UserAuthorityRepository userAuthorityRepository;
    private final AuthorityRepository authorityRepository;
    private final UserRepository userRepository;
    private final UserAuthorityMapper userAuthorityMapper;

    public UserAuthorityServiceImpl(UserAuthorityRepository userAuthorityRepository,
                                    AuthorityRepository authorityRepository,
                                    UserRepository userRepository,
                                    UserAuthorityMapper userAuthorityMapper) {
        this.userAuthorityRepository = userAuthorityRepository;
        this.authorityRepository = authorityRepository;
        this.userRepository = userRepository;
        this.userAuthorityMapper = userAuthorityMapper;
    }

    @Override
    public List<UserAuthorityVM> findByUserId(Long userId) {
        List<UserAuthorityEntity> userAuthorityEntities = userAuthorityRepository.findAllByUser_Id(userId);
        List<UserAuthorityVM> userAuthorityVMS = new ArrayList<>();
        for (UserAuthorityEntity userAuthorityEntity : userAuthorityEntities) {
            UserAuthorityVM userAuthorityVM = new UserAuthorityVM();
            userAuthorityVM.setAuthorityName(userAuthorityEntity.getAuthority().getName());
            userAuthorityVM.setUserId(userAuthorityEntity.getUser().getId());
            List<String> permissions = new ArrayList<>();
            for (PermissionEntity permission : userAuthorityEntity.getAuthority().getPermissions()) {
                permissions.add(permission.getAccess().name());
            }
            userAuthorityVM.setPermissions(permissions);
            userAuthorityVMS.add(userAuthorityVM);
        }
        return userAuthorityVMS;
    }

    @Override
    public void grantAuthority(String authority, Long userId) throws NotFoundException {
        log.debug("Request to grant authority to user : {}", userId);
        Optional<UserAuthorityEntity> userAuthorityEntityOptional =
            userAuthorityRepository.findByAuthority_NameAndUser_Id(authority, userId);
        if (!userAuthorityEntityOptional.isPresent()) {
            Optional<UserEntity> userEntityOptional = userRepository.findById(userId);
            if (userEntityOptional.isPresent()) {
                AuthorityEntity authorityEntity = authorityRepository.findByName(authority);
                if (authorityEntity != null) {
                    UserAuthorityEntity userAuthorityEntity = new UserAuthorityEntity();
                    userAuthorityEntity.setAuthority(authorityEntity);
                    userAuthorityEntity.setUser(userEntityOptional.get());
                    userAuthorityRepository.save(userAuthorityEntity);
                }
            } else {
                throw new NotFoundException("User Not Found");
            }
        }
    }

    @Override
    public void grantAuthority(List<String> authorities, Long userId) throws NotFoundException {
        log.debug("Request to grant authority to user : {}", userId);
        Optional<UserEntity> userEntityOptional = userRepository.findById(userId);
        if (userEntityOptional.isPresent()) {
            for (String authority : authorities) {
                Optional<UserAuthorityEntity> userAuthorityEntityOptional =
                    userAuthorityRepository.findByAuthority_NameAndUser_Id(authority, userId);
                if (!userAuthorityEntityOptional.isPresent()) {
                    AuthorityEntity authorityEntity = authorityRepository.findByName(authority);
                    if (authorityEntity != null) {
                        UserAuthorityEntity userAuthorityEntity = new UserAuthorityEntity();
                        userAuthorityEntity.setAuthority(authorityEntity);
                        userAuthorityEntity.setUser(userEntityOptional.get());
                        userAuthorityRepository.save(userAuthorityEntity);
                    }
                }
            }
        } else {
            throw new NotFoundException("User Not Found");
        }
    }

    @Override
    public void removeAuthority(String authority, Long userId) throws NotFoundException {
        log.debug("Request to remove authority from user : {}", userId);
        Optional<UserEntity> userEntityOptional = userRepository.findById(userId);
        if (userEntityOptional.isPresent()) {
            UserEntity userEntity = userEntityOptional.get();
            Set<UserAuthorityEntity> userAuthorityEntities = userEntity.getUserAuthorities();
            for (UserAuthorityEntity userAuthorityEntity : userAuthorityEntities) {
                if (userAuthorityEntity.getAuthority().getName().equals(authority))
                    userAuthorityEntities.remove(userAuthorityEntity);
            }
            userEntity.setUserAuthorities(userAuthorityEntities);
            userRepository.save(userEntity);
        } else {
            throw new NotFoundException("User Not Found");
        }
    }
}
