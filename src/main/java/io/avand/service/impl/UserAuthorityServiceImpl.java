package io.avand.service.impl;

import io.avand.domain.entity.jpa.AuthorityEntity;
import io.avand.domain.entity.jpa.UserAuthorityEntity;
import io.avand.domain.entity.jpa.UserEntity;
import io.avand.repository.jpa.AuthorityRepository;
import io.avand.repository.jpa.UserAuthorityRepository;
import io.avand.repository.jpa.UserRepository;
import io.avand.service.UserAuthorityService;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class UserAuthorityServiceImpl implements UserAuthorityService {

    private final Logger log = LoggerFactory.getLogger(UserAuthorityServiceImpl.class);

    private final UserAuthorityRepository userAuthorityRepository;
    private final AuthorityRepository authorityRepository;
    private final UserRepository userRepository;

    public UserAuthorityServiceImpl(UserAuthorityRepository userAuthorityRepository,
                                    AuthorityRepository authorityRepository,
                                    UserRepository userRepository) {
        this.userAuthorityRepository = userAuthorityRepository;
        this.authorityRepository = authorityRepository;
        this.userRepository = userRepository;
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
