package cn.ultronxr.valorant.controller;

import cn.ultronxr.common.bean.AjaxResponse;
import cn.ultronxr.common.util.AjaxResponseUtils;
import cn.ultronxr.valorant.service.StoreFrontService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author Ultronxr
 * @date 2023/02/22 11:11
 * @description
 */
@Controller
@RequestMapping("/valorant/storefront")
@Slf4j
public class StoreFrontController {

    @Autowired
    private StoreFrontService sfService;


    @GetMapping("/singleItemOffers")
    @ResponseBody
    public AjaxResponse singleItemOffers(String userId, String date) {
        if(StringUtils.isEmpty(userId) || StringUtils.isEmpty(date)) {
            return AjaxResponseUtils.success();
        }
        return AjaxResponseUtils.success(sfService.toVO(sfService.singleItemOffers(userId, date)));
    }

    @GetMapping("/bonusOffers")
    @ResponseBody
    public AjaxResponse bonusOffers(String userId, String date) {
        if(StringUtils.isEmpty(userId) || StringUtils.isEmpty(date)) {
            return AjaxResponseUtils.success();
        }
        return AjaxResponseUtils.success(sfService.toVO(sfService.bonusOffers(userId, date)));
    }

}
