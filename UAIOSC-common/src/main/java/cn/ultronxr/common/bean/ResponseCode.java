package cn.ultronxr.common.bean;

/**
 * @author Ultronxr
 * @date 2022/12/15 18:58
 * @description 用于在 AjaxResponse 中使用的响应代码<br/>
 *              注：这里的响应代码只在业务上使用，与HTTP响应代码不一样。
 */
public enum ResponseCode {
    SUCCESS(200),
    FAIL(500),
    ;


    private int code;

    ResponseCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}
