package cn.ultronxr.web.service.impl;

import cn.ultronxr.framework.jjwt.JJWTService;
import cn.ultronxr.framework.jjwt.TokenService;
import cn.ultronxr.system.bean.mybatis.bean.User;
import cn.ultronxr.system.service.UserService;
import cn.ultronxr.web.bean.LoginVO;
import cn.ultronxr.web.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author Ultronxr
 * @date 2023/01/13 15:12
 * @description
 */
@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserService userService;

    @Autowired
    private JJWTService jjwtService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;


    @Override
    public User login(LoginVO loginVO) {
        User user = userService.findUserByUsername(loginVO.getUsername());
        if(null != user && user.getPassword().equals(loginVO.getPassword())) {
            return user;
        }
        return null;
    }

}
