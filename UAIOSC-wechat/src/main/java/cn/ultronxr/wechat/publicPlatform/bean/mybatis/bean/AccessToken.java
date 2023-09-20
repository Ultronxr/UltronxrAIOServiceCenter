package cn.ultronxr.wechat.publicPlatform.bean.mybatis.bean;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author Ultronxr
 * @date 2023/09/20 12:03:29
 * @description
 */
@TableName("wechat_access_token")
@Data
public class AccessToken {

    @TableId(type = IdType.INPUT)
    private String appId;

    private String accessToken;

    private int expiresIn;

    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @TableField(fill = FieldFill.UPDATE)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

}
