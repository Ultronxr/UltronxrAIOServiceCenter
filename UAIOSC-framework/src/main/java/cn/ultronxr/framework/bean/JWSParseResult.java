package cn.ultronxr.framework.bean;

import cn.ultronxr.framework.jjwt.JWSTokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.Data;

/**
 * @author Ultronxr
 * @date 2023/01/18 19:23
 * @description JWS token 的验证与解析结果封装<br/>
 *              本对象是以下方法的产物： {@link JWSTokenService#parseToken(String)}
 */
@Data
public class JWSParseResult {

    /**
     * 验证状态，标记该 JWS token 有没有通过验证<br/>
     * {@code true} - 通过验证；{@code false} - 未通过验证
     */
    private boolean validation;

    /**
     * 如果 JWS token 未通过验证，那么这里是非空的验证信息（为何未通过验证）<br/>
     * 如果 通过验证，那么 {@code msg == null}
     */
    private String msg;

    /**
     * 如果 JWS token 未通过验证，那么 {@code jws == null} <br/>
     * 如果 通过验证，那么这里是非空的解析结果
     */
    private Jws<Claims> jws;


    /**
     * 前提：当 JWS token 未通过验证时，<br/>
     *      判断未通过原因是否为 “JWS 已过期”<br/>
     * 即：{@code if(validation == false && msg.equals("JWS 已过期")) { expired = true; }}
     *
     * @return {@code true} - 验证未通过原因是“JWS 已过期”；{@code false} - 验证通过，或验证未通过原因不是“JWS 已过期”
     */
    public boolean isExpired() {
        return !validation && msg.equals("JWS 已过期");
    }

    /**
     * 前提：当 JWS token 通过验证后，<br/>
     *      获取解析结果中 "rememberMe" 对应的值
     *
     * @return token 解析结果中的 "rememberMe" 对应的 {@code Boolean} 值<br/>
     *          如果 token 验证未通过，返回 {@code null}
     */
    public Boolean getRememberMe() {
        return null == jws ? null : jws.getBody().get("rememberMe", Boolean.class);
    }

    /**
     * 前提：当 JWS token 通过验证后，<br/>
     *      获取解析结果中 "username" 对应的值
     *
     * @return token 解析结果中的 "username" 对应的 {@code String} 值<br/>
     *          如果 token 验证未通过，返回 {@code null}
     */
    public String getUsername() {
        return null == jws ? null : jws.getBody().get("username", String.class);
    }

}
