package cn.ultronxr.gameregister.controller;

import cn.ultronxr.common.bean.AjaxResponse;
import cn.ultronxr.gameregister.bean.mybatis.bean.Game;
import cn.ultronxr.gameregister.service.GameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public AjaxResponse createGame(@RequestBody Game game) {
        if(gameService.createGame(game) > 0) {
            return AjaxResponse.success();
        }
        return AjaxResponse.fail();
    }

    @GetMapping("delete")
    @ResponseBody
    public AjaxResponse deleteGame(@RequestParam("id") String id) {
        if(gameService.deleteGame(Long.parseLong(id)) > 0) {
            return AjaxResponse.success();
        }
        return AjaxResponse.fail();
    }

    @PostMapping("update")
    @ResponseBody
    public AjaxResponse updateGame(@RequestBody Game game) {
        if(gameService.updateGame(game) > 0) {
            return AjaxResponse.success();
        }
        return AjaxResponse.fail();
    }

    @PostMapping("query")
    @ResponseBody
    public AjaxResponse queryAccount(@RequestBody Game game) {
        List<Game> gameList = gameService.queryGame(game);
        return AjaxResponse.success(null, gameList);
    }

}
