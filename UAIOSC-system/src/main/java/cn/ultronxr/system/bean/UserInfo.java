package cn.ultronxr.system.bean;

import cn.ultronxr.system.bean.mybatis.bean.Role;
import cn.ultronxr.system.bean.mybatis.bean.User;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Ultronxr
 * @date 2024/05/03 17:04:34
 * @description 用户信息
 */
@Data
public class UserInfo extends User implements Serializable {

    public List<Role> roleList;

}
