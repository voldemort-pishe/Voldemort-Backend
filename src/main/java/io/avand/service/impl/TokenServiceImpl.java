package io.avand.service.impl;

import io.avand.security.AuthoritiesConstants;
import io.avand.security.jwt.TokenProvider;
import io.avand.service.TokenService;
import io.avand.service.dto.TokenDTO;
import javassist.NotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

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
    public TokenDTO createAccessTokenByUserName(String username, Boolean rememberMe) {

//        HashMap<String, String> authorizationParameters = new HashMap<>();
//        authorizationParameters.put("scope", "openid");
//        authorizationParameters.put("username", "web_app");
//        authorizationParameters.put("client_id", "internal");
//        authorizationParameters.put("grant", "password");
//
//        Set<GrantedAuthority> authorities = new HashSet<>();
//        authorities.add(new SimpleGrantedAuthority(AuthoritiesConstants.USER));
//
//        Set<String> responseType = new HashSet<>();
//        responseType.add("password");
//
//        Set<String> scopes = new HashSet<>();
//        scopes.add("openid");
//
//        OAuth2Request authorizationRequest = new OAuth2Request(
//            authorizationParameters, "web_app",
//            authorities, true, scopes, null, "",
//            responseType, null);
//
//        org.springframework.security.core.userdetails.User userPrincipal =
//            new org.springframework.security.core.userdetails.User(username, "", true, true, true, true, authorities);
//
//        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
//            userPrincipal, null, authorities);
//
//
//
//        OAuth2Authentication authenticationRequest = new OAuth2Authentication(
//            authorizationRequest, authenticationToken);
//        authenticationRequest.setAuthenticated(true);
//
//        return tokenServices.createAccessToken(authenticationRequest);
        return null;
    }

}
