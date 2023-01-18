package cn.ultronxr.web.bean;

import lombok.Data;

/**
 * @author Ultronxr
 * @date 2023/01/13 15:07
 * @description 用户登录请求内容
 */
@Data
public class LoginObj {

    private String username;
    private String password;
    private Boolean rememberMe;

}
