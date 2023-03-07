package cn.ultronxr.valorant.exception;

/**
 * @author Ultronxr
 * @date 2023/03/04 20:12
 * @description
 */
public class APIUnauthorizedException extends Exception {

    private String msg = "API未授权";


    public APIUnauthorizedException() {
    }

    public APIUnauthorizedException(String msg) {
        this.msg = msg;
    }

    @Override
    public String getMessage() {
        return this.msg;
    }

}
