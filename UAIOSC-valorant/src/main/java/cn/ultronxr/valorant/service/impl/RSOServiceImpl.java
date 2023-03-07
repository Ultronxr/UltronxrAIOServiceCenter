package cn.ultronxr.valorant.service.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.Method;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.ultronxr.valorant.auth.RSO;
import cn.ultronxr.valorant.bean.mybatis.bean.RiotAccount;
import cn.ultronxr.valorant.bean.mybatis.mapper.RiotAccountMapper;
import cn.ultronxr.valorant.exception.*;
import cn.ultronxr.valorant.service.RSOService;
import cn.ultronxr.valorant.util.RSOUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Ultronxr
 * @date 2023/03/04 12:30
 * @description
 */
@Service
@Slf4j
public class RSOServiceImpl implements RSOService {

    @Autowired
    private RiotAccountMapper accountMapper;


    @Override
    public RSO processRSO(HttpRequest request, String username, String password, String multiFactorCode) {
        if(null == request) {
            return null;
        }

        request.setUrl(RSOUtils.AUTH_URL)
                .method(Method.POST)
                .headerMap(RSOUtils.getHeader(), true)
                .body(RSOUtils.getPingBodyJsonStr());
        HttpResponse response = request.execute();
        log.info("RSO流程第一步：ping response = {}", response.body());

        JSONObject resObj = JSONUtil.parseObj(response.body());
        String resType = resObj.getStr("type", "null");
        if(!"response".equals(resType)) {
            request.method(Method.PUT)
                    .headerMap(RSOUtils.getHeader(), true)
                    .body(RSOUtils.getAuthBodyJsonStr(username, password, true));
            response = request.execute();
            log.info("RSO流程第二步：auth response = {}", response.body());

            resObj = JSONUtil.parseObj(response.body());
            resType = resObj.getStr("type", "null");
            if("multifactor".equals(resType)) {
                // 两步验证
                request.method(Method.PUT)
                        .headerMap(RSOUtils.getHeader(), true)
                        .body(RSOUtils.getMultiFactorBodyJsonStr(multiFactorCode, true));
                response = request.execute();
                log.info("RSO流程第三步：multifactor response = {}", response.body());

                JSONObject multiFactorResObj  = JSONUtil.parseObj(response.body());
                if (multiFactorResObj.containsKey("error") && multiFactorResObj.getStr("error").equals("multifactor_attempt_failed")) {
                    throw new RSOMultiFactorAttemptFailedException();
                } else {
                    // 两步验证成功
                    resObj = multiFactorResObj;
                }
            } else if ("response".equals(resType)) {
                // 解析结果
            } else if ("auth".equals(resType)) {
                // 认证错误
                String error = resObj.getStr("error");
                if(StringUtils.isNotEmpty(error)) {
                    switch (error) {
                        case "auth_failure":
                            throw new RSOAuthFailureException();
                        case "rate_limited":
                            throw new RSORateLimitedException();
                        default:
                            throw new RSOUnknownAuthErrorException();
                    }
                }
            } else {
                // 其他未知情况
                throw new RSOUnknownResponseTypeException();
            }
        }

        // 解析结果
        RSO rso = new RSO();
        rso = RSOUtils.parseAccessToken(resObj, rso);

        request.setUrl(RSOUtils.ENTITLEMENTS_URL)
                .method(Method.POST)
                .headerMap(RSOUtils.getHeader(), true)
                .header("Authorization", "Bearer " + rso.getAccessToken())
                .body("{}");
        response = request.execute();
        log.info("RSO流程第四步：entitlements response = {}", response.body());

        resObj = JSONUtil.parseObj(response.body());
        if(StringUtils.isNotEmpty(resObj.getStr("errorCode"))) {
            throw new RSOEntitlementsErrorException();
        }
        rso = RSOUtils.parseEntitlementsToken(resObj, rso);

        return rso;
    }

    @Override
    public RSO initRSO(String username, String password, String multiFactorCode) {
        RSO rso = null;
        try {
            HttpRequest request = HttpUtil.createPost(RSOUtils.AUTH_URL);
            rso = this.processRSO(request, username, password, multiFactorCode);
        } catch (Exception e) {
            log.warn("RSO验证失败：username = {}, exception = {}", username, e.getMessage());
            return null;
        }
        return rso;
    }

    @Override
    public RSO updateRSO(String userId) {
        RSO rso = null;
        RiotAccount account = accountMapper.selectById(userId);
        try {
            HttpRequest request = HttpUtil.createPost(RSOUtils.AUTH_URL);
            rso = this.processRSO(request, account.getUsername(), account.getPassword(), account.getMultiFactor());
        } catch (Exception e) {
            log.warn("RSO验证失败：username = {}, exception = {}", account.getUsername(), e.getMessage());
            return null;
        }
        account.setAccessToken(rso.getAccessToken());
        account.setEntitlementsToken(rso.getEntitlementsToken());
        accountMapper.updateById(account);
        return rso;
    }

    @Override
    public RSO getRSOByAccount(RiotAccount account) {
        RSO rso;
        if(StringUtils.isNotEmpty(account.getUserId())) {
            rso = updateRSO(account.getUserId());
        } else {
            rso = initRSO(account.getUsername(), account.getPassword(), account.getMultiFactor());
        }
        return rso;
    }

    @Override
    public RSO fromAccount(RiotAccount account) {
        RSO rso = new RSO();
        rso.updateToken(account.getAccessToken(), account.getEntitlementsToken(), account.getUserId());
        return rso;
    }

    @Override
    public RSO fromAccount(String userId) {
        RiotAccount account = accountMapper.selectById(userId);
        return fromAccount(account);
    }

}
