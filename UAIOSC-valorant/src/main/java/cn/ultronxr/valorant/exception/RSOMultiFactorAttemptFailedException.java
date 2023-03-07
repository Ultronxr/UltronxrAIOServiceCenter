package cn.ultronxr.valorant.exception;

/**
 * @author Ultronxr
 * @date 2023/03/06 23:47
 * @description
 */
public class RSOMultiFactorAttemptFailedException extends RuntimeException {

    private String msg = "RSO流程-3-[multifactor]：两步验证失败！";


    public RSOMultiFactorAttemptFailedException() {
    }

    public RSOMultiFactorAttemptFailedException(String msg) {
        this.msg = msg;
    }

    @Override
    public String getMessage() {
        return this.msg;
    }

}
