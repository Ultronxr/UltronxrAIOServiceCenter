package cn.ultronxr.valorant.exception;

/**
 * @author Ultronxr
 * @date 2023/03/06 23:38
 * @description
 */
public class RSOUnknownResponseTypeException extends RuntimeException {

    private String msg = "RSO流程-1-[ping]：未知响应结果类别！";

    public RSOUnknownResponseTypeException() {
    }

    public RSOUnknownResponseTypeException(String msg) {
        this.msg = msg;
    }

    @Override
    public String getMessage() {
        return this.msg;
    }

}
