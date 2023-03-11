package cn.ultronxr.system.service.impl;

import cn.ultronxr.system.bean.mybatis.bean.User;
import cn.ultronxr.system.bean.mybatis.mapper.UserMapper;
import cn.ultronxr.system.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Ultronxr
 * @date 2022/12/13 14:04
 * @description
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public List<User> queryUser(User user) {
        LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery();
        if(user != null) {
            wrapper.like(StringUtils.isNotEmpty(user.getNick()), User::getNick, user.getNick())
                    .like(StringUtils.isNotEmpty(user.getUsername()), User::getUsername, user.getUsername())
                    .like(StringUtils.isNotEmpty(user.getNote()), User::getNote, user.getNote());
        }
        return userMapper.selectList(wrapper);
    }

    @Override
    public User findUserByUsername(String username) {
        List<User> list = userMapper.selectList(
                Wrappers.lambdaQuery(User.class)
                        .eq(User::getUsername, username)
        );
        if(CollectionUtils.isNotEmpty(list)) {
            return list.get(0);
        }
        return null;
    }
}
