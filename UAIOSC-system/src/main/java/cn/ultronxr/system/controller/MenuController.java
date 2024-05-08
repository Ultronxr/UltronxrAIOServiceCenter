package cn.ultronxr.system.controller;

import cn.ultronxr.common.bean.AjaxResponse;
import cn.ultronxr.common.util.AjaxResponseUtils;
import cn.ultronxr.framework.util.JWSTokenUtils;
import cn.ultronxr.system.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Ultronxr
 * @date 2024/05/05 00:08:14
 * @description
 */
@Controller
@RequestMapping("/system/menu")
@Slf4j
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @RequestMapping("/getMenuList")
    @ResponseBody
    public AjaxResponse getMenuList(HttpServletRequest request) {
        String token = JWSTokenUtils.unwrapRequestToken(request);
        Object menuList = menuService.getMenuList(token);
        return AjaxResponseUtils.success(menuList);
    }

}
