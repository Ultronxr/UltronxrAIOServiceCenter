package cn.ultronxr.system.service.impl;

import cn.hutool.core.date.CalendarUtil;
import cn.hutool.db.sql.Condition;
import cn.hutool.db.sql.SqlUtil;
import cn.ultronxr.system.bean.mybatis.bean.User;
import cn.ultronxr.system.bean.mybatis.bean.UserExample;
import cn.ultronxr.system.bean.mybatis.mapper.UserMapper;
import cn.ultronxr.system.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Ultronxr
 * @date 2022/12/13 14:04
 * @description
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;


    @Override
    public int createUser(User user) {
        user.setId(null);
        user.setCreateBy("");
        user.setCreateTime(CalendarUtil.calendar().getTime());
        return userMapper.insertSelective(user);
    }

    @Override
    public int updateUser(User user) {
        user.setUpdateBy("");
        user.setUpdateTime(CalendarUtil.calendar().getTime());
        return userMapper.updateByPrimaryKey(user);
    }

    @Override
    public int deleteUser(Long userId) {
        return userMapper.deleteByPrimaryKey(userId);
    }

    @Override
    public List<User> queryUser(User user) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();

        if(StringUtils.isNotEmpty(user.getNick())) {
            criteria.andNickLike(SqlUtil.buildLikeValue(user.getNick(), Condition.LikeType.Contains, false));
        }
        if(StringUtils.isNotEmpty(user.getUsername())) {
            criteria.andNickLike(SqlUtil.buildLikeValue(user.getUsername(), Condition.LikeType.Contains, false));
        }
        if(StringUtils.isNotEmpty(user.getNote())) {
            criteria.andNickLike(SqlUtil.buildLikeValue(user.getNote(), Condition.LikeType.Contains, false));
        }

        return userMapper.selectByExample(example);
    }

    @Override
    public User findUserByUsername(String username) {
        UserExample example = new UserExample();
        example.createCriteria().andUsernameEqualTo(username);
        List<User> list = userMapper.selectByExample(example);
        if(CollectionUtils.isNotEmpty(list)) {
            return list.get(0);
        }
        return null;
    }
}
