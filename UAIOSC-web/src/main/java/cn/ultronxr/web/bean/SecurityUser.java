package cn.ultronxr.web.bean;

import cn.ultronxr.system.bean.mybatis.bean.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * @author Ultronxr
 * @date 2023/03/11 11:00
 * @description SpringSecurity 流程中的用户信息对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SecurityUser extends User implements UserDetails {

    private List<GrantedAuthority> authorities;

    public SecurityUser(User user) {
        super(user);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public String getUsername() {
        return super.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
