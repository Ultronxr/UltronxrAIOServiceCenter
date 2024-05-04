package cn.ultronxr.framework.bean;

import lombok.Getter;

/**
 * @author Ultronxr
 * @date 2023/02/01 16:38
 * @description 常量
 */
@Getter
public class Constant {

    /**
     * cookie 中存储的用户登录凭据 的 key
     */
    public static class AuthCookieKey {
        public static final String AUTH_KEY = "Authorization";

        public static final String REFRESH_KEY = "Authorization_Refresh";

        public static final String USERNAME_KEY = "Username";

        public static final String REMEMBER_ME_KEY = "RememberMe";

    }


    /**
     * 用户登录或请求接口时，拦截器/业务代码对其登录凭据进行校验，产生的登录结果信息
     */
    @Getter
    public enum AuthInfo {

        NO_AUTH_TOKEN("未携带 auth token"),
        INVALID_TOKEN("token 过期/无效"),
        REFRESH_AUTH_TOKEN("auth token 过期，使用 refresh token 进行更新"),
        LOGGED_IN("已登录"),
        ;

        private final String msg;

        AuthInfo(String msg) {
            this.msg = msg;
        }

        @Override
        public String toString() {
            return this.msg;
        }

    }

}
