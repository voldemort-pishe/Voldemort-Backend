package hr.pishe.service.impl;

import hr.pishe.domain.entity.jpa.UserEntity;
import hr.pishe.repository.jpa.UserRepository;
import hr.pishe.security.jwt.TokenProvider;
import hr.pishe.service.TokenService;
import hr.pishe.service.dto.TokenDTO;
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
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class TokenServiceImpl implements TokenService {

    private final TokenProvider tokenProvider;

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    public TokenServiceImpl(TokenProvider tokenProvider,
                            AuthenticationManager authenticationManager,
                            UserRepository userRepository) {
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
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
    public TokenDTO createAccessTokenByUserName(String login) {
        Optional<UserEntity> userEntity = userRepository.findOneWithAuthoritiesByLogin(login);
        if (userEntity.isPresent()) {
            List<GrantedAuthority> grantedAuthorities = userEntity.get().getUserAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthority().getName()))
                .collect(Collectors.toList());
            PreAuthenticatedAuthenticationToken authentication = new PreAuthenticatedAuthenticationToken(login, null, grantedAuthorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = tokenProvider.createToken(authentication, false);
            TokenDTO tokenDTO = new TokenDTO();
            tokenDTO.setToken(jwt);
            return tokenDTO;
        } else {
            return null;
        }
    }


}
