package cn.ultronxr.tool.controller;

import cn.ultronxr.common.bean.AjaxResponse;
import cn.ultronxr.common.util.AjaxResponseUtils;
import cn.ultronxr.tool.bean.FinalShellCrackActivateKey;
import cn.ultronxr.tool.service.FinalShellCrackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Ultronxr
 * @date 2024/04/28 18:59:52
 * @description
 */
@Controller
@RequestMapping("/finalShellCrack")
@Slf4j
public class FinalShellCrackController {

    private final FinalShellCrackService service;

    public FinalShellCrackController(FinalShellCrackService service) {
        this.service = service;
    }

    @RequestMapping("/getActivateKey")
    @ResponseBody
    public AjaxResponse getActivateKey(String machineCode) {
        FinalShellCrackActivateKey key = service.getActivateKey(machineCode);
        return AjaxResponseUtils.success(key);
    }

}
