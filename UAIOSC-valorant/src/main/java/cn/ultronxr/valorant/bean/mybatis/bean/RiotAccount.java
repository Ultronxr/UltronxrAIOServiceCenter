package cn.ultronxr.valorant.bean.mybatis.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Ultronxr
 * @date 2023/02/22 15:02
 * @description
 */
@Data
@TableName("valorant_riot_account")
public class RiotAccount {

    @TableId(type = IdType.INPUT)
    private String userId;

    private String username;

    private String password;

    private String socialName;

    private String socialTag;

    private String accessToken;

    private String entitlementsToken;

    private String multiFactor;

    private Boolean isVerified;

}
