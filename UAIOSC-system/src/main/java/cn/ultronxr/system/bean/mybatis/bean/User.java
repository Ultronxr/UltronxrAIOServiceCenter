package cn.ultronxr.system.bean.mybatis.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("system_user")
public class User implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String nick;

    private String username;

    @JsonIgnore
    private String password;

    private String avatar;

    private String note;

    private String createBy;

    private Date createTime;

    private String updateBy;

    private Date updateTime;

    public User(@NotNull User user) {
        this.id = user.getId();
        this.nick = user.getNick();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.avatar = user.getAvatar();
        this.note = user.getNote();
        this.createBy = user.getCreateBy();
        this.createTime = user.getCreateTime();
        this.updateBy = user.getUpdateBy();
        this.updateTime = user.getUpdateTime();
    }

}