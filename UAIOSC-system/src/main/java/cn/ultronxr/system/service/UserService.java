package cn.ultronxr.system.service;

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

}
