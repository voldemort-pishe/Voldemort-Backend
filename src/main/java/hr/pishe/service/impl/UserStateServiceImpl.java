package hr.pishe.service.impl;

import hr.pishe.domain.entity.jpa.UserStateEntity;
import hr.pishe.domain.enumeration.UserStateType;
import hr.pishe.repository.jpa.UserStateRepository;
import hr.pishe.security.SecurityUtils;
import hr.pishe.service.UserStateService;
import hr.pishe.service.dto.UserStateDTO;
import hr.pishe.service.mapper.UserStateMapper;
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
    public UserStateDTO updateState(UserStateType stateType, Long userId) {
        log.debug("Request to save userState : {}", stateType);
        UserStateEntity userStateEntity = userStateRepository.findByUserId(userId);
        if (userStateEntity == null) {
            userStateEntity = new UserStateEntity();
        }
        userStateEntity.setState(stateType);
        userStateEntity.setUserId(userId);
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
