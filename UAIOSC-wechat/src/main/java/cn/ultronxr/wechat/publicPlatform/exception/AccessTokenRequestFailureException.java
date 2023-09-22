package cn.ultronxr.wechat.publicPlatform.exception;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Ultronxr
 * @date 2023/09/14 17:11:23
 * @description 请求获取 access token 失败异常
 */
public class AccessTokenRequestFailureException extends RuntimeException {

    private String message = "请求获取 access token 失败！";

    private String responseBody;


    public AccessTokenRequestFailureException() {
    }

    public AccessTokenRequestFailureException(String message) {
        if(StringUtils.isNotBlank(message)) {
            this.message = message;
        }
    }

    public AccessTokenRequestFailureException(String message, String responseBody) {
        if(StringUtils.isNotBlank(message)) {
            this.message = message;
        }
        this.responseBody = responseBody;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    public String getResponseBody() {
        return this.responseBody;
    }

}
