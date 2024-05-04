package cn.ultronxr.framework.util;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

import static cn.ultronxr.framework.bean.Constant.AuthCookieKey.AUTH_KEY;

/**
 * @author Ultronxr
 * @date 2023/02/02 14:58
 * @description
 */
public class JWSTokenUtils {

    /**
     * 向 redis 中插入 JWS auth token 键值对内容时使用的 field 键<br/><br/>
     *
     * 注：有关 redis hash 中 key 和 field 的区别：<br/>
     *     key -> {field1: value1, field2: value2 ...}
     */
    private static final String FIELD_PREFIX_AUTH_TOKEN = "AUTH-";

    /** 向 redis 中插入 JWS refresh token 键值对内容时使用的 field 键 */
    private static final String FIELD_PREFIX_REFRESH_TOKEN = "REFRESH-";


    /**
     * 把传入的 用户名 包装成 JWS auth token 插入 redis 时使用的 field 键
     *
     * @param username 用户名
     */
    public static String authFieldWrapper(String username) {
        return FIELD_PREFIX_AUTH_TOKEN + username;
    }

    /**
     * 把传入的 用户名 包装成 JWS refresh token 插入 redis 时使用的 field 键
     *
     * @param username 用户名
     */
    public static String refreshFieldWrapper(String username) {
        return FIELD_PREFIX_REFRESH_TOKEN + username;
    }

    /**
     * 把客户端请求头中的 JWS token 解析出来
     *
     * @param request   客户端请求
     * @return 干净的 JWS token 字符串
     */
    public static String unwrapRequestToken(HttpServletRequest request) {
        return unwrapRequestToken(request, AUTH_KEY);
    }

    /**
     * 把客户端请求头中的 JWS token 解析出来
     *
     * @param request   客户端请求
     * @param headerKey 请求头 key
     * @return 干净的 JWS token 字符串
     */
    public static String unwrapRequestToken(HttpServletRequest request, String headerKey) {
        if(null == request) {
            return null;
        }
        String token = request.getHeader(headerKey);
        return unwrapRequestToken(token);
    }

    /**
     * 把客户端请求的 以 “Bearer ” 开头的 JWS token 解析出来
     *
     * @param bearerToken 以 “Bearer ” 开头的包含 JWS token 的字符串
     * @return 干净的 JWS token 字符串
     */
    public static String unwrapRequestToken(String bearerToken) {
        if(!StringUtils.isEmpty(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.replaceFirst("Bearer ", "");
        }
        return bearerToken;
    }

}
