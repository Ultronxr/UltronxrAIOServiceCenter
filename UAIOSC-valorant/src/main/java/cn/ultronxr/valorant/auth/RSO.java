package cn.ultronxr.valorant.auth;

import cn.ultronxr.valorant.bean.RiotClientVersion;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ultronxr
 * @date 2023/02/21 16:27
 * @description 拳头用户登录验证服务（Riot Sign On）的内容
 */
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

    public void updateVersion(RiotClientVersion rcv) {
        setRiotClientVersion(rcv.getRiotClientVersion());
        setRiotClientBuild(rcv.getRiotClientBuild());
        updateRiotClientAgent();
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
        return new HashMap<>() {{
            put("Content-Type", "application/json");
            put("Authorization", "Bearer " + accessToken);
            put("X-Riot-Entitlements-JWT", entitlementsToken);
            put("X-Riot-ClientPlatform", riotClientPlatform);
            put("X-Riot-ClientVersion", riotClientVersion);
        }};
    }

    @Override
    public String toString() {
        return "RSO{" +
                "accessToken=" + accessToken + '\n' +
                ", entitlementsToken=" + entitlementsToken + '\n' +
                ", userId=" + userId + '\n' +
                ", riotClientPlatform=" + riotClientPlatform + '\n' +
                ", riotClientVersion=" + riotClientVersion + '\n' +
                ", riotClientBuild=" + riotClientBuild + '\n' +
                ", riotClientAgent=" + riotClientAgent + '\n' +
                '}';
    }
}
