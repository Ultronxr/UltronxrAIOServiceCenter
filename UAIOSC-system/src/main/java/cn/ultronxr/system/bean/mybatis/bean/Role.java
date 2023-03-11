package cn.ultronxr.system.bean.mybatis.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("system_role")
public class Role {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;

    private String code;

    private String note;

    private String createBy;

    private Date createTime;

    private String updateBy;

    private Date updateTime;

}