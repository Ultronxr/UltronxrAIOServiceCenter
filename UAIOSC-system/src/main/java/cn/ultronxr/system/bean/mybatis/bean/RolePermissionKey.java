package cn.ultronxr.system.bean.mybatis.bean;

public class RolePermissionKey {
    private Long roleId;

    private Long permissionId;

    public RolePermissionKey() {
    }

    public RolePermissionKey(Long roleId, Long permissionId) {
        this.roleId = roleId;
        this.permissionId = permissionId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }

    @Override
    public String toString() {
        return "RolePermissionKey{" +
                "roleId=" + roleId +
                ", permissionId=" + permissionId +
                '}';
    }
}