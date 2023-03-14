package cn.ultronxr.framework.jjwt;

import cn.ultronxr.framework.bean.JWSParseResult;
import cn.ultronxr.framework.config.JJWTConfig;
import cn.ultronxr.framework.util.JWSTokenUtils;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

/**
 * @author Ultronxr
 * @date 2023/01/14 20:48
 * @description 管理 JJWT 签发的 token （即 JWS token） 的服务
 */
@Service
@Slf4j
public class JWSTokenService {

    /** 向 redis 中插入哈希值时使用的 key 键 */
    public static final String REDIS_KEY = "UAIOSC-framework";

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    private JJWTConfig jjwtConfig;

    @Autowired
    private JJWTService jjwtService;


    /**
     * 为用户签发一个新的 JWS auth token
     *
     * @param username   用户名
     */
    public String createAuthToken(String username) {
        return jjwtService.generate(username, null, jjwtConfig.authTokenExpireMilliSeconds());
    }

    public String createAuthToken(Authentication authentication) {
        return jjwtService.generate(null, authentication, jjwtConfig.authTokenExpireMilliSeconds());
    }

    /**
     * 为用户签发一个新的 JWS refresh token （勾选了“记住我”）
     *
     * @param username   用户名
     */
    public String createRefreshToken(String username) {
        return jjwtService.generate(username, null, jjwtConfig.refreshTokenExpireMilliSeconds());
    }

    public String createRefreshToken(Authentication authentication) {
        return jjwtService.generate(null, authentication, jjwtConfig.refreshTokenExpireMilliSeconds());
    }

    /**
     * 验证一个 JWS token 是否合法（使用此方法时解析的结果会被丢弃）
     *
     * @param token JWS token
     * @return {@code true} - 合法；{@code false} 不合法
     */
    public boolean validatedToken(String token) {
        return parseToken(token).isValidation();
    }

    /**
     * <b>⚠警告：该方法返回的 false 值大部分情况下应当丢弃，不能作为“token未过期”的依据，因为该断言往往隐含了“token已通过验证”的前提；<br/>
     *      如有上述情况，应当采用 {@link #validatedToken(String)} 单独判断。 {@link JWSParseResult#isExpired()} </b><br/><br/>
     *
     * 验证一个 JWS token 是否过期（使用此方法时解析的结果会被丢弃）
     *
     * @param token JWS token
     * @return {@code true} - 已过期；{@code false} 验证通过，或验证未通过且原因不是“JWS 已过期”
     */
    public boolean expiredToken(String token) {
        return parseToken(token).isExpired();
    }

    /**
     * 验证一个 JWS token 是否合法，并解析
     *
     * @param token JWS token
     * @return 返回一个 {@link JWSParseResult} 对象
     */
    public JWSParseResult parseToken(String token) {
        // 检查缓存中是否存在该 token 的解析结果
        //if(JWSParseCache.contains(token)) {
        //    return JWSParseCache.get(token);
        //}

        JWSParseResult result = new JWSParseResult();
        try {
            result.setJws(jjwtService.parse(token));
            result.setValidation(true);
        } catch (Exception e) {
            //result.setJws(null);
            result.setValidation(false);
            if(e instanceof UnsupportedJwtException) {
                result.setMsg("JWS 类型不匹配（不是一个正确包含声明与签名的JWS）");
            } else if(e instanceof MalformedJwtException) {
                result.setMsg("JWS 字符串格式不正确");
            } else if(e instanceof SignatureException) {
                result.setMsg("JWS 签名验证失败");
            } else if(e instanceof ExpiredJwtException) {
                result.setMsg("JWS 已过期");
            } else if(e instanceof IllegalArgumentException) {
                result.setMsg("JWS 不能为空");
            } else {
                result.setMsg("其他异常");
            }
        }
        log.info("JWS token username = {} | 验证结果 = {} | 信息 = {}", result.getUsername() ,result.isValidation(), result.getMsg());
        // 把解析结果存入缓存
        //JWSParseCache.put(token, result);
        return result;
    }

    /**
     * 检查 redis 中是否已存在指定用户的 JWS auth token
     *
     * @param username 用户名
     * @return {@code true} - 存在；{@code false} - 不存在
     */
    public boolean hasAuthToken(String username) {
        return redisTemplate.opsForHash().hasKey(REDIS_KEY, JWSTokenUtils.authFieldWrapper(username));
    }

    /**
     * 检查 redis 中是否已存在指定用户的 refresh token
     *
     * @param username 用户名
     * @return {@code true} - 存在；{@code false} - 不存在
     */
    public boolean hasRefreshToken(String username) {
        return redisTemplate.opsForHash().hasKey(REDIS_KEY, JWSTokenUtils.refreshFieldWrapper(username));
    }

    /**
     * 获取 redis 中指定用户的 JWS auth token
     *
     * @param username 用户名
     * @return JWS auth token 字符串
     */
    public String getAuthToken(String username) {
        return (String) redisTemplate.opsForHash().get(REDIS_KEY, JWSTokenUtils.authFieldWrapper(username));
    }

    /**
     * 获取 redis 中指定用户的 JWS refresh token
     *
     * @param username 用户名
     * @return JWS refresh token 字符串
     */
    public String getRefreshToken(String username) {
        return (String) redisTemplate.opsForHash().get(REDIS_KEY, JWSTokenUtils.refreshFieldWrapper(username));
    }

    /**
     * 向 redis 中插入指定用户的 JWS auth token 和 JWS refresh token <br/>
     * 此方法会覆盖相同用户名的 token
     *
     * @param username     用户名
     * @param authToken    服务端签发的 JWS auth token 字符串
     * @param refreshToken 服务端签发的 JWS refresh token 字符串
     */
    public void saveToken(String username, String authToken, String refreshToken) {
        if(StringUtils.isNotEmpty(authToken)) {
            redisTemplate.opsForHash().put(REDIS_KEY, JWSTokenUtils.authFieldWrapper(username), authToken);
        }
        if(StringUtils.isNotEmpty(refreshToken)) {
            redisTemplate.opsForHash().put(REDIS_KEY, JWSTokenUtils.refreshFieldWrapper(username), refreshToken);
        }
    }

    /**
     * 删除 redis 中指定用户的 JWS auth token 和 JWS refresh token
     *
     * @param username 用户名
     */
    public void deleteToken(String username) {
        redisTemplate.opsForHash().delete(REDIS_KEY, JWSTokenUtils.authFieldWrapper(username));
        redisTemplate.opsForHash().delete(REDIS_KEY, JWSTokenUtils.refreshFieldWrapper(username));
    }

}
