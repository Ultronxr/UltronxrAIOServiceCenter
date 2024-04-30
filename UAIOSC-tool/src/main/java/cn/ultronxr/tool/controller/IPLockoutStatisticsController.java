//package cn.ultronxr.tool.IPLockoutStatistics.controller;
//
//import cn.ultronxr.common.bean.AjaxResponse;
//import cn.ultronxr.common.util.AjaxResponseUtils;
//import cn.ultronxr.tool.IPLockoutStatistics.service.IPLockoutStatisticsService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
///**
// * @author Ultronxr
// * @date 2024/01/25 22:05:16
// * @description IP 登录封锁统计 controller
// */
//@Controller
//@RequestMapping("/tool/IPLockoutStatistics")
//@Slf4j
//public class IPLockoutStatisticsController {
//
//    @Autowired
//    private IPLockoutStatisticsService ipLockoutStatisticsService;
//
//    @PostMapping("/process")
//    @ResponseBody
//    public AjaxResponse process() {
//        return AjaxResponseUtils.fail();
//    }
//
//}
