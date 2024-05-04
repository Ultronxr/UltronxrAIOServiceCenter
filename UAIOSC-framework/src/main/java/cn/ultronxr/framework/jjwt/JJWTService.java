package cn.ultronxr.framework.jjwt;

import cn.hutool.core.date.CalendarUtil;
import cn.hutool.core.lang.UUID;
import cn.ultronxr.framework.config.JJWTConfig;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * @author Ultronxr
 * @date 2023/01/14 16:08
 * @description JJWT 签发 token 的实现<br/>
 *              参见：<a href="https://jwt.io/">jwt.io</a><br/>
 *              注意 JWT(Json Web Token) 与 JWS(JSON Web Signature) 的区别：<br/>
 *              JWT 是一个规范，JWS 是 JWT 的一种实现方式。<br/><br/>
 *
 *              JWS 一个完整 token 的格式：<br/>
 *              头部（明文），表明token类型和算法 Header: {@code base64UrlEncode(header)}<br/>
 *              .<br/>
 *              载荷（明文），包含各种claims声明 PayLoad: {@code base64UrlEncode(payload)}<br/>
 *              .<br/>
 *              签名（密文），验证数据完整性 Signature: {@code HMACSHA512(base64UrlEncode(header)+"."+base64UrlEncode(payload), secretKey)}<br/>
 */
@Service
@Slf4j
public class JJWTService {

    @Autowired
    private JJWTConfig jjwtConfig;

    private SecretKey SECRET_KEY;

    private final HashMap<String, Object> HEADER = new HashMap<>();


    @PostConstruct
    private void init() {
        HEADER.put("alg", "HS512");
        HEADER.put("typ", "JWT");

        SECRET_KEY = Keys.hmacShaKeyFor(jjwtConfig.getSecret().getBytes(StandardCharsets.UTF_8));
        // Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    /**
     * 为用户签发一个新的 JWS token
     *
     * @param username                登录用户名
     * @param tokenExpireMilliSeconds 指定该 JWT 的有效时长（毫秒）
     * @param authentication          spring security Authentication 对象，存储了用户登录验证信息
     * @return  签发的 token 字符串
     */
    public String generate(String username, Authentication authentication, long tokenExpireMilliSeconds) {
        Date now = CalendarUtil.calendar().getTime(),
             expire = new Date(now.getTime() + tokenExpireMilliSeconds);
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("username", username);
        if(null != authentication) {
            // 使用反射获取 userId 字段
            Long userId = null;
            Class<?> clazz = authentication.getPrincipal().getClass();
            try {
                Field field = clazz.getDeclaredField("userId");
                field.setAccessible(true);
                userId = field.getLong(authentication.getPrincipal());
            } catch (NoSuchFieldException | IllegalAccessException e) {
                log.error("生成 JWS token 抛出异常：无法使用反射获取 userId 字段值！");
            }
            claims.put("userId", userId);

            username = authentication.getName();
            claims.put("username", username);
            String authorities =
                    authentication.getAuthorities().stream()
                            .map(GrantedAuthority::getAuthority)
                            .collect(Collectors.joining(","));
            claims.put("authorities", authorities);
        }

        JwtBuilder jwtBuilder = Jwts.builder()
                // === Header ===
                .setHeader(HEADER)
                // === Payload ===
                // 自定义声明
                // 注：如果使用 setClaims(HashMap<>) 方法，会覆盖所有之前的声明，所以应该放在最开头执行，然后再设置标准声明；不想要这样的话，可以选择使用 claim(String, Object) 方法
                .setClaims(claims)
                // 标准声明 jti: JWT 的唯一身份标识，主要用来作为一次性token，从而回避重放攻击
                .setId(UUID.randomUUID().toString())
                // 标准声明 iss: JWT 签发者
                .setIssuer(jjwtConfig.getIssuer())
                // 标准声明 sub: JWT 的主体用户
                .setSubject(username)
                // 标准声明 aud: JWT 的接收方
                .setAudience(username)
                // 标准声明 iat: JWT 签发时间
                .setIssuedAt(now)
                // 标准声明 exp: JWT 过期时间，必须要大于签发时间
                .setExpiration(expire)
                // 标准声明 nbf: 在什么时间之前，该 JWT 都是不可用的
                .setNotBefore(now)
                // === Signature ===
                // JWT 的签发方式
                .signWith(SECRET_KEY);

        return jwtBuilder.compact();
    }

    /**
     * 验证并解析 JWS token
     *
     * @param token JWS token
     * @return {@code Jws} 对象，包含 Header、Body (PayLoad)、Signature 三部分
     */
    public Jws<Claims> parse(String token)
            throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException
    {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token);
    }

}
