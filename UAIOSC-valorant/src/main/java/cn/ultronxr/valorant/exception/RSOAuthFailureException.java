package cn.ultronxr.valorant.exception;

/**
 * @author Ultronxr
 * @date 2023/03/06 22:25
 * @description
 */
public class RSOAuthFailureException extends RuntimeException {

    private String msg = "RSO流程-2-[auth]：账号验证失败！";


    public RSOAuthFailureException() {
    }

    public RSOAuthFailureException(String msg) {
        this.msg = msg;
    }

    @Override
    public String getMessage() {
        return this.msg;
    }

}
