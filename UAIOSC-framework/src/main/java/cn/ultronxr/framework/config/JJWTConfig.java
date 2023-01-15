package cn.ultronxr.framework.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author Ultronxr
 * @date 2023/01/14 15:39
 * @description JJWT (JSON Web Token Support For The JVM) 配置
 */
@Configuration
public class JJWTConfig {

    /** JWT 的签发者 */
    @Value("${jjwt.issuer}")
    private String issuer;

    /** 签发 JWT 时的自定义加密串 */
    @Value("${jjwt.secret}")
    private String secret;

    /** 登录 token 的有效时长 */
    @Value("${jjwt.expireMinutes.authToken}")
    private long authTokenExpireMinutes;

    /**
     * 勾选了“记住我”，更新 token 的有效时长，用于登录 token 过期之后更新登录 token <br/>
     * （不管有没有勾选“记住我”，登录 token 的有效时长都是固定的；
     * 勾选了“记住我”之后会多签发一个有效时长大得多的 更新 token ，在登录 token 过期之后进行更新）
     */
    @Value("${jjwt.expireMinutes.rememberMe.refreshToken}")
    private long refreshTokenExpireMinutes;


    public String getIssuer() {
        return issuer;
    }

    public String getSecret() {
        return secret;
    }

    public long authTokenExpireMilliSeconds() {
        return getMilliSeconds(authTokenExpireMinutes);
    }

    public long refreshTokenExpireMilliSeconds() {
        return getMilliSeconds(refreshTokenExpireMinutes);
    }

    private static long getMilliSeconds(long minutes) {
        return minutes * 60 * 1000L;
    }

}
