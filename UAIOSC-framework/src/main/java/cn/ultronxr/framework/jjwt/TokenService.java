package cn.ultronxr.framework.jjwt;

import cn.ultronxr.framework.config.JJWTConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author Ultronxr
 * @date 2023/01/14 20:48
 * @description 管理 JJWT 签发的 token 的服务
 */
@Service
@Slf4j
public class TokenService {

    public static final String REDIS_HASH_KEY = "UAIOSC-framework";

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    private JJWTConfig jjwtConfig;

    @Autowired
    private JJWTService jjwtService;


    /**
     * 为用户签发一个新的 登录 JWS token
     *
     * @param username   用户名
     * @param rememberMe 登录时是否勾选了“记住我”
     */
    public String createAuthToken(String username, Boolean rememberMe) {
        return jjwtService.generate(username, rememberMe, jjwtConfig.authTokenExpireMilliSeconds());
    }

    /**
     * 为用户签发一个新的 更新 JWS token （勾选了“记住我”）
     *
     * @param username   用户名
     * @param rememberMe 登录时是否勾选了“记住我”
     */
    public String createRefreshToken(String username, Boolean rememberMe) {
        return jjwtService.generate(username, rememberMe, jjwtConfig.refreshTokenExpireMilliSeconds());
    }

    /**
     * 解析与验证一个 JWS token 是否合法
     *
     * @param token JWS token
     * @return 非 {@code null} - 合法；{@code null} - 不合法
     */
    public Jws<Claims> validateToken(String token) {
        return jjwtService.parse(token);
    }

    /**
     * 检查 redis 中是否已存在指定用户的 token
     *
     * @param username 用户名
     * @return {@code true} - 存在；{@code false} - 不存在
     */
    public boolean hasToken(String username) {
        return redisTemplate.opsForHash().hasKey(REDIS_HASH_KEY, username);
    }

    /**
     * 获取 redis 中指定用户的 JWS token
     *
     * @param username 用户名
     * @return JWS token 字符串
     */
    public String getToken(String username) {
        return (String) redisTemplate.opsForHash().get(REDIS_HASH_KEY, username);
    }

    /**
     * 向 redis 中插入指定用户的 JWS token
     *
     * @param username 用户名
     * @param token    JWS token 字符串
     */
    public void saveToken(String username, String token) {
        redisTemplate.opsForHash().put(REDIS_HASH_KEY, username, token);
    }

    /**
     * 删除 redis 中指定用户的 JWS token
     *
     * @param username 用户名
     */
    public void deleteToken(String username) {
        redisTemplate.opsForHash().delete(REDIS_HASH_KEY, username);
    }

}
