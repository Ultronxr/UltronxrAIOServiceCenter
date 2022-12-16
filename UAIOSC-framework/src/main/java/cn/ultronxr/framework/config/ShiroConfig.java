package cn.ultronxr.framework.config;

import cn.ultronxr.common.util.CipherUtils;
import cn.ultronxr.framework.shiro.credential.CredentialMatcher;
import cn.ultronxr.framework.shiro.realm.AuthRealm;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.MemorySessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Ultronxr
 * @date 2022/12/14 22:37
 * @description
 */
@Configuration
public class ShiroConfig {

    @Value("${shiro.session.timeout}")
    private Integer sessionTimeout;

    @Value("${shiro.session.validationInterval}")
    private Integer sessionValidationInterval;
    @Value("${shiro.cookie.domain}")
    private String cookieDomain;

    @Value("${shiro.cookie.path}")
    private String cookiePath;

    @Value("${shiro.cookie.httpOnly}")
    private Boolean cookieHttpOnly;

    @Value("${shiro.cookie.maxAge}")
    private Integer cookieMaxAge;

    @Value("${shiro.cookie.cipherKey}")
    private String cookieCipherKey;

    @Value("${shiro.rememberMe.enabled}")
    private Boolean rememberMeEnabled;


    /**
     * 自定义认证与授权器
     */
    @Bean("authRealm")
    public AuthRealm authRealm(@Qualifier("credentialMatcher") CredentialMatcher credentialMatcher) {
        AuthRealm authRealm = new AuthRealm();
        // 使用缓存
        authRealm.setCacheManager(cacheManager());
        authRealm.setCredentialsMatcher(credentialMatcher);
        return authRealm;
    }

    /**
     * 自定义密码匹配器
     */
    @Bean("credentialMatcher")
    public CredentialMatcher credentialMatcher() {
        return new CredentialMatcher();
    }

    /**
     * 自定义缓存管理器
     */
    @Bean("cacheManager")
    public CacheManager cacheManager() {
        return new MemoryConstrainedCacheManager();
    }

    /**
     * 设置 session ID
     */
    @Bean("sessionIdCookie")
    public SimpleCookie sessionIdCookie() {
        SimpleCookie simpleCookie = new SimpleCookie("shiro.session");
        // 防止xss攻击，窃取cookie内容
        simpleCookie.setHttpOnly(true);
        return simpleCookie;
    }

    /**
     * 自定义 sessionDAO
     */
    @Bean("sessionDAO")
    public SessionDAO sessionDAO(){
        return new MemorySessionDAO();
        // return new EnterpriseCacheSessionDAO();
    }

    /**
     * 自定义 session 管理器
     */
    @Bean("sessionManager")
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        // 会话超时时间，单位：毫秒
        sessionManager.setGlobalSessionTimeout(sessionTimeout * 60 * 1000L);
        // 定时清理失效会话, 清理用户直接关闭浏览器造成的孤立会话
        sessionManager.setSessionValidationInterval(sessionValidationInterval * 60 * 1000L);
        // 是否开启定时清理失效会话
        sessionManager.setSessionValidationSchedulerEnabled(true);
        // 指定sessionid
        sessionManager.setSessionIdCookie(sessionIdCookie());
        // 是否允许将sessionId 放到 cookie 中
        sessionManager.setSessionIdCookieEnabled(true);
        // 删除自带的 jsessionid ，防止第二次打开浏览器时 jsessionid 不同导致操作异常
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        // 默认使用MemerySessionDao，设置为EnterpriseCacheSessionDAO以配合ehcache实现分布式集群缓存支持
        sessionManager.setSessionDAO(sessionDAO());
        return sessionManager;
    }

    /**
     * 自定义 cookie 内容
     */
    @Bean("cookie")
    public Cookie cookie() {
        SimpleCookie cookie = new SimpleCookie();
        cookie.setDomain(cookieDomain);
        cookie.setPath(cookiePath);
        cookie.setHttpOnly(cookieHttpOnly);
        cookie.setMaxAge(cookieMaxAge * 24 * 60 * 60);
        return cookie;
    }

    /**
     * 自定义 rememberMe 功能启用时的处理器
     */
    @Bean("rememberManager")
    public RememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(cookie());
        if (StringUtils.isNotEmpty(cookieCipherKey)) {
            cookieRememberMeManager.setCipherKey(Base64.decode(cookieCipherKey));
        } else {
            cookieRememberMeManager.setCipherKey(CipherUtils.generateNewKey(128, "AES").getEncoded());
        }
        return cookieRememberMeManager;
    }

    public static void main(String[] args) {
        String cookieCipherKey = Base64.encodeToString(CipherUtils.generateNewKey(128, "AES").getEncoded());
        System.out.println(cookieCipherKey);
    }

    /**
     * 自定义 web 安全管理器
     */
    @Bean("securityManager")
    public SecurityManager securityManager(@Qualifier("authRealm") AuthRealm authRealm) {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(authRealm);
        manager.setCacheManager(cacheManager());
        manager.setSessionManager(sessionManager());
        // 如果启用 rememberMe 功能，则添加管理器，否则不添加
        manager.setRememberMeManager(rememberMeEnabled ? rememberMeManager() : null);
        return manager;
    }

    /**
     * 自定义 shiro 过滤器
     */
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager") SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setSuccessUrl("/index");
        shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized");

        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();

        // authc 表示只有登录后才有权限访问，anon 表示没有登录也有权限访问
        filterChainDefinitionMap.put("/index", "authc");
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/ajaxLogin", "anon");

        // 放行静态资源
        filterChainDefinitionMap.put("/static/assets/**", "anon");
        filterChainDefinitionMap.put("/static/css/**", "anon");
        filterChainDefinitionMap.put("/static/js/**", "anon");
        filterChainDefinitionMap.put("/static/lib/**", "anon");

        // 指定访问接口所需的具体角色和权限
        filterChainDefinitionMap.put("/system", "roles[admin]");
        filterChainDefinitionMap.put("/system/user/create", "perms[system:user:create]");

        // 开放其他所有请求，只要已登录或者rememberMe的用户都能访问
        filterChainDefinitionMap.put("/**", "user");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    /**
     * 以下的两个方法是设置 shiro 与 spring 之间的关联
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") SecurityManager securityManager) {
        // 配置spring使用自定义web安全管理器
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        // 为true代表以cglib动态代理方式生成代理类，默认false表示以JDK方式
        creator.setProxyTargetClass(true);
        return creator;
    }

}
