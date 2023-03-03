package cn.ultronxr.valorant.bean.mybatis.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Ultronxr
 * @date 2023/02/20 18:33
 * @description
 */
@Data
@TableName("valorant_weapon_skin")
public class WeaponSkin {

    @TableId(type = IdType.INPUT)
    private String uuid;

    private String type;

    private String parentWeaponUuid;

    private String parentSkinUuid;

    private String assetPath;

    private String contentTierUuid;

    private String themeUuid;

    private String displayName;

    private String swatch;

    private String displayIcon;

    private String fullRender;

    private String wallpaper;

    private String streamedVideo;

}
