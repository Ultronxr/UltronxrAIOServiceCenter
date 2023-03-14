package cn.ultronxr.common.bean;

/**
 * @author Ultronxr
 * @date 2022/12/15 18:58
 * @description 用于在 AjaxResponse 中使用的响应代码<br/>
 *              注：这里的响应代码只在业务上使用，与HTTP响应代码不一样。
 */
public enum ResponseCode {
    SUCCESS(200, "业务成功"),
    FAIL(500, "业务失败"),
    ;


    private int code;

    private String msg;

    ResponseCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
