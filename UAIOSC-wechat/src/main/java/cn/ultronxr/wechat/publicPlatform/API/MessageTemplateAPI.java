package cn.ultronxr.wechat.publicPlatform.API;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.ultronxr.wechat.publicPlatform.bean.mybatis.bean.AccessToken;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Ultronxr
 * @date 2023/09/14 16:47:50
 * @description 发送模板消息 API<br/>
 *              请注意：正常情况下只有企业服务号（需要缴费并通过认证）才能发送模板消息，这里使用测试号替代
 * @link <a href="https://mp.weixin.qq.com/debug/cgi-bin/sandbox?t=sandbox/login">微信公众平台-接口测试账号登录（可以使用服务号的功能）</a><br/>
 *       <a href="https://mp.weixin.qq.com/debug/cgi-bin/readtmpl?t=tmplmsg/faq_tmpl">官方文档 《测试号模板消息接口》</a><br/>
 *       <a href="https://developers.weixin.qq.com/doc/offiaccount/Message_Management/Template_Message_Interface.html">官方文档 《企业服务号模板消息接口》</a>
 */
@Slf4j
public class MessageTemplateAPI {

    private static final String API = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token={ACCESS_TOKEN}";


    public static String sendRequest(String toUserOpenId, String templateId, String url, JSONObject data, AccessToken accessToken) {
        String requestUrl = API.replace("{ACCESS_TOKEN}", accessToken.getAccessToken());
        JSONObject requestBody = new JSONObject();
        requestBody.set("touser", toUserOpenId);
        requestBody.set("template_id", templateId);
        requestBody.set("url", url);
        requestBody.set("data", data);

        HttpRequest request = HttpRequest
                .post(requestUrl)
                .body(requestBody.toString());
        HttpResponse response = request.execute();
        log.info("向用户 {} 发送模板消息结果： {}", toUserOpenId, response.body());
        return response.body();
    }

}
