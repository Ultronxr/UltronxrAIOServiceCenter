package cn.ultronxr.system.bean.mybatis.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

@Data
@TableName("system_menu")
public class Menu {

    @TableId(type = IdType.INPUT)
    private String id;

    private String parentId;

    private String name;

    private Integer type;

    private String path;

    private String redirect;

    private String component;

    private String permissionCode;

    private Boolean isEnabled;

    private Boolean isLink;

    private Boolean isCached;

    @TableField(exist = false)
    private MenuMeta meta;

    @TableField(exist = false)
    private List<Menu> children;

}