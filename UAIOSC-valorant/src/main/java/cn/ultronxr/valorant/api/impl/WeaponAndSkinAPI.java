package cn.ultronxr.valorant.api.impl;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.ultronxr.valorant.api.BaseAPI;
import cn.ultronxr.valorant.api.ValorantDotComAPIEnum;
import cn.ultronxr.valorant.bean.mybatis.bean.Weapon;
import cn.ultronxr.valorant.bean.mybatis.bean.WeaponSkin;
import cn.ultronxr.valorant.service.WeaponService;
import cn.ultronxr.valorant.service.WeaponSkinService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Ultronxr
 * @date 2023/02/20 18:21
 * @description <b>武器信息</b> 及 <b>武器皮肤（包含皮肤、升级、炫彩）</b>API
 */
@Component
@Slf4j
public class WeaponAndSkinAPI extends BaseAPI {

    private static final String WEAPON_API = ValorantDotComAPIEnum.Weapon.getUrl();

    @Autowired
    private WeaponService weaponService;

    @Autowired
    private WeaponSkinService weaponSkinService;


    @Transactional(rollbackFor = Exception.class)
    public void processAndUpdateDB() {
        String responseBody = requestAPI(WEAPON_API, null);
        if(StringUtils.isEmpty(responseBody)) {
            return;
        }

        JSONArray dataArray = parseData(responseBody);
        if(null == dataArray) {
            return;
        }
        parseWeapon(dataArray);
    }

    /**
     * 把请求 API 返回的数据的 json "data" 节点解析成 JSONArray
     */
    @Override
    public JSONArray parseData(String responseBody) {
        JSONObject rootObj = JSONUtil.parseObj(responseBody, false);
        if(!rootObj.get("status", String.class).equals("200")) {
            return null;
        }
        // 获取 "data" 节点
        return rootObj.getJSONArray("data");
    }

    /**
     * 解析武器信息数据
     */
    private void parseWeapon(JSONArray dataArray) {
        if(null == dataArray || dataArray.isEmpty()) {
            return;
        }

        dataArray.parallelStream().forEach(obj -> {
            JSONObject jObj = (JSONObject) obj;
            Weapon weapon = JSONUtil.toBean(jObj, Weapon.class);
            weaponService.saveOrUpdate(weapon);

            JSONArray skinArray = jObj.getJSONArray("skins");
            parseWeaponSkin(skinArray, weapon.getUuid());
        });
    }

    /**
     * 解析武器的皮肤数据
     */
    private void parseWeaponSkin(JSONArray skinArray, String parentWeaponID) {
        if(null == skinArray || skinArray.isEmpty()) {
            return;
        }

        skinArray.parallelStream().forEach(obj -> {
            JSONObject jObj = (JSONObject) obj;
            WeaponSkin skin = JSONUtil.toBean(jObj, WeaponSkin.class);
            skin.setType("skin");
            skin.setParentWeaponUuid(parentWeaponID);
            weaponSkinService.saveOrUpdate(skin);

            JSONArray levelArray = jObj.getJSONArray("levels"),
                    chromaArray = jObj.getJSONArray("chromas");
            parseWeaponSkinLevelOrChroma(levelArray, parentWeaponID, skin.getUuid(), "level");
            parseWeaponSkinLevelOrChroma(chromaArray, parentWeaponID, skin.getUuid(), "chroma");
        });
    }

    /**
     * 解析武器皮肤包含的升级、炫彩数据
     */
    private void parseWeaponSkinLevelOrChroma(JSONArray levelArray, String parentWeaponID, String parentSkinID, String type) {
        if(null == levelArray || levelArray.isEmpty()) {
            return;
        }

        levelArray.parallelStream().forEach(obj -> {
            WeaponSkin levelOrChroma = JSONUtil.toBean((JSONObject) obj, WeaponSkin.class);
            levelOrChroma.setType(type);
            levelOrChroma.setParentWeaponUuid(parentWeaponID);
            levelOrChroma.setParentSkinUuid(parentSkinID);
            weaponSkinService.saveOrUpdate(levelOrChroma);
        });
    }

}
