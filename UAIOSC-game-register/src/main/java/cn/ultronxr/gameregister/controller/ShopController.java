package cn.ultronxr.gameregister.controller;

import cn.ultronxr.common.bean.AjaxResponse;
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
        if(service.create(shop) > 0) {
            return AjaxResponse.success();
        }
        return AjaxResponse.fail();
    }

    @GetMapping("delete")
    @ResponseBody
    public AjaxResponse delete(@RequestParam("id") String id) {
        if(service.delete(id) > 0) {
            return AjaxResponse.success();
        }
        return AjaxResponse.fail();
    }

    @PostMapping("update")
    @ResponseBody
    public AjaxResponse update(@RequestBody Shop shop) {
        if(service.update(shop) > 0) {
            return AjaxResponse.success();
        }
        return AjaxResponse.fail();
    }

    @PostMapping("query")
    @ResponseBody
    public AjaxResponse query(@RequestBody Shop shop) {
        List<Shop> shopList = service.query(shop);
        return AjaxResponse.success(null, shopList);
    }

    @GetMapping("list")
    @ResponseBody
    public AjaxResponse list() {
        List<Shop> shopList = service.query(null);
        return AjaxResponse.success(null, shopList);
    }

}
