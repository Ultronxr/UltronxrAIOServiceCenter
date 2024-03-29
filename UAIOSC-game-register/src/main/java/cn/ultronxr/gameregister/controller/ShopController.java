package cn.ultronxr.gameregister.controller;

import cn.ultronxr.common.bean.AjaxResponse;
import cn.ultronxr.common.util.AjaxResponseUtils;
import cn.ultronxr.gameregister.bean.mybatis.bean.Shop;
import cn.ultronxr.gameregister.service.ShopService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Ultronxr
 * @date 2022/12/29 16:42
 * @description
 */
@Controller
@RequestMapping("/game-register/shop")
@Slf4j
public class ShopController {

    @Autowired
    private ShopService service;


    @PostMapping("create")
    @ResponseBody
    public AjaxResponse create(@RequestBody Shop shop) {
        if(service.save(shop)) {
            return AjaxResponseUtils.success();
        }
        return AjaxResponseUtils.fail();
    }

    @DeleteMapping("delete")
    @ResponseBody
    public AjaxResponse delete(@RequestParam List<Integer> idList) {
        if(service.removeByIds(idList)) {
            return AjaxResponseUtils.success();
        }
        return AjaxResponseUtils.fail();
    }

    @PutMapping("update")
    @ResponseBody
    public AjaxResponse update(@RequestBody Shop shop) {
        if(service.updateById(shop)) {
            return AjaxResponseUtils.success();
        }
        return AjaxResponseUtils.fail();
    }

    @PostMapping("query")
    @ResponseBody
    public AjaxResponse query(@RequestBody Shop shop) {
        List<Shop> shopList = service.listShop(shop);
        return AjaxResponseUtils.success(null, shopList);
    }

    @GetMapping("list")
    @ResponseBody
    public AjaxResponse list() {
        List<Shop> shopList = service.listShop(null);
        return AjaxResponseUtils.success(null, shopList);
    }

}
