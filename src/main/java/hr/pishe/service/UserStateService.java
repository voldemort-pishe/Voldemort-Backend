package hr.pishe.service;

import hr.pishe.domain.enumeration.UserStateType;
import hr.pishe.service.dto.UserStateDTO;
import javassist.NotFoundException;

public interface UserStateService {
    UserStateDTO save(UserStateDTO userStateDTO) throws NotFoundException;

    UserStateDTO updateState(UserStateType stateType,Long userId);

    UserStateDTO findByUserId(Long userId);
}
