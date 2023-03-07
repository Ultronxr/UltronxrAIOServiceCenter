package cn.ultronxr.valorant.exception;

/**
 * @author Ultronxr
 * @date 2023/03/07 11:21
 * @description
 */
public class RSOEntitlementsErrorException extends RuntimeException {

    private String msg = "RSO流程-4-[entitlements]：获取 entitlementsToken 失败！";


    public RSOEntitlementsErrorException() {
    }

    public RSOEntitlementsErrorException(String msg) {
        this.msg = msg;
    }

    @Override
    public String getMessage() {
        return this.msg;
    }

}
