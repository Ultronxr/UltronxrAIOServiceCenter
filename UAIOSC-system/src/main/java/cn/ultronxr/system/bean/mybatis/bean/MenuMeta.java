package cn.ultronxr.system.bean.mybatis.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@TableName("system_menu_meta")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MenuMeta {

    @TableId(type = IdType.INPUT)
    private String id;

    private String title;

    private Integer orderNo;

    private Integer dynamicLevel;

    private String icon;

    private String realPath;

    private String roles;

    private String frameSrc;

    private String transitionName;

    private String currentActiveMenu;

    private Byte hideMenu;

    private Byte hideChildrenInMenu;

    private Byte hideBreadcrumb;

    private Byte hideTab;

    private Byte ignoreAuth;

    private Byte ignoreKeepAlive;

    private Byte ignoreRoute;

    private Byte affix;

    private Byte carryParam;

    private Byte hidePathForChildren;

}