package io.avand.service;

import javassist.NotFoundException;

public interface UserAuthorityService {

    void grantAuthority(String authority, Long userId) throws NotFoundException;

    void removeAuthority(String authority, Long userId) throws NotFoundException;

}
