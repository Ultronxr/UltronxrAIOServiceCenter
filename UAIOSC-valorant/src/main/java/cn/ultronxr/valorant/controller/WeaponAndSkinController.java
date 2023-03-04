package cn.ultronxr.valorant.controller;

import cn.ultronxr.common.bean.AjaxResponse;
import cn.ultronxr.common.util.AjaxResponseUtils;
import cn.ultronxr.valorant.api.impl.WeaponAndSkinAPI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Ultronxr
 * @date 2023/02/20 18:54
 * @description
 */
@Controller
@RequestMapping("/valorant/weaponAndSkinAPI")
@Slf4j
public class WeaponAndSkinController {

    @Autowired
    private WeaponAndSkinAPI weaponAndSkinAPI;


    @RequestMapping("updateDB")
    @ResponseBody
    public AjaxResponse process() {
        weaponAndSkinAPI.processAndUpdateDB();
        return AjaxResponseUtils.success();
    }

}
