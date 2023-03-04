package cn.ultronxr.valorant.service.impl;

import cn.ultronxr.valorant.bean.mybatis.bean.WeaponSkin;
import cn.ultronxr.valorant.bean.mybatis.mapper.WeaponSkinMapper;
import cn.ultronxr.valorant.service.WeaponSkinService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Ultronxr
 * @date 2023/02/21 11:25
 * @description
 */
@Service
@Slf4j
public class WeaponSkinServiceImpl extends ServiceImpl<WeaponSkinMapper, WeaponSkin> implements WeaponSkinService {

    @Autowired
    private WeaponSkinMapper wsMapper;


    public List<WeaponSkin> getSkins(String weaponId) {
        LambdaQueryWrapper<WeaponSkin> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(WeaponSkin::getParentWeaponUuid, weaponId)
                .eq(WeaponSkin::getType, "skin")
                .orderByAsc(WeaponSkin::getDisplayName);
        return wsMapper.selectList(wrapper);
    }

    public List<WeaponSkin> getLevels(String skinId) {
        LambdaQueryWrapper<WeaponSkin> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(WeaponSkin::getParentSkinUuid, skinId)
                .eq(WeaponSkin::getType, "level")
                .orderByAsc(WeaponSkin::getDisplayName);
        return wsMapper.selectList(wrapper);
    }

    public List<WeaponSkin> getChromas(String skinId) {
        LambdaQueryWrapper<WeaponSkin> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(WeaponSkin::getParentSkinUuid, skinId)
                .eq(WeaponSkin::getType, "chroma")
                .orderByAsc(WeaponSkin::getDisplayName);
        return wsMapper.selectList(wrapper);
    }

    @Override
    public String getParentSkinIdByLevelOrChromaId(String levelOrChromaId) {
        LambdaQueryWrapper<WeaponSkin> wrapper = Wrappers.lambdaQuery();
        wrapper.in(WeaponSkin::getType, "level", "chroma")
                .eq(WeaponSkin::getUuid, levelOrChromaId);
        return this.getOne(wrapper).getParentSkinUuid();
    }

    @Override
    public String getParentWeaponIdBySkinId(String skinId) {
        LambdaQueryWrapper<WeaponSkin> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(WeaponSkin::getUuid, skinId);
        return this.getOne(wrapper).getParentWeaponUuid();
    }

}
