package cn.ultronxr.gameregister.controller;

import cn.ultronxr.common.bean.AjaxResponse;
import cn.ultronxr.common.util.AjaxResponseUtils;
import cn.ultronxr.gameregister.bean.mybatis.bean.Platform;
import cn.ultronxr.gameregister.service.PlatformService;
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
@RequestMapping("/game-register/platform")
@Slf4j
public class PlatformController {

    @Autowired
    private PlatformService service;


    @PostMapping("create")
    @ResponseBody
    public AjaxResponse create(@RequestBody Platform platform) {
        if(service.create(platform) > 0) {
            return AjaxResponseUtils.success();
        }
        return AjaxResponseUtils.fail();
    }

    @GetMapping("delete")
    @ResponseBody
    public AjaxResponse delete(@RequestParam("id") String id) {
        if(service.delete(id) > 0) {
            return AjaxResponseUtils.success();
        }
        return AjaxResponseUtils.fail();
    }

    @PostMapping("update")
    @ResponseBody
    public AjaxResponse update(@RequestBody Platform platform) {
        if(service.update(platform) > 0) {
            return AjaxResponseUtils.success();
        }
        return AjaxResponseUtils.fail();
    }

    @PostMapping("query")
    @ResponseBody
    public AjaxResponse query(@RequestBody Platform platform) {
        List<Platform> platformList = service.query(platform);
        return AjaxResponseUtils.success(null, platformList);
    }

    @GetMapping("list")
    @ResponseBody
    public AjaxResponse list() {
        List<Platform> platformList = service.query(null);
        return AjaxResponseUtils.success(null, platformList);
    }

}
