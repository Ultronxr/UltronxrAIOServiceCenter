package cn.ultronxr.web.controller.system;

import cn.ultronxr.common.bean.AjaxResponse;
import cn.ultronxr.common.util.DateTimeUtils;
import cn.ultronxr.framework.service.LoginService;
import cn.ultronxr.system.bean.mybatis.bean.User;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Ultronxr
 * @date 2022/12/15 22:09
 * @description
 */
@Controller
@RequestMapping("/")
@Slf4j
public class LoginController {

    @Value("${shiro.rememberMe.enabled}")
    private Boolean rememberMeEnabled;

    @Data
    public static class LoginVO {
        private String username;
        private String password;
        private String isRememberMe;
    }

    @Resource
    private LoginService loginService;


    @GetMapping("login")
    public String login(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        boolean isLogin = loginService.isAuthenticated(session.getId(), request, response);
        if(isLogin) {
            User user = loginService.getUserInfo(session.getId(), request, response);
            session.setAttribute("user", user);
            log.info("用户已登录。username={}", user.getUsername());
            return "index";
        }

        return "login";
    }

    @RequestMapping("ajaxLogin")
    @ResponseBody
    public AjaxResponse ajaxLogin(@RequestBody LoginVO loginVO, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        Subject subject = SecurityUtils.getSubject();
        boolean rememberMe = rememberMeEnabled && loginVO.getIsRememberMe().equals("on");
        UsernamePasswordToken token = new UsernamePasswordToken(loginVO.getUsername(), loginVO.getPassword(), rememberMe);
        try {
            subject.login(token);
            User user = (User) subject.getPrincipal();
            session.setAttribute("user", user);
            log.info("用户登录成功：username={}", user.getUsername());
            return AjaxResponse.success("登录成功");
        } catch (Exception e) {
            log.warn("用户登录失败：username={}", loginVO.getUsername());
            log.warn("{}\n{}", e.getClass(), e.getMessage());
            return AjaxResponse.fail("用户名或密码错误！");
        }
    }

    @RequestMapping("checkLogin")
    @ResponseBody
    public AjaxResponse checkLogin() {
        return AjaxResponse.success();
    }

    public static void main(String[] args) {
        String str = DateTimeUtils.utcToUtc8WithStandardFormat("2023-12-15T14:19:25.429Z");
        System.out.println(str);
    }

}
