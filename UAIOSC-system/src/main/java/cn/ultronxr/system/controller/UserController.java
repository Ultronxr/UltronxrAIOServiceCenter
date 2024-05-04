package cn.ultronxr.system.controller;

import cn.ultronxr.common.bean.AjaxResponse;
import cn.ultronxr.common.util.AjaxResponseUtils;
import cn.ultronxr.framework.util.JWSTokenUtils;
import cn.ultronxr.system.bean.UserInfo;
import cn.ultronxr.system.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Ultronxr
 * @date 2022/12/13 14:04
 * @description
 */
@Controller("systemUserController")
@RequestMapping("/system/user")
@Slf4j
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/getUserInfo")
    @ResponseBody
    public AjaxResponse getUserInfo(HttpServletRequest request) {
        String token = JWSTokenUtils.unwrapRequestToken(request);
        UserInfo userInfo = userService.getUserInfo(token);
        if(null == userInfo) {
            return AjaxResponseUtils.fail("userInfo 获取失败！");
        }
        return AjaxResponseUtils.success(userInfo);
    }

}
