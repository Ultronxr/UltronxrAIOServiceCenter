//package cn.ultronxr.web.controller;
//
//import cn.ultronxr.web.security.filter.JWTAuthenticationFilterForUserLogin;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
///**
// * @author Ultronxr
// * @date 2023/01/13 14:58
// * @description
// */
//@Controller
//@RequestMapping("/")
//@Slf4j
//public class LoginController {
//
//
//    /**
//     * 返回 login 页面<br/>
//     * 用户登录认证流程请查看 {@link JWTAuthenticationFilterForUserLogin}
//     */
//    @GetMapping(value = {"/login"})
//    public String login(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
//        return "login";
//    }
//
//    @GetMapping(value = {"/", "/index"})
//    public String index(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
//        return "index";
//    }
//
//}
