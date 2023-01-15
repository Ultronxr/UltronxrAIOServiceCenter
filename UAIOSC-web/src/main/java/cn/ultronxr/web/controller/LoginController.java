package cn.ultronxr.web.controller;

import cn.ultronxr.common.bean.AjaxResponse;
import cn.ultronxr.framework.cache.user.UserCacheService;
import cn.ultronxr.framework.jjwt.TokenService;
import cn.ultronxr.system.bean.mybatis.bean.User;
import cn.ultronxr.web.bean.LoginVO;
import cn.ultronxr.web.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

/**
 * @author Ultronxr
 * @date 2023/01/13 14:58
 * @description
 */
@Controller
@RequestMapping("/")
@Slf4j
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserCacheService userCacheService;


    /**
     * 默认返回 login 页面<br/>
     * 如果用户已登录，则跳转到 index 页面
     */
    @GetMapping("login")
    public String login(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        // TODO 如果已登录则自动跳转到index
        String token = request.getHeader("Authorization");
        return "login";
    }

    /**
     * 处理 ajax login 请求
     */
    @PostMapping("ajaxLogin")
    @ResponseBody
    public AjaxResponse ajaxLogin(@RequestBody LoginVO loginVO, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        // 校验用户名和密码
        User user = loginService.login(loginVO);
        if(user != null) {
            // 校验成功，在缓存中保存用户信息
            userCacheService.putUser(user, loginVO.getRememberMe());

            // 签发 JWS auth token
            String authToken = tokenService.createAuthToken(loginVO.getUsername(), loginVO.getRememberMe());
            tokenService.saveToken("AUTH" + loginVO.getUsername(), authToken);

            // 勾选了“记住我”时，签发 JWS refresh token
            if(loginVO.getRememberMe()) {
                String refreshToken = tokenService.createRefreshToken(loginVO.getUsername(), loginVO.getRememberMe());
                tokenService.saveToken("REFRESH" + loginVO.getUsername(), refreshToken);
            }

            HashMap<String, String> responseMap = new HashMap<>();
            responseMap.put("authToken", null);
            responseMap.put("refreshToken", null);
            return AjaxResponse.success("用户登录成功", responseMap);
        }

        return AjaxResponse.fail("用户登录失败");
    }

}
