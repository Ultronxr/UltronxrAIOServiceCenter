package cn.ultronxr.valorant.auth;

import cn.hutool.core.net.URLDecoder;
import cn.hutool.core.util.ReUtil;
import lombok.Data;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author Ultronxr
 * @date 2023/03/06 19:32
 * @description RSO 验证流程 第2/3步 验证成功的响应结果 uri 内容
 */
@Data
public class RSOAuthResUri {

    private String uri;

    private String accessToken;

    private String scope;

    private String iss;

    private String idToken;

    private String tokenType;

    private String sessionState;

    private long expiresIn;

    private static final String REGEX =
            "^http(s)?://.*(#|\\?)access_token=(.*)&scope=(.*)&iss=(.*)&id_token=(.*)&token_type=(\\w+)&session_state=(.*)&expires_in=(\\d+)$";


    /**
     * 对响应结果中的 uri 字符串进行URL解码，再使用正则进行匹配
     * @param uri 响应结果中的 uri 字符串
     * @return RSOAuthResUri 对象
     */
    public static RSOAuthResUri of(String uri) {
        RSOAuthResUri rsoARU = new RSOAuthResUri();
        rsoARU.setUri(URLDecoder.decode(uri, StandardCharsets.UTF_8));
        List<String> list = ReUtil.getAllGroups(Pattern.compile(REGEX), rsoARU.getUri());
        rsoARU.setAccessToken(list.get(3));
        rsoARU.setScope(list.get(4));
        rsoARU.setIss(list.get(5));
        rsoARU.setIdToken(list.get(6));
        rsoARU.setTokenType(list.get(7));
        rsoARU.setSessionState(list.get(8));
        rsoARU.setExpiresIn(Long.parseLong(list.get(9)));
        return rsoARU;
    }

}
