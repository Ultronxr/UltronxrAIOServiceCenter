package cn.ultronxr.common.bean;

import cn.hutool.json.JSONUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;

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


    public AjaxResponse() {
    }

    public AjaxResponse(@NotNull ResponseCode code) {
        super.put(CODE_KEY, code.getCode());
        // 未提供自定义信息时，默认自动填充 ResponseCode 中的信息
        super.put(MSG_KEY, code.getMsg());
    }

    public AjaxResponse(@NotNull ResponseCode code, String msg) {
        super.put(CODE_KEY, code.getCode());
        super.put(MSG_KEY, msg);
    }

    public AjaxResponse(@NotNull ResponseCode code, Object data) {
        super.put(CODE_KEY, code.getCode());
        // 未提供自定义信息时，默认自动填充 ResponseCode 中的信息
        super.put(MSG_KEY, code.getMsg());
        if(null != data) {
            super.put(DATA_KEY, data);
        }
    }

    public AjaxResponse(@NotNull ResponseCode code, String msg, Object data) {
        super.put(CODE_KEY, code.getCode());
        super.put(MSG_KEY, msg);
        if(null != data) {
            super.put(DATA_KEY, data);
        }
    }

    public AjaxResponse(HttpStatus httpStatus) {
        super.put(CODE_KEY, httpStatus.value());
        super.put(MSG_KEY, httpStatus.getReasonPhrase());
    }

    public AjaxResponse(HttpStatus httpStatus, String msg) {
        super.put(CODE_KEY, httpStatus.value());
        super.put(MSG_KEY, msg);
    }

    @Override
    public String toString() {
        return JSONUtil.toJsonStr(this);
    }

}
