package cn.ultronxr.web.service;

import cn.ultronxr.system.bean.mybatis.bean.User;
import cn.ultronxr.web.bean.LoginVO;

/**
 * @author Ultronxr
 * @date 2023/01/13 15:12
 * @description
 */
public interface LoginService {

    /**
     * 用户登录逻辑，只对用户名和密码进行校验
     *
     * @param loginVO 用户登录时请求的数据封装
     * @return 校验成功：返回数据库查询出来的对应 {@code User} 对象<br/>
     *          校验失败：返回 {@code null}
     */
    User login(LoginVO loginVO);

}
