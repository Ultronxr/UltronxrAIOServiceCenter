package cn.ultronxr.valorant.bean.VO;

import cn.ultronxr.valorant.bean.mybatis.bean.StoreFront;
import cn.ultronxr.valorant.bean.mybatis.bean.WeaponSkin;
import lombok.Data;

import java.util.List;

/**
 * @author Ultronxr
 * @date 2023/02/23 10:54
 * @description
 */
@Data
public class StoreFrontVO extends StoreFront {

    /**
     * 每日商店物品对应的本身皮肤具体信息
     */
    private WeaponSkin weaponSkin;

    /**
     * 对应的皮肤的皮肤升级
     */
    private List<WeaponSkin> weaponSkinLevels;

    /**
     * 对应的皮肤的皮肤炫彩
     */
    private List<WeaponSkin> weaponSkinChromas;

    public StoreFrontVO toVO(StoreFront sf) {
        setUserId(sf.getUserId());
        setOfferId(sf.getOfferId());
        setDate(sf.getDate());
        setEndDate(sf.getEndDate());
        setIsDirectPurchase(sf.getIsDirectPurchase());
        setCurrencyId(sf.getCurrencyId());
        setCost(sf.getCost());
        setItemTypeId(sf.getItemTypeId());
        setItemId(sf.getItemId());
        setQuantity(sf.getQuantity());
        setDiscountPercent(sf.getDiscountPercent());
        setDiscountCost(sf.getDiscountCost());
        setIsBonus(sf.getIsBonus());
        return this;
    }

}
