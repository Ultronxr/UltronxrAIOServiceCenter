package cn.ultronxr.system.service;

import cn.ultronxr.system.bean.UserInfo;
import cn.ultronxr.system.bean.mybatis.bean.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Ultronxr
 * @date 2022/12/13 14:03
 * @description 用户信息 service
 */
public interface UserService extends IService<User> {

    List<User> queryUser(User user);

    User findUserByUsername(String username);

    /**
     * 根据登录 token 获取用户信息
     *
     * @param jwsToken 登录 token
     * @return 用户信息对象 {@link UserInfo}
     */
    UserInfo getUserInfo(String jwsToken);

    /**
     * 根据用户 ID 获取用户信息
     *
     * @param userId 用户ID
     * @return 用户信息对象 {@link UserInfo}
     */
    UserInfo getUserInfo(Long userId);

}
