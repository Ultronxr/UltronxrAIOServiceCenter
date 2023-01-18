package cn.ultronxr.framework.jjwt;

import cn.ultronxr.framework.bean.JWSParseResult;
import cn.ultronxr.framework.cache.token.JWSParseCache;
import cn.ultronxr.framework.config.JJWTConfig;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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

    /**
     * 插入 JWS auth token 键值对内容时使用的 field 键<br/><br/>
     *
     * 注：有关 redis hash 中 key 和 field 的区别：<br/>
     *     key -> {field1: value1, field2: value2 ...}
     */
    private static final String FIELD_PREFIX_AUTH_TOKEN = "AUTH-";

    /** 插入 JWS refresh token 键值对内容时使用的 field 键*/
    private static final String FIELD_PREFIX_REFRESH_TOKEN = "REFRESH-";


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
     * @param rememberMe 登录时是否勾选了“记住我”
     */
    public String createAuthToken(String username, Boolean rememberMe) {
        return jjwtService.generate(username, rememberMe, jjwtConfig.authTokenExpireMilliSeconds());
    }

    /**
     * 为用户签发一个新的 JWS refresh token （勾选了“记住我”）
     *
     * @param username   用户名
     * @param rememberMe 登录时是否勾选了“记住我”
     */
    public String createRefreshToken(String username, Boolean rememberMe) {
        return jjwtService.generate(username, rememberMe, jjwtConfig.refreshTokenExpireMilliSeconds());
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
     * 验证一个 JWS token 是否过期（使用此方法时解析的结果会被丢弃）
     *
     * @param token JWS token
     * @return {@code true} - 已过期；{@code false} 未过期
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
        if(JWSParseCache.contains(token)) {
            return JWSParseCache.get(token);
        }

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
            log.info(result.getMsg(), e);
        }
        // 把解析结果存入缓存
        JWSParseCache.put(token, result);
        return result;
    }

    /**
     * 检查 redis 中是否已存在指定用户的 JWS auth token
     *
     * @param username 用户名
     * @return {@code true} - 存在；{@code false} - 不存在
     */
    public boolean hasAuthToken(String username) {
        return redisTemplate.opsForHash().hasKey(REDIS_KEY, authFieldWrapper(username));
    }

    /**
     * 检查 redis 中是否已存在指定用户的 refresh token
     *
     * @param username 用户名
     * @return {@code true} - 存在；{@code false} - 不存在
     */
    public boolean hasRefreshToken(String username) {
        return redisTemplate.opsForHash().hasKey(REDIS_KEY, refreshFieldWrapper(username));
    }

    /**
     * 获取 redis 中指定用户的 JWS auth token
     *
     * @param username 用户名
     * @return JWS auth token 字符串
     */
    public String getAuthToken(String username) {
        return (String) redisTemplate.opsForHash().get(REDIS_KEY, authFieldWrapper(username));
    }

    /**
     * 获取 redis 中指定用户的 JWS refresh token
     *
     * @param username 用户名
     * @return JWS refresh token 字符串
     */
    public String getRefreshToken(String username) {
        return (String) redisTemplate.opsForHash().get(REDIS_KEY, refreshFieldWrapper(username));
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
            redisTemplate.opsForHash().put(REDIS_KEY, authFieldWrapper(username), authToken);
        }
        if(StringUtils.isNotEmpty(refreshToken)) {
            redisTemplate.opsForHash().put(REDIS_KEY, refreshFieldWrapper(username), refreshToken);
        }
    }

    /**
     * 删除 redis 中指定用户的 JWS auth token 和 JWS refresh token
     *
     * @param username 用户名
     */
    public void deleteToken(String username) {
        redisTemplate.opsForHash().delete(REDIS_KEY, authFieldWrapper(username));
        redisTemplate.opsForHash().delete(REDIS_KEY, refreshFieldWrapper(username));
    }

    /**
     * 把传入的 用户名 包装成 JWS auth token 插入 redis 时使用的 field 键
     *
     * @param username 用户名
     */
    public String authFieldWrapper(String username) {
        return FIELD_PREFIX_AUTH_TOKEN + username;
    }

    /**
     * 把传入的 用户名 包装成 JWS refresh token 插入 redis 时使用的 field 键
     *
     * @param username 用户名
     */
    public String refreshFieldWrapper(String username) {
        return FIELD_PREFIX_REFRESH_TOKEN + username;
    }

}