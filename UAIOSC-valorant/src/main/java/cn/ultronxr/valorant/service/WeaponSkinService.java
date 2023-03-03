package cn.ultronxr.valorant.service;

import cn.ultronxr.valorant.bean.mybatis.bean.WeaponSkin;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Ultronxr
 * @date 2023/02/21 17:37
 * @description
 */
public interface WeaponSkinService extends IService<WeaponSkin> {

    List<WeaponSkin> getSkins(String weaponId);

    List<WeaponSkin> getLevels(String skinId);

    List<WeaponSkin> getChromas(String skinId);

    /**
     * 通过 皮肤升级ID或炫彩ID 查找对应的 父皮肤ID <br/>
     * （对应关系为：武器ID ==> 皮肤ID ==> 升级ID/炫彩ID）<br/>
     *                            ↑             |      <br/>
     *                            --------------       <br/>
     *
     * @param levelOrChromaId 皮肤升级ID或炫彩ID
     * @return 父皮肤ID
     */
    String getParentSkinIdByLevelOrChromaId(String levelOrChromaId);

    /**
     * 通过 皮肤ID 查找对应的 父武器ID <br/>
     * （对应关系为：武器ID ==> 皮肤ID ==> 升级ID/炫彩ID）<br/>
     *                ↑          |             |       <br/>
     *                --------------------------       <br/>
     *
     * @param skinId 皮肤ID（或者 升级ID/炫彩ID 也是可以的）
     * @return 父武器ID
     */
    String getParentWeaponIdBySkinId(String skinId);

}
