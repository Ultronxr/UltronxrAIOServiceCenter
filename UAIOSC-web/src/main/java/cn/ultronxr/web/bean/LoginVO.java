package cn.ultronxr.web.bean;

import lombok.Data;
import lombok.Getter;

/**
 * @author Ultronxr
 * @date 2023/01/13 15:07
 * @description 用户登录请求内容
 */
@Getter
public class LoginVO {

    private String username;
    private String password;
    private Boolean rememberMe;

}
