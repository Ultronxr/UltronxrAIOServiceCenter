package cn.ultronxr.gameregister.controller;

import cn.ultronxr.gameregister.bean.mybatis.bean.Account;
import cn.ultronxr.gameregister.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Ultronxr
 * @date 2022/12/10 19:09
 * @description
 */
@Controller
@RequestMapping("/game-register/account")
@Slf4j
public class AccountController {

    @Resource
    private AccountService service;


    @PostMapping("create")
    @ResponseBody
    public String createAccount(@RequestBody Account account) {
        if(service.createAccount(account) > 0) {
            return "创建成功。";
        }
        return "创建失败！";
    }

    @GetMapping("delete")
    @ResponseBody
    public String deleteAccount(@RequestParam("id") String id) {
        if(service.deleteAccount(Integer.parseInt(id)) > 0) {
            return "删除成功。";
        }
        return "删除失败！";
    }

    @PostMapping("update")
    @ResponseBody
    public String updateAccount(@RequestBody Account account) {
        if(service.updateAccount(account) > 0) {
            return "更新成功。";
        }
        return "更新失败！";
    }

    @PostMapping("query")
    @ResponseBody
    public List<Account> queryAccount(@RequestBody Account account) {
        return service.queryAccount(account);
    }

}
