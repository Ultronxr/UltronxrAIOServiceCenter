package cn.ultronxr.web.security.component;

import cn.ultronxr.framework.bean.JWSParseResult;
import cn.ultronxr.framework.jjwt.JWSTokenService;
import cn.ultronxr.web.security.bean.SecurityUser;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ultronxr
 * @date 2023/03/11 13:34
 * @description 创建或解析 JWS token
 */
@Component
@Slf4j
public class TokenProvider {

    private final JWSTokenService jwsTokenService;

    public TokenProvider(JWSTokenService jwsTokenService) {
        this.jwsTokenService = jwsTokenService;
    }


    public String createAuthToken(Authentication authentication) {
        return jwsTokenService.createAuthToken(authentication);
    }

    public String createRefreshToken(Authentication authentication) {
        return jwsTokenService.createRefreshToken(authentication);
    }

    public Authentication parseAuthentication(String token) throws NullPointerException {
        JWSParseResult parseResult = jwsTokenService.parseToken(token);
        Claims claims = parseResult.getJws().getBody();

        Object authoritiesStr = claims.get("authorities");
        List<GrantedAuthority> authorities =
                authoritiesStr != null ?
                        Arrays.stream(authoritiesStr.toString().split(","))
                                .map(SimpleGrantedAuthority::new)
                                .collect(Collectors.toList())
                        : Collections.emptyList();
        SecurityUser principal = new SecurityUser();
        principal.setUsername(claims.getSubject());
        principal.setPassword("******");
        principal.setAuthorities(authorities);
        principal.setUserId(parseResult.getUserId());

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

}
