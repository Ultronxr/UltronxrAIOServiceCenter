package cn.ultronxr.web.util;

import javax.servlet.http.Cookie;

/**
 * @author Ultronxr
 * @date 2023/01/30 17:36
 * @description
 */
public class CookieUtils {

    public static final int MAX_AGE_IN_SECONDS = 30*24*60*60;

    public static final String DOMAIN = "localhost";

    public static final String PATH = "/";

    public static boolean HTTPONLY = false;

    public static boolean SECURE = false;


    public static Cookie commonCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(MAX_AGE_IN_SECONDS);
        cookie.setDomain(DOMAIN);
        cookie.setPath(PATH);
        cookie.setHttpOnly(HTTPONLY);
        cookie.setSecure(SECURE);
        return cookie;
    }

}
