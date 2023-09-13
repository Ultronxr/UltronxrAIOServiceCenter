package cn.ultronxr.web.controller;

import cn.hutool.crypto.digest.DigestUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @author Ultronxr
 * @date 2023/09/13 11:03
 * @description 微信公众平台
 * @link <a href="https://mp.weixin.qq.com/debug/cgi-bin/sandbox?t=sandbox/login">微信公众平台-接口测试账号登录</a>
 * @link <a href="https://developers.weixin.qq.com/doc/offiaccount/Basic_Information/Access_Overview.html">微信公众平台开发文档-接入指南</a>
 */
@Controller
@RequestMapping("/wechat")
@Slf4j
public class WechatController {

    /** 微信公众平台-接口测试账号 */
    private static final String APP_ID = "";
    private static final String APP_SECRET = "";

    /** 自定义 token */
    private static final String TOKEN = "";


    /**
     * 订阅微信公众平台的服务器消息<br/>
     *      0. 所有请求都携带 signature、timestamp、nonce 三个参数，需要进行签名校验，用于鉴别微信官方服务器的身份<br/>
     *      1. 接口配置：微信服务器以 GET 方式请求此接口，并携带 echostr 参数，如果签名校验通过，需要响应 echostr<br/>
     *      2. 其他情况：微信服务器以 POST 方式请求此接口，并在 request body 中携带 XML 格式的报文信息，如果签名校验通过，自行处理报文，无需响应<br/>
     */
    @RequestMapping("/push")
    @ResponseBody
    public String push(HttpServletRequest request, @RequestBody(required = false) String xmlData) {
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");

        log.info("signature={}", signature);
        log.info("timestamp={}", timestamp);
        log.info("nonce={}", nonce);
        log.info("echostr={}", echostr);

        String[] array = new String[] {TOKEN, timestamp, nonce};
        Arrays.sort(array);
        log.info("{}", (Object) array);

        String sha1Str = array[0] + array[1] + array[2];
        sha1Str = DigestUtil.sha1Hex(sha1Str);
        log.info("{}", sha1Str);

        if(!sha1Str.equals(signature)) {
            log.info("签名校验失败！非微信官方服务器！");
            return "FAIL";
        }

        // 接口配置：携带 echostr 参数，需要响应
        if(StringUtils.isNotEmpty(echostr)) {
            log.info("携带 echostr 参数，需要响应");
            return echostr;
        }

        // 其他情况：打印 微信服务器推送的 XML 消息报文，无需响应
        log.info("接收到微信服务器报文：\n{}", xmlData);

        return "OK";
    }

}
