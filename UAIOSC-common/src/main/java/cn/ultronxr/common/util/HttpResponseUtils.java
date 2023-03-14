package cn.ultronxr.common.util;

import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author Ultronxr
 * @date 2023/03/13 10:57
 * @description 有关 HTTP 响应的 工具类库
 */
public class HttpResponseUtils {

    // "application/json;charset=UTF-8"
    private static final String JSON_TYPE = MediaType.APPLICATION_JSON_VALUE + ";charset=" + StandardCharsets.UTF_8.name();


    public static void sendJson(HttpServletResponse response, Object objectToSend) throws IOException {
        response.setContentType(JSON_TYPE);
        response.getWriter().write(objectToSend.toString());
        response.getWriter().flush();
    }

    public static void sendJson(HttpServletResponse response, String strToSend) throws IOException {
        response.setContentType(JSON_TYPE);
        response.getWriter().write(strToSend);
        response.getWriter().flush();
    }

}
