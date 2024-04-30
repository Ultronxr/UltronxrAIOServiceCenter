package cn.ultronxr.tool.controller;

import cn.ultronxr.tool.service.RandomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Ultronxr
 * @date 2024/04/28 10:01:51
 * @description 随机工具 controller
 */
@Controller
@RequestMapping("/tool/random")
@Slf4j
public class RandomController {

    private final RandomService randomService;


    public RandomController(RandomService randomService) {
        this.randomService = randomService;
    }

    @GetMapping("/string")
    @ResponseBody
    public String randomString() {
        return randomService.randomString(null);
    }

    @GetMapping("/string/{length}")
    @ResponseBody
    public String randomString(@PathVariable(required = false) Integer length) {
        return randomService.randomString(length);
    }

}
