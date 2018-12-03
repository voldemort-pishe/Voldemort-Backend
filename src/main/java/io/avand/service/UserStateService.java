package io.avand.service;

import io.avand.service.dto.UserStateDTO;
import javassist.NotFoundException;

public interface UserStateService {
    UserStateDTO save(UserStateDTO userStateDTO) throws NotFoundException;

    UserStateDTO findByUserId(Long userId);
}
