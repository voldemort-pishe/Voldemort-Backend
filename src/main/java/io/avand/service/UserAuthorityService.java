package io.avand.service;

import io.avand.service.dto.UserAuthorityDTO;
import io.avand.web.rest.vm.UserAuthorityVM;
import javassist.NotFoundException;

import java.util.List;

public interface UserAuthorityService {

    List<UserAuthorityVM> findByUserId(Long userId);

    void grantAuthority(String authority, Long userId) throws NotFoundException;

    void removeAuthority(String authority, Long userId) throws NotFoundException;

}
