package cn.ultronxr.system.controller;

import cn.ultronxr.common.bean.AjaxResponse;
import cn.ultronxr.common.util.AjaxResponseUtils;
import cn.ultronxr.framework.util.JWSTokenUtils;
import cn.ultronxr.system.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Ultronxr
 * @date 2022/12/13 14:05
 * @description
 */
@Controller
@RequestMapping("/system/permission")
@Slf4j
public class PermissionController {

    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @RequestMapping("/getPermissionCodeList")
    @ResponseBody
    public AjaxResponse getPermissionCodeList(HttpServletRequest request) {
        String token = JWSTokenUtils.unwrapRequestToken(request);
        List<String> permList = permissionService.getPermissionCodeList(token);
        if(null == permList) {
            return AjaxResponseUtils.fail("permissionCodeList 获取失败！");
        }
        return AjaxResponseUtils.success(permList);
    }

}
