package hr.pishe.service;

import hr.pishe.web.rest.vm.UserAuthorityVM;
import javassist.NotFoundException;

import java.util.List;

public interface UserAuthorityService {

    List<UserAuthorityVM> findByUserId(Long userId);

    void grantAuthority(String authority, Long userId) throws NotFoundException;

    void grantAuthority(List<String> authorities, Long userId) throws NotFoundException;

    void removeAuthority(String authority, Long userId) throws NotFoundException;

}
