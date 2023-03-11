package cn.ultronxr.system.bean.mybatis.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@TableName("system_role_permission")
public class RolePermission {

    @MppMultiId
    private Long roleId;

    @MppMultiId
    private Long permissionId;


    @Override
    public String toString() {
        return "RolePermission{" +
                "roleId=" + roleId +
                ", permissionId=" + permissionId +
                '}';
    }
}