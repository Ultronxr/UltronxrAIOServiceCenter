package cn.ultronxr.wechat.publicPlatform.token;

import cn.ultronxr.wechat.publicPlatform.API.StableAccessTokenAPI;
import cn.ultronxr.wechat.publicPlatform.bean.PublicPlatformData;
import cn.ultronxr.wechat.publicPlatform.bean.mybatis.bean.AccessToken;
import cn.ultronxr.wechat.publicPlatform.bean.mybatis.mapper.AccessTokenMapper;
import cn.ultronxr.wechat.publicPlatform.exception.AccessTokenRequireFailureException;
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
     * 每一个半小时执行一次<br/>
     * crontab 不能直接表达每一个半小时，需要分成两块：<br/>
     *      ① 整点：00:00    03:00    06:00    09:00    12:00    15:00    18:00    21:00 （从0时0分开始，每三小时执行一次）<br/>
     *      ② 半点：    01:30    04:30    07:30    10:30    13:30    16:30    19:30    22:30 （从1时30分开始，每三小时执行一次）
     */
    @Scheduled(cron = "0 0 0/3 * * ? ")
    @Scheduled(cron = "0 30 1/3 * * ? ")
    public void refreshAccessToken() {
        log.info("开始刷新微信公众平台 access token");
        AccessToken token = null;
        try {
            token = StableAccessTokenAPI.requestAccessToken();
        } catch (AccessTokenRequireFailureException e) {
            log.error("刷新微信公众平台 access token 失败。", e);
            return;
        }
        if(this.saveOrUpdate(token)) {
            log.info("更新微信公众平台 access token 成功。");
        }
    }

    public AccessToken getAccessToken() {
        return this.getById(PublicPlatformData.APP_ID);
    }

}
