package cn.ultronxr.framework.service;

import cn.ultronxr.system.bean.mybatis.bean.User;
import cn.ultronxr.system.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.web.session.mgt.WebSessionKey;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Ultronxr
 * @date 2022/12/15 17:24
 * @description
 */
@Service
@Slf4j
public class LoginService {

    // TODO 用户登录记录缓存，一段时间内超出几次错误登录就锁定 private Cache<String, Object> loginCache = new CacheManager().getCache("");

    @Resource
    private UserService userService;


    /**
     * 用户登录
     * @param username 用户登录时提交的用户名
     * @param password 用户登录时提交的密码
     * @return
     */
    public User login(String username, String password) throws AuthenticationException {
        User user = userService.findUserByUsername(username);

        // 手动判 null ，null用户不能继续在登录认证流程中往下传，否则会报错
        if(user == null) {
            throw new UnknownAccountException("用户不存在！ username="+username);
        }

        return user;
    }

    /**
     * 验证是否已登录
     */
    public boolean isAuthenticated(String sessionID, HttpServletRequest request, HttpServletResponse response){
        boolean status = false;

        SessionKey key = new WebSessionKey(sessionID, request, response);
        try{
            Session session = SecurityUtils.getSecurityManager().getSession(key);
            Object obj = session.getAttribute(DefaultSubjectContext.AUTHENTICATED_SESSION_KEY);
            if(obj != null){
                status = (Boolean) obj;
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            Session session = null;
            Object obj = null;
        }

        return status;
    }

    /**
     * 获取已登录的用户信息
     */
    public User getUserInfo(String sessionID,HttpServletRequest request,HttpServletResponse response){
        boolean status = false;
        SessionKey key = new WebSessionKey(sessionID,request,response);
        try{
            Session se = SecurityUtils.getSecurityManager().getSession(key);
            Object obj = se.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
            //org.apache.shiro.subject.SimplePrincipalCollection cannot be cast to User
            SimplePrincipalCollection coll = (SimplePrincipalCollection) obj;
            return (User) coll.getPrimaryPrincipal();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
        }
        return null;
    }

}
