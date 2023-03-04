package cn.ultronxr.valorant.api;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.ultronxr.valorant.exception.APIUnauthorizedException;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @author Ultronxr
 * @date 2023/02/21 18:10
 * @description 基础 API 框架，包含常用方法
 */
@Slf4j
public abstract class BaseAPI {

    /**
     * 进行请求API、处理并解析返回数据的完整流程
     */
    public void process() {

    }

    /**
     * 请求 API 获取返回数据
     */
    public String requestAPI(String API, Map<String, String> headerMap) {
        log.info("请求API：{}", API);
        String responseBody;
        HttpResponse response =
                HttpUtil.createGet(API)
                        .headerMap(headerMap, true)
                        .execute();
        // responseBody = response.isOk() ? response.body() : null;
        responseBody = response.body();
        response.close();
        return responseBody;
    }

    /**
     * 解析 API 返回的数据
     */
    public Object parseData(String responseBody) throws APIUnauthorizedException {
        return null;
    }

}
