package cn.ultronxr.common.bean;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

/**
 * @author Ultronxr
 * @date 2022/12/15 18:15
 * @description Ajax 请求响应体
 */
public class AjaxResponse extends HashMap<String, Object> {

    // 代码
    private static final String CODE_KEY = "code";

    // 消息
    private static final String MSG_KEY = "msg";

    // 数据
    private static final String DATA_KEY = "data";


    /** 无消息、无数据的成功响应单例 */
    private static final AjaxResponse SUCCESS = new AjaxResponse(ResponseCode.SUCCESS);

    /** 无消息、无数据的失败响应单例 */
    private static final AjaxResponse FAIL = new AjaxResponse(ResponseCode.FAIL);


    public AjaxResponse() {
    }

    public AjaxResponse(@NotNull ResponseCode code) {
        super.put(CODE_KEY, code.getCode());
    }

    public AjaxResponse(@NotNull ResponseCode code, String msg) {
        super.put(CODE_KEY, code.getCode());
        super.put(MSG_KEY, msg);
    }

    public AjaxResponse(@NotNull ResponseCode code, String msg, Object data) {
        super.put(CODE_KEY, code.getCode());
        super.put(MSG_KEY, msg);
        if(data != null) {
            super.put(DATA_KEY, data);
        }
    }


    public static AjaxResponse success() {
        return AjaxResponse.SUCCESS;
    }

    public static AjaxResponse success(String msg) {
        if(StringUtils.isEmpty(msg)) {
            return AjaxResponse.SUCCESS;
        }
        return new AjaxResponse(ResponseCode.SUCCESS, msg);
    }

    public static AjaxResponse success(String msg, Object data) {
        if(StringUtils.isEmpty(msg) && data == null) {
            return AjaxResponse.SUCCESS;
        }
        return new AjaxResponse(ResponseCode.SUCCESS, msg, data);
    }

    public static AjaxResponse fail() {
        return AjaxResponse.FAIL;
    }

    public static AjaxResponse fail(String msg) {
        if(StringUtils.isEmpty(msg)) {
            return AjaxResponse.FAIL;
        }
        return new AjaxResponse(ResponseCode.FAIL, msg);
    }

    public static AjaxResponse fail(String msg, Object data) {
        if(StringUtils.isEmpty(msg) && data == null) {
            return AjaxResponse.FAIL;
        }
        return new AjaxResponse(ResponseCode.FAIL, msg, data);
    }

}
