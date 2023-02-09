package cn.ultronxr.gameregister.controller;

import cn.ultronxr.common.bean.AjaxResponse;
import cn.ultronxr.common.util.AjaxResponseUtils;
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
        if(service.save(account)) {
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
    public AjaxResponse update(@RequestBody Account account) {
        if(service.updateById(account)) {
            return AjaxResponseUtils.success();
        }
        return AjaxResponseUtils.fail();
    }

    @PostMapping("query")
    @ResponseBody
    public AjaxResponse query(@RequestBody Account account) {
        List<Account> accountList = service.listAccount(account);
        AjaxResponse response = AjaxResponseUtils.success(null, accountList);
        response.put("count", accountList.size());
        return response;
    }

    @GetMapping("list")
    @ResponseBody
    public AjaxResponse list() {
        List<Account> accountList = service.listAccount(null);
        return AjaxResponseUtils.success(null, accountList);
    }

}
