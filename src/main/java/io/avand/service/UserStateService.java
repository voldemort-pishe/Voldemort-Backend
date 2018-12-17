package io.avand.service;

import io.avand.domain.enumeration.UserStateType;
import io.avand.service.dto.UserStateDTO;
import javassist.NotFoundException;

public interface UserStateService {
    UserStateDTO save(UserStateDTO userStateDTO) throws NotFoundException;

    UserStateDTO updateState(UserStateType stateType,Long userId);

    UserStateDTO findByUserId(Long userId);
}
