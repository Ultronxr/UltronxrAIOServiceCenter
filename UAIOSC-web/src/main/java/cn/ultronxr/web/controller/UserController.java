package cn.ultronxr.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

/**
 * @author Ultronxr
 * @date 2022/12/11 17:22
 * @description
 */
@Controller
@RequestMapping("/web/user")
public class UserController {

    @RequestMapping("login")
    @ResponseBody
    public String login(@RequestBody HashMap<String, Object> requestMap) {
        return "login OK";
    }

}
