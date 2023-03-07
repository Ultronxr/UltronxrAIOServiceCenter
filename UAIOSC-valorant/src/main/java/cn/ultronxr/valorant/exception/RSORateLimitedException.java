package cn.ultronxr.valorant.exception;

/**
 * @author Ultronxr
 * @date 2023/03/06 22:28
 * @description
 */
public class RSORateLimitedException extends RuntimeException {

    private String msg = "RSO流程-2-[auth]：认证速率受限！";


    public RSORateLimitedException() {
    }

    public RSORateLimitedException(String msg) {
        this.msg = msg;
    }

    @Override
    public String getMessage() {
        return this.msg;
    }

}
