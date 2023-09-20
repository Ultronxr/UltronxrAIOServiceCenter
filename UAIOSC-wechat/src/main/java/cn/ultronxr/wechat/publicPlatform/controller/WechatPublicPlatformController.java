package cn.ultronxr.wechat.publicPlatform.controller;

import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.json.JSONObject;
import cn.ultronxr.wechat.publicPlatform.API.MessageTemplateAPI;
import cn.ultronxr.wechat.publicPlatform.bean.PublicPlatformData;
import cn.ultronxr.wechat.publicPlatform.bean.mybatis.bean.AccessToken;
import cn.ultronxr.wechat.publicPlatform.token.AccessTokenManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @author Ultronxr
 * @date 2023/09/13 11:03
 * @description 微信公众平台 controller
 * @link <a href="https://mp.weixin.qq.com/debug/cgi-bin/sandbox?t=sandbox/login">微信公众平台-接口测试账号登录</a>
 * @link <a href="https://developers.weixin.qq.com/doc/offiaccount/Basic_Information/Access_Overview.html">微信公众平台开发文档-接入指南</a>
 */
@Controller
@RequestMapping("/wechat")
@Slf4j
public class WechatPublicPlatformController {

    @Autowired
    private AccessTokenManager accessTokenManager;


    /**
     * 微信公众平台消息接口（接收微信公众平台的服务器推送的消息）<br/>
     *      0. 所有请求都携带 signature、timestamp、nonce 三个参数，需要进行签名校验，用于鉴别微信官方服务器的身份<br/>
     *      1. 接口配置：微信服务器以 GET 方式请求此接口，并携带 echostr 参数，如果签名校验通过，需要响应 echostr<br/>
     *      2. 其他情况：微信服务器以 POST 方式请求此接口，并在 request body 中携带 XML 格式的报文信息，如果签名校验通过，自行处理报文，无需响应<br/>
     */
    @RequestMapping("/messageInterface")
    @ResponseBody
    public String messageInterface(HttpServletRequest request, @RequestBody(required = false) String xmlData) {
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");

        log.info("signature={}", signature);
        log.info("timestamp={}", timestamp);
        log.info("nonce={}", nonce);
        log.info("echostr={}", echostr);

        String[] array = new String[] {PublicPlatformData.TOKEN, timestamp, nonce};
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

    /**
     * 手动刷新 access token
     */
    @GetMapping("/refreshAccessToken")
    @ResponseBody
    public AccessToken refreshAccessToken() {
        accessTokenManager.refreshAccessToken();
        return accessTokenManager.getAccessToken();
    }

    /**
     * 发送模板消息
     */
    @RequestMapping("/messageTemplate")
    @ResponseBody
    public String[] messageTemplate() {
        String[] toUserOpenIds = new String[] {""};
        String templateId = "";
        String url = "https://www.baidu.com/";
        JSONObject data = new JSONObject()
                .set("keyword1", new JSONObject().set("value", "This is test message.").set("color", "#173177"));
        AccessToken accessToken = accessTokenManager.getAccessToken();

        String[] responseArray = new String[toUserOpenIds.length];
        for(int i = 0; i < toUserOpenIds.length; i++) {
            responseArray[i] = MessageTemplateAPI.sendRequest(toUserOpenIds[i], templateId, url, data, accessToken);
        }
        return responseArray;
    }

}
