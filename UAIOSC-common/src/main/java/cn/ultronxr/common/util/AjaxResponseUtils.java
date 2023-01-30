package cn.ultronxr.common.util;

import cn.ultronxr.common.bean.AjaxResponse;
import cn.ultronxr.common.bean.ResponseCode;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Ultronxr
 * @date 2023/01/19 16:48
 * @description {@link AjaxResponse} 对象的工具类，获取常用的响应结果
 */
public class AjaxResponseUtils {

    /** 无数据的“未登录/未授权”响应单例 */
    private static final AjaxResponse UNAUTHORIZED = new AjaxResponse(ResponseCode.UNAUTHORIZED);

    /** 无数据的“业务成功”响应单例 */
    private static final AjaxResponse SUCCESS = new AjaxResponse(ResponseCode.SUCCESS);

    /** 无数据的“业务失败”响应单例 */
    private static final AjaxResponse FAIL = new AjaxResponse(ResponseCode.FAIL);


    public static AjaxResponse unauthorized() {
        return UNAUTHORIZED;
    }

    public static AjaxResponse unauthorized(String msg) {
        if(StringUtils.isEmpty(msg)) {
            return UNAUTHORIZED;
        }
        return new AjaxResponse(ResponseCode.UNAUTHORIZED, msg);
    }

    public static AjaxResponse refreshAuthToken(Object data) {
        return new AjaxResponse(ResponseCode.REFRESH_AUTH_TOKEN, data);
    }

    public static AjaxResponse success() {
        return SUCCESS;
    }

    public static AjaxResponse success(String msg) {
        if(StringUtils.isEmpty(msg)) {
            return SUCCESS;
        }
        return new AjaxResponse(ResponseCode.SUCCESS, msg);
    }

    public static AjaxResponse success(Object data) {
        if(null == data) {
            return SUCCESS;
        }
        return new AjaxResponse(ResponseCode.SUCCESS, data);
    }

    public static AjaxResponse success(String msg, Object data) {
        if(StringUtils.isEmpty(msg) && null == data) {
            return SUCCESS;
        }
        return new AjaxResponse(ResponseCode.SUCCESS, msg, data);
    }

    public static AjaxResponse fail() {
        return FAIL;
    }

    public static AjaxResponse fail(String msg) {
        if(StringUtils.isEmpty(msg)) {
            return FAIL;
        }
        return new AjaxResponse(ResponseCode.FAIL, msg);
    }

    public static AjaxResponse fail(Object data) {
        if(null == data) {
            return FAIL;
        }
        return new AjaxResponse(ResponseCode.FAIL, data);
    }

    public static AjaxResponse fail(String msg, Object data) {
        if(StringUtils.isEmpty(msg) && null == data) {
            return FAIL;
        }
        return new AjaxResponse(ResponseCode.FAIL, msg, data);
    }

}
