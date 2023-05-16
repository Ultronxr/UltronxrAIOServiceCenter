package cn.ultronxr.common.constant;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Ultronxr
 * @date 2023/04/15 19:04:38
 * @description 配置文件资源包
 */
public class ResBundle {

    //public static final ResourceBundle ALI_CLOUD = ResourceBundle.getBundle("conf.aliCloudConfig");

    public static final ResourceBundle TENCENT_CLOUD = ResourceBundle.getBundle("config.tencentCloudConfig", Locale.SIMPLIFIED_CHINESE);

    public static final ResourceBundle SMS = ResourceBundle.getBundle("config.sms", Locale.SIMPLIFIED_CHINESE);

}
