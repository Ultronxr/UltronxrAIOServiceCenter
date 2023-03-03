package cn.ultronxr.valorant.bean.mybatis.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Ultronxr
 * @date 2023/02/20 18:32
 * @description
 */
@Data
@TableName("valorant_weapon")
public class Weapon {

    @TableId(type = IdType.INPUT)
    private String uuid;

    private String assetPath;

    private String category;

    private String defaultSkinUuid;

    private String displayName;

    private String displayIcon;

    private String killStreamIcon;

}
