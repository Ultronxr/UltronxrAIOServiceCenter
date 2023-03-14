//package cn.ultronxr.web.controller;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//
///**
// * @author Ultronxr
// * @date 2022/12/11 23:33
// * @description
// */
//@Controller()
//@RequestMapping("/")
//@Slf4j
//public class BaseController {
//
//    /**
//     * 处理 HTTP错误代码 的 controller，跳转到对应的错误页面<br/>
//     * {@link cn.ultronxr.web.config.ErrorPageConfig}
//     */
//    @GetMapping("error/{errorCode}")
//    public String errorPage(@PathVariable Integer errorCode) {
//        log.warn("web 请求错误，错误代码：{}", errorCode);
//        switch (errorCode) {
//            case 400: return "error/400";
//            case 401: return "error/401";
//            case 404: return "error/404";
//            case 500: return "error/500";
//            default: return "error/other";
//        }
//    }
//
//}
