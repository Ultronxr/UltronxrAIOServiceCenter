package cn.ultronxr.wechat.publicPlatform.bean;

import cn.ultronxr.common.constant.ResBundle;

/**
 * @author Ultronxr
 * @date 2023/09/20 13:50:02
 * @description 微信公众平台 公共数据
 */
public class PublicPlatformData {

    /** 微信公众平台-接口测试账号 */
    public static final String APP_ID = ResBundle.WECHAT.getString("sandbox.app.id");
    public static final String APP_SECRET = ResBundle.WECHAT.getString("sandbox.app.secret");

    /** 消息接口自定义 token */
    public static final String TOKEN = ResBundle.WECHAT.getString("sandbox.messageInterface.token");

}
