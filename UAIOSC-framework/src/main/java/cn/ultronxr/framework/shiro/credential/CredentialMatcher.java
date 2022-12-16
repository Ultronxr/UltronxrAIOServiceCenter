package cn.ultronxr.framework.shiro.credential;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

/**
 * @author Ultronxr
 * @date 2022/12/14 22:45
 * @description 自定义密码匹配器
 */
public class CredentialMatcher extends SimpleCredentialsMatcher {

    /**
     * 自定义密码匹配流程
     *
     * @param token subject （用户）提交登录请求时生成的认证 {@code AuthenticationToken}
     * @param info  数据库查询出来的包含对应正确 subject 信息的 {@code AuthenticationInfo}
     * @return true - 密码匹配；false - 密码不匹配
     */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        // 获取用户提交登录请求的 token 中的密码
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        String password = new String(usernamePasswordToken.getPassword());
        // 获取数据库中的正确密码
        String dbPassword = (String) info.getCredentials();
        // 检查两者是否一致
        return this.equals(password, dbPassword);
    }

}
