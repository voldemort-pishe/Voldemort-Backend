package hr.pishe.service;

import hr.pishe.service.dto.TokenDTO;
import javassist.NotFoundException;

public interface TokenService {

    TokenDTO createAccessTokenByUserNameAndPassword(String username, String password, Boolean rememberMe) throws NotFoundException;

    TokenDTO createAccessTokenByUserName(String login);
}
