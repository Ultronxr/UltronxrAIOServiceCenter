package cn.ultronxr.valorant.exception;

/**
 * @author Ultronxr
 * @date 2023/03/06 23:37
 * @description
 */
public class RSOUnknownAuthErrorException extends RuntimeException {

    private String msg = "RSO流程-2-[auth]：未知账号登录认证错误！";


    public RSOUnknownAuthErrorException() {
    }

    public RSOUnknownAuthErrorException(String msg) {
        this.msg = msg;
    }

    @Override
    public String getMessage() {
        return this.msg;
    }

}
