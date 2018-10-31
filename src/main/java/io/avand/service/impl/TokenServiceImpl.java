package io.avand.service.impl;

import io.avand.security.jwt.TokenProvider;
import io.avand.service.TokenService;
import io.avand.service.dto.TokenDTO;
import io.avand.service.dto.UserDTO;
import javassist.NotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class TokenServiceImpl implements TokenService {

    private final TokenProvider tokenProvider;

    private final AuthenticationManager authenticationManager;

    public TokenServiceImpl(TokenProvider tokenProvider, AuthenticationManager authenticationManager) {
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public TokenDTO createAccessTokenByUserNameAndPassword(String username, String password, Boolean rememberMe) throws NotFoundException {
        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(username, password);
        try {
            Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            boolean isRemember = (rememberMe == null) ? false : rememberMe;
            String jwt = tokenProvider.createToken(authentication, isRemember);
            TokenDTO tokenDTO = new TokenDTO();
            tokenDTO.setToken(jwt);
            return tokenDTO;
        } catch (AuthenticationException ae) {
            throw new NotFoundException("اطلاعات کاربری صحیح نمی باشد");
        }
    }

    @Override
    public TokenDTO createAccessTokenByUserName(UserDTO userDTO) {
        List<GrantedAuthority> grantedAuthorities = userDTO.getUserAuthorities().stream()
            .map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
            .collect(Collectors.toList());
            PreAuthenticatedAuthenticationToken authentication = new PreAuthenticatedAuthenticationToken(userDTO.getEmail() , null, grantedAuthorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = tokenProvider.createToken(authentication, false);
            TokenDTO tokenDTO = new TokenDTO();
            tokenDTO.setToken(jwt);
            return tokenDTO;
    }


}
