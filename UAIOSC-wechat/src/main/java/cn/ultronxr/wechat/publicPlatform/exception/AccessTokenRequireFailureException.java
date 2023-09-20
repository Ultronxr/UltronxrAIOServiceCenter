package cn.ultronxr.wechat.publicPlatform.exception;

/**
 * @author Ultronxr
 * @date 2023/09/14 17:11:23
 * @description 获取 access token 失败异常
 */
public class AccessTokenRequireFailureException extends RuntimeException {

    private String message = "获取 access token 失败！";


    public AccessTokenRequireFailureException() {
    }

    public AccessTokenRequireFailureException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

}
