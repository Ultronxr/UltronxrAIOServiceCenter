package cn.ultronxr.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Ultronxr
 * @date 2023/01/13 14:58
 * @description
 */
@Controller
@RequestMapping("/")
@Slf4j
public class LoginController {


    /**
     * 返回 login 页面<br/>
     * 用户登录认证流程请查看 {@link cn.ultronxr.web.filter.JWTAuthenticationFilter}
     */
    @GetMapping(value = {"/login"})
    public String login(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        return "login";
    }

    /**
     * 处理 ajax login 请求
     */
    //@PostMapping("/ajaxLogin")
    //@ResponseBody
    //public AjaxResponse ajaxLogin(@RequestBody LoginObj loginObj, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
    //    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginObj.getUsername(), loginObj.getPassword());
    //    Authentication authentication = null;
    //    try {
    //        authentication = authenticationManager.authenticate(token);
    //    } catch (AuthenticationException e) {
    //        log.warn("用户登录失败：username = {} , exception = {}", loginObj.getUsername(), e.getMessage());
    //        return AjaxResponseUtils.unauthorized("登录失败：" + e.getMessage());
    //    }
    //
    //    SecurityContextHolder.getContext().setAuthentication(authentication);
    //    String authToken = tokenProvider.createToken(authentication);
    //
    //    jwsTokenService.saveToken(authentication.getName(), authToken, null);
    //    log.info("{}\n{}", authentication.getName(), authToken);
    //
    //    User user = (User) authentication.getPrincipal();
    //    UserCache.putUser(user);
    //
    //    HashMap<String, String> responseMap = new HashMap<>();
    //    responseMap.put(AUTH_KEY, authToken);
    //    responseMap.put(USERNAME_KEY, authentication.getName());
    //    return AjaxResponseUtils.success("用户登录成功", responseMap);
    //}

    /**
     * 处理 ajax logout 请求
     */
    //@GetMapping("ajaxLogout")
    //@ResponseBody
    //public AjaxResponse ajaxLogout(@CookieValue(value = AUTH_KEY, required = false) String clientAuthToken,
    //                               HttpSession session, HttpServletRequest request, HttpServletResponse response) {
    //    //if(loginService.logout(JWSTokenUtils.unwrapRequestToken(clientAuthToken))) {
    //    //    return AjaxResponseUtils.success("用户登出成功");
    //    //}
    //    return AjaxResponseUtils.unauthorized("用户登出失败");
    //
    //}

}
