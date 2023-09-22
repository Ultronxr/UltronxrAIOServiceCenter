package cn.ultronxr.wechat.publicPlatform.token;

import cn.ultronxr.wechat.publicPlatform.API.StableAccessTokenAPI;
import cn.ultronxr.wechat.publicPlatform.bean.PublicPlatformData;
import cn.ultronxr.wechat.publicPlatform.bean.mybatis.bean.AccessToken;
import cn.ultronxr.wechat.publicPlatform.bean.mybatis.mapper.AccessTokenMapper;
import cn.ultronxr.wechat.publicPlatform.exception.AccessTokenRequestFailureException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @author Ultronxr
 * @date 2023/09/14 12:06:43
 * @description access token 中控管理（请求、刷新、存储、分发）
 */
@Service
@Slf4j
public class AccessTokenManager extends ServiceImpl<AccessTokenMapper, AccessToken> {

    /**
     * 定时刷新 access token ，并缓存<br/>
     * 当前每 30 分钟请求一次（ token 有效期 2 小时，不覆盖旧 token ）
     */
    @Scheduled(cron = "0 0/30 * * * ? ")
    public void refreshAccessToken() {
        AccessToken token = null;
        try {
            token = StableAccessTokenAPI.requestAccessToken();
        } catch (AccessTokenRequestFailureException e) {
            log.warn("刷新微信公众平台 access token 失败。", e);
            log.warn("response body\n{}", e.getResponseBody());
            return;
        }
        if(this.saveOrUpdate(token)) {
            log.info("刷新微信公众平台 access token 成功。");
        }
    }

    /**
     * 从缓存中获取 access token
     */
    public AccessToken getAccessToken() {
        return this.getById(PublicPlatformData.APP_ID);
    }

}
