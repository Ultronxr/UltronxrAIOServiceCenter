package cn.ultronxr.common.bean;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

/**
 * @author Ultronxr
 * @date 2022/12/15 18:15
 * @description
 */
public class AjaxResponse extends HashMap<String, Object> {

    private static final String CODE_KEY = "code";

    private static final String MSG_KEY = "msg";

    private static final String DATA_KEY = "data";


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
        return new AjaxResponse(ResponseCode.SUCCESS);
    }

    public static AjaxResponse success(String msg) {
        return new AjaxResponse(ResponseCode.SUCCESS, msg);
    }

    public static AjaxResponse success(String msg, Object data) {
        return new AjaxResponse(ResponseCode.SUCCESS, msg, data);
    }

    public static AjaxResponse fail() {
        return new AjaxResponse(ResponseCode.FAIL);
    }

    public static AjaxResponse fail(String msg) {
        return new AjaxResponse(ResponseCode.FAIL, msg);
    }

    public static AjaxResponse fail(String msg, Object data) {
        return new AjaxResponse(ResponseCode.FAIL, msg, data);
    }

}
