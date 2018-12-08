package io.avand.service;

import io.avand.service.dto.TokenDTO;
import io.avand.service.dto.UserDTO;
import javassist.NotFoundException;

public interface TokenService {

    TokenDTO createAccessTokenByUserNameAndPassword(String username,String password,Boolean rememberMe) throws NotFoundException;

    TokenDTO createAccessTokenByUserName(String login);
}
