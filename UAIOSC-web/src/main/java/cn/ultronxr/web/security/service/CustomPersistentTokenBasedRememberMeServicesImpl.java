package cn.ultronxr.web.security.service;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.ultronxr.web.bean.RequestWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author Ultronxr
 * @date 2023/03/12 21:15
 * @description 对 PersistentTokenBasedRememberMeServices 的自定义实现
 */
@Slf4j
public class CustomPersistentTokenBasedRememberMeServicesImpl extends PersistentTokenBasedRememberMeServices {

    private boolean alwaysRemember;


    public CustomPersistentTokenBasedRememberMeServicesImpl(String key, UserDetailsService userDetailsService, PersistentTokenRepository persistentTokenRepository) {
        super(key, userDetailsService, persistentTokenRepository);
    }

    @Override
    public void setAlwaysRemember(boolean alwaysRemember) {
        this.alwaysRemember = alwaysRemember;
    }

    /**
     * 判断登录请求中是否要求了 rememberMe
     */
    @Override
    protected boolean rememberMeRequested(HttpServletRequest request, String parameter) {
        if (alwaysRemember) {
            return true;
        }
        // 判断请求是否为JSON
        if (request != null
                && request.getMethod().equalsIgnoreCase("POST")
                && request.getContentType() != null
                && (request.getContentType().equalsIgnoreCase(MediaType.APPLICATION_JSON_VALUE) || request.getContentType().contains(MediaType.APPLICATION_JSON_VALUE))
        ) {
            String str = null;
            try {
                // 从请求的 json 中解析出 rememberMe
                str = new RequestWrapper(request).getBodyString();
                JSONObject jsonObject = JSONUtil.parseObj(str);
                Boolean rememberMe = jsonObject.getBool("rememberMe");
                log.info("登录请求 rememberMe = {}", rememberMe);
                if(rememberMe) {
                    return true;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        // 否则调用原本的判断 rememberMe 的方法
        return super.rememberMeRequested(request, parameter);
    }

}
