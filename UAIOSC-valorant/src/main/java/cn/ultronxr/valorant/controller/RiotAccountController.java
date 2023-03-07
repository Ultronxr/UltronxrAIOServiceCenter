package cn.ultronxr.valorant.controller;

import cn.hutool.http.HttpRequest;
import cn.ultronxr.common.bean.AjaxResponse;
import cn.ultronxr.common.util.AjaxResponseUtils;
import cn.ultronxr.valorant.bean.mybatis.bean.RiotAccount;
import cn.ultronxr.valorant.exception.RSOMultiFactorAttemptFailedException;
import cn.ultronxr.valorant.service.RSOService;
import cn.ultronxr.valorant.service.RiotAccountService;
import cn.ultronxr.valorant.util.RSOUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * @author Ultronxr
 * @date 2023/02/23 10:51
 * @description
 */
@Controller
@RequestMapping("/valorant/account")
@Slf4j
public class RiotAccountController {

    @Autowired
    private RiotAccountService accountService;

    @Autowired
    private RSOService rsoService;


    @PostMapping("/multiFactor")
    @ResponseBody
    public AjaxResponse multiFactor(@RequestBody RiotAccount account) {
        log.info("RSO获取邮箱验证码：username = {}", account.getUsername());
        try {
            HttpRequest request = HttpRequest.post(RSOUtils.AUTH_URL);
            rsoService.processRSO(request, account.getUsername(), account.getPassword(), null);
        } catch (Exception e) {
            if(e instanceof RSOMultiFactorAttemptFailedException) {
                log.info("邮箱验证码发送成功");
                return AjaxResponseUtils.success("验证码发送成功");
            }
            log.info("拳头账号验证失败！");
            return AjaxResponseUtils.fail("拳头账号验证失败");
        }
        return AjaxResponseUtils.fail();
    }

    @PostMapping("/create")
    @ResponseBody
    public AjaxResponse create(@RequestBody RiotAccount account) {
        if(accountService.create(account)) {
            return AjaxResponseUtils.success();
        }
        return AjaxResponseUtils.fail("拳头账号验证失败！");
    }

    @DeleteMapping("/delete")
    @ResponseBody
    public AjaxResponse delete(@RequestParam String[] idList) {
        if(accountService.removeByIds(Arrays.asList(idList))) {
            return AjaxResponseUtils.success();
        }
        return AjaxResponseUtils.fail();
    }

    //@PutMapping("/update")
    //@ResponseBody
    //public AjaxResponse update(RiotAccount account) {
    //    if(accountService.update(account)) {
    //        return AjaxResponseUtils.success();
    //    }
    //    return AjaxResponseUtils.fail("拳头账户验证失败！");
    //}

    @PostMapping("/query")
    @ResponseBody
    public AjaxResponse query(@RequestBody RiotAccount account) {
        return AjaxResponseUtils.success(accountService.queryAccount(account));
    }

    @GetMapping("/select")
    @ResponseBody
    public AjaxResponse select() {
        return AjaxResponseUtils.success(accountService.queryAccount(null));
    }

}
