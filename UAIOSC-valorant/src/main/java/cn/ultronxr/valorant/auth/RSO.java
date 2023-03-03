package cn.ultronxr.valorant.auth;

import cn.ultronxr.valorant.bean.RiotClientVersion;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ultronxr
 * @date 2023/02/21 16:27
 * @description 拳头用户登录验证服务（Riot Sign On）的内容
 */
@Component
@Data
public class RSO {

    /** 用户验证 token */
    private String accessToken;

    /** 用户验证 token */
    private String entitlementsToken;

    /** 用户 ID （登录验证成功后可以获取） */
    private String userId;

    /** 拳头客户端平台 */
    private String riotClientPlatform = "ew0KCSJwbGF0Zm9ybVR5cGUiOiAiUEMiLA0KCSJwbGF0Zm9ybU9TIjogIldpbmRvd3MiLA0KCSJwbGF0Zm9ybU9TVmVyc2lvbiI6ICIxMC4wLjE5MDQyLjEuMjU2LjY0Yml0IiwNCgkicGxhdGZvcm1DaGlwc2V0IjogIlVua25vd24iDQp9";

    /** 拳头客户端版本 */
    private String riotClientVersion = "release-06.03-shipping-6-830687";

    /** 拳头客户端构建版本 */
    private String riotClientBuild = "63.0.9.4909983.4789131";

    private static final String RIOT_CLIENT_AGENT_PREFIX = "RiotClient/";
    private static final String RIOT_CLIENT_AGENT_SUFFIX = " rso-auth (Windows;10;;Professional, x64)";
    /** 拳头客户端代理环境 */
    private String riotClientAgent = RIOT_CLIENT_AGENT_PREFIX + riotClientBuild + RIOT_CLIENT_AGENT_SUFFIX;


    private void updateRiotClientAgent() {
        setRiotClientAgent(RIOT_CLIENT_AGENT_PREFIX + riotClientBuild + RIOT_CLIENT_AGENT_SUFFIX);
    }

    /**
     *
     */
    public void updateVersion(RiotClientVersion rcv) {
        setRiotClientVersion(rcv.getRiotClientVersion());
        setRiotClientBuild(rcv.getRiotClientBuild());
        updateRiotClientAgent();
    }

    public void updateToken() {
    }

    public void updateToken(String accessToken, String entitlementsToken, String userId) {
        this.accessToken = accessToken;
        this.entitlementsToken = entitlementsToken;
        this.userId = userId;
    }

    /**
     * 获取请求API时用于用户验证的请求头
     */
    public Map<String, String> getRequestHeaderMap() {
        updateToken(
                "eyJraWQiOiJzMSIsImFsZyI6IlJTMjU2In0.eyJwcCI6eyJjIjoiYXMifSwic3ViIjoiMTEzYmJlZjEtYTU5ZS01MTQyLThlOGYtMjFiODlkZWYwMzhlIiwic2NwIjpbIm9wZW5pZCIsImxpbmsiLCJiYW4iLCJsb2xfcmVnaW9uIiwibG9sIiwic3VtbW9uZXIiLCJvZmZsaW5lX2FjY2VzcyJdLCJjbG0iOlsibG9sX2FjY291bnQiLCJlbWFpbF92ZXJpZmllZCIsIm9wZW5pZCIsInB3IiwibG9sIiwib3JpZ2luYWxfcGxhdGZvcm1faWQiLCJwaG9uZV9udW1iZXJfdmVyaWZpZWQiLCJwaG90byIsIm9yaWdpbmFsX2FjY291bnRfaWQiLCJwcmVmZXJyZWRfdXNlcm5hbWUiLCJsb2NhbGUiLCJiYW4iLCJsb2xfcmVnaW9uIiwiYWNjdF9nbnQiLCJyZWdpb24iLCJwdnBuZXRfYWNjb3VudF9pZCIsImFjY3QiLCJ1c2VybmFtZSJdLCJkYXQiOnsicCI6bnVsbCwibG5rIjpbXSwiYyI6InV3MSIsImxpZCI6IkpLZkxtQjNRSU1LMGptbUxKOEdrb2cifSwiaXNzIjoiaHR0cHM6Ly9hdXRoLnJpb3RnYW1lcy5jb20iLCJleHAiOjE2Nzc4NDE4ODMsImlhdCI6MTY3NzgzODI4MywianRpIjoiRjBiSENETExnTG8iLCJjaWQiOiJyaW90LWNsaWVudCJ9.PexuUJI1pVmOfytmxAEqMFx58bGQ_8pBtrDy5CN5no72OHQWFcnw1VJR60s5fvAeEJDI_zMN5NzSrzo0Zh8EiiwAKo0yknszXsRMMHdKGsSkYTJbrayAOpFaICUUcRZTEafb4BMPrRab900SaGfFgq1ez3itQkxkZNQFUD2qupM"
                , "eyJraWQiOiJrMSIsImFsZyI6IlJTMjU2In0.eyJlbnRpdGxlbWVudHMiOltdLCJhdF9oYXNoIjoiMnBWdlB2N0pGbHlMN1NWUkc1RC1zQSIsInN1YiI6IjExM2JiZWYxLWE1OWUtNTE0Mi04ZThmLTIxYjg5ZGVmMDM4ZSIsImlzcyI6Imh0dHBzOlwvXC9lbnRpdGxlbWVudHMuYXV0aC5yaW90Z2FtZXMuY29tIiwiaWF0IjoxNjc3ODM4Mjg0LCJqdGkiOiJGMGJIQ0RMTGdMbyJ9.lVk6oNqHAGU3Ivmkgpj5McuiCebIB2HJ1X2YN1ZsdeubWPMaDthicf7LpqcRGDMs6dAibeGJtsHJs6H7epyW7CfK0ohyEWtcDakuwab5xl0vYpc1WKL7aXeAkHaseTNNT6Ryn-5LLjY7eUicsVZsYTx-kqN5geXI8ukngXPX3dyRtyeaTOREwpIYKyCYf3wn6J-2p0991UqrfKirswGQg1_rSxO-gi8aMAbPdQXY6krvAUZrrHzIfRrrrKBFEFQDvp_vmlg2spzPY4pIYqdNLMj_fouNqzIvhJ5Ht8G33yRG20iFm2hzd7gXG9IuLTI4irn6qOKoJXRpwFJNKc-tTQ"
                , "113bbef1-a59e-5142-8e8f-21b89def038e"
        );
        //updateToken();

        return new HashMap<>() {{
            put("Content-Type", "application/json");
            put("Authorization", "Bearer " + accessToken);
            put("X-Riot-Entitlements-JWT", entitlementsToken);
            put("X-Riot-ClientPlatform", riotClientPlatform);
            put("X-Riot-ClientVersion", riotClientVersion);
        }};
    }

}
