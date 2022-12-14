package cn.ultronxr.gameregister.controller;

import cn.ultronxr.common.bean.AjaxResponse;
import cn.ultronxr.gameregister.bean.mybatis.bean.Account;
import cn.ultronxr.gameregister.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private AccountService service;


    @PostMapping("create")
    @ResponseBody
    public AjaxResponse create(@RequestBody Account account) {
        if(service.create(account) > 0) {
            return AjaxResponse.success();
        }
        return AjaxResponse.fail();
    }

    @GetMapping("delete")
    @ResponseBody
    public AjaxResponse delete(@RequestParam List<Integer> idList) {
        if(service.delete(idList) > 0) {
            return AjaxResponse.success();
        }
        return AjaxResponse.fail();
    }

    @PostMapping("update")
    @ResponseBody
    public AjaxResponse update(@RequestBody Account account) {
        if(service.update(account) > 0) {
            return AjaxResponse.success();
        }
        return AjaxResponse.fail();
    }

    @PostMapping("query")
    @ResponseBody
    public AjaxResponse query(@RequestBody Account account) {
        List<Account> accountList = service.query(account);
        AjaxResponse response = AjaxResponse.success(null, accountList);
        response.put("count", accountList.size());
        return response;
    }

}
