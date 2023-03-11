package cn.ultronxr.system.bean.mybatis.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("system_user")
public class User implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String nick;

    private String username;

    private String password;

    private String avatar;

    private String note;

    private String createBy;

    private Date createTime;

    private String updateBy;

    private Date updateTime;

}