package cn.ultronxr.system.bean.mybatis.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("system_permission")
public class Permission {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String parentId;

    private String permissionName;

    private String permissionCode;

    private String menu;

    private String menuType;

    private Integer menuSort;

    private String request;

    private String note;

    private String createBy;

    private Date createTime;

    private String updateBy;

    private Date updateTime;

}