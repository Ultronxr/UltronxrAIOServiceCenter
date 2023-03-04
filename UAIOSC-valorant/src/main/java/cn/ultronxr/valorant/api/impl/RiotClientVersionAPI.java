package cn.ultronxr.valorant.api.impl;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.ultronxr.valorant.api.BaseAPI;
import cn.ultronxr.valorant.api.ValorantDotComAPIEnum;
import cn.ultronxr.valorant.auth.RSO;
import cn.ultronxr.valorant.bean.RiotClientVersion;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Ultronxr
 * @date 2023/02/21 16:46
 * @description <b>拳头客户端版本</b>API
 */
@Component
@Slf4j
public class RiotClientVersionAPI extends BaseAPI {

    private static final String API = ValorantDotComAPIEnum.RiotClientVersion.getUrl();


    public void process(RSO rso) {
        String responseBody = requestAPI(API, null);
        RiotClientVersion rcv = parseData(responseBody);
        rso.updateVersion(rcv);
    }

    @Override
    public RiotClientVersion parseData(String responseBody) {
        JSONObject rootObj = JSONUtil.parseObj(responseBody, false);
        if(!rootObj.get("status", String.class).equals("200")) {
            return null;
        }
        JSONObject data = rootObj.getJSONObject("data");
        return JSONUtil.toBean(data, RiotClientVersion.class);
    }

}
