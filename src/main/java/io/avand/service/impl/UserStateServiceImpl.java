package io.avand.service.impl;

import io.avand.domain.entity.jpa.UserStateEntity;
import io.avand.repository.jpa.UserStateRepository;
import io.avand.security.SecurityUtils;
import io.avand.service.UserStateService;
import io.avand.service.dto.UserStateDTO;
import io.avand.service.mapper.UserStateMapper;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserStateServiceImpl implements UserStateService {

    private final Logger log = LoggerFactory.getLogger(UserStateServiceImpl.class);
    private final UserStateRepository userStateRepository;
    private final UserStateMapper userStateMapper;
    private final SecurityUtils securityUtils;

    public UserStateServiceImpl(UserStateRepository userStateRepository,
                                UserStateMapper userStateMapper,
                                SecurityUtils securityUtils) {
        this.userStateRepository = userStateRepository;
        this.userStateMapper = userStateMapper;
        this.securityUtils = securityUtils;
    }

    @Override
    public UserStateDTO save(UserStateDTO userStateDTO) throws NotFoundException {
        log.debug("Request to save userState : {}", userStateDTO);
        Long userId = securityUtils.getCurrentUserId();
        userStateDTO.setUserId(userId);
        UserStateEntity userStateEntity = userStateRepository.findByUserId(userId);
        if (userStateEntity != null) {
            userStateEntity.setState(userStateDTO.getState());
        } else {
            userStateEntity = userStateMapper.toEntity(userStateDTO);
        }
        userStateEntity = userStateRepository.save(userStateEntity);
        return userStateMapper.toDto(userStateEntity);

    }

    @Override
    public UserStateDTO findByUserId(Long userId) {
        log.debug("Request to find userState by userId : {}", userId);
        UserStateEntity userStateEntity = userStateRepository.findByUserId(userId);
        return userStateMapper.toDto(userStateEntity);
    }
}
