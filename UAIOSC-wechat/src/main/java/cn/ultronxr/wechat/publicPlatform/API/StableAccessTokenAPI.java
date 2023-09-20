package cn.ultronxr.wechat.publicPlatform.API;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.*;
import cn.ultronxr.common.constant.ResBundle;
import cn.ultronxr.wechat.publicPlatform.bean.PublicPlatformData;
import cn.ultronxr.wechat.publicPlatform.bean.mybatis.bean.AccessToken;
import cn.ultronxr.wechat.publicPlatform.exception.AccessTokenRequireFailureException;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Ultronxr
 * @date 2023/09/14 16:47:04
 * @description 获取稳定版 access token 的 API
 * @link <a href="https://developers.weixin.qq.com/doc/offiaccount/Basic_Information/getStableAccessToken.html">官方文档 《获取稳定版接口调用凭据》</a>
 */
@Slf4j
public class StableAccessTokenAPI {

    private static final String API = "https://api.weixin.qq.com/cgi-bin/stable_token";

    private static final JSONObject REQUEST_BODY = new JSONObject() {{
        set("grant_type", "client_credential");
        set("appid", PublicPlatformData.APP_ID);
        set("secret", PublicPlatformData.APP_SECRET);
        set("force_refresh", false);
    }};

    public static AccessToken requestAccessToken() throws AccessTokenRequireFailureException {
        HttpRequest request = HttpRequest
                .post(API)
                .body(REQUEST_BODY.toString());
        HttpResponse response = request.execute();
        if(response.isOk()) {
            JSONObject resObj = JSONUtil.parseObj(response.body());
            log.info("获取 access token 成功：{}", resObj);
            AccessToken token = JSONUtil.toBean(resObj, AccessToken.class);
            token.setAppId(ResBundle.WECHAT.getString("sandbox.app.id"));
            return token;
        }
        log.warn("获取 access token 失败！{}", response.body());
        throw new AccessTokenRequireFailureException();
    }

}
