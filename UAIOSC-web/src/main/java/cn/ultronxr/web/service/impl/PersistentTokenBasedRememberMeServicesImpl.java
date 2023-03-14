package cn.ultronxr.web.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Ultronxr
 * @date 2023/03/12 21:15
 * @description
 */
@Deprecated
@Slf4j
public class PersistentTokenBasedRememberMeServicesImpl extends PersistentTokenBasedRememberMeServices {

    private boolean alwaysRemember;


    public PersistentTokenBasedRememberMeServicesImpl(String key, UserDetailsService userDetailsService, PersistentTokenRepository jdbcTokenRepositoryImpl) {
        super(key, userDetailsService, jdbcTokenRepositoryImpl);
    }

    @Override
    public void setAlwaysRemember(boolean alwaysRemember) {
        this.alwaysRemember = alwaysRemember;
    }

    @Override
    protected boolean rememberMeRequested(HttpServletRequest request, String parameter) {
        if (alwaysRemember) {
            return true;
        }
        // 判断请求是否为JSON
        if (request != null
                && request.getMethod().equalsIgnoreCase("POST")
                && request.getContentType() != null
                && (request.getContentType().equalsIgnoreCase(MediaType.APPLICATION_JSON_VALUE) || request.getContentType().equalsIgnoreCase(MediaType.APPLICATION_JSON_VALUE))
        ) {
            Boolean rememberMe = (Boolean) request.getAttribute("rememberMe");
            if (rememberMe) {
                return true;
            }
        }
        // 否则调用原本的自我记住功能
        return super.rememberMeRequested(request, parameter);
    }

}
