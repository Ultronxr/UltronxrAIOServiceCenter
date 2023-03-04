package cn.ultronxr.valorant.service;

import cn.ultronxr.valorant.auth.RSO;
import cn.ultronxr.valorant.bean.mybatis.bean.RiotAccount;

/**
 * @author Ultronxr
 * @date 2023/03/04 12:30
 * @description
 */
public interface RSOService {

    /**
     * 账户第一次录入，还未获取 userId 时，初始化生成 RSO 信息
     * @param username 用户名
     * @param password 密码
     * @return
     */
    RSO initRSO(String username, String password);

    /**
     * 后续更新账户的 RSO 信息
     * @param userId 用户ID
     * @return
     */
    RSO updateRSO(String userId);

    RSO getRSOByAccount(RiotAccount riotAccount);

    RSO fromAccount(RiotAccount account);

    RSO fromAccount(String userId);

}
