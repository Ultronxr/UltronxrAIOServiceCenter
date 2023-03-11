package cn.ultronxr.system.bean.mybatis.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import lombok.Data;

@Data
@TableName("system_user_role")
public class UserRole {

    @MppMultiId
    private Long userId;

    @MppMultiId
    private Long roleId;

}