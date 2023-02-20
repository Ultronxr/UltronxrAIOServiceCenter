package cn.ultronxr.gameregister.controller;

import cn.ultronxr.common.bean.AjaxResponse;
import cn.ultronxr.common.util.AjaxResponseUtils;
import cn.ultronxr.gameregister.bean.mybatis.bean.Game;
import cn.ultronxr.gameregister.service.GameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author Ultronxr
 * @date 2022/12/10 19:10
 * @description
 */
@Controller
@RequestMapping("/game-register/game")
@Slf4j
public class GameController {

    @Autowired
    private GameService gameService;


    @PostMapping("create")
    @ResponseBody
    public AjaxResponse create(@RequestBody Game game) {
        if(gameService.save(game)) {
            return AjaxResponseUtils.success();
        }
        return AjaxResponseUtils.fail();
    }

    @DeleteMapping("delete")
    @ResponseBody
    public AjaxResponse delete(@RequestParam List<Integer> idList) {
        if(gameService.removeByIds(idList)) {
            return AjaxResponseUtils.success();
        }
        return AjaxResponseUtils.fail();
    }

    @PutMapping("update")
    @ResponseBody
    public AjaxResponse update(@RequestBody Game game) {
        if(gameService.updateById(game)) {
            return AjaxResponseUtils.success();
        }
        return AjaxResponseUtils.fail();
    }

    @PostMapping("query")
    @ResponseBody
    public AjaxResponse query(@RequestBody Game game) {
        List<Game> gameList = gameService.list(game);
        return AjaxResponseUtils.success(null, gameList);
    }

    @PostMapping("import/json")
    @ResponseBody
    public AjaxResponse importJson(String accountId, String shopId, MultipartFile file) {
        if(null == file) {
            return AjaxResponseUtils.fail("未上传文件！");
        }
        if(gameService.importJson(accountId, shopId, file)) {
            return AjaxResponseUtils.success();
        }
        return AjaxResponseUtils.fail("使用json文件 导入游戏失败！");
    }

    @PostMapping("import/api")
    @ResponseBody
    public AjaxResponse importAPI(String accountId, String shopId) {
        if(gameService.importAPI(accountId, shopId)) {
            return AjaxResponseUtils.success();
        }
        return AjaxResponseUtils.fail("请求官方API 导入游戏失败！");
    }

}
