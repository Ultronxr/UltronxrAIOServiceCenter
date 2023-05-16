package cn.ultronxr.valorant.service;

import cn.hutool.http.HttpRequest;
import cn.ultronxr.valorant.auth.RSO;
import cn.ultronxr.valorant.bean.mybatis.bean.RiotAccount;

/**
 * @author Ultronxr
 * @date 2023/03/04 12:30
 * @description
 */
public interface RSOService {

    /**
     * 进行完整的 拳头用户登录验证服务（Riot Sign On）流程 <br/>
     *     1. [ping]         发起认证请求 <br/>
     *     2. [auth]         进行账号密码校验 <br/>
     *     3. [multifactor]  对于开启了两步验证的账号，进行两步验证 <br/>
     *     4. [entitlements] 使用 access token 获取 entitlements token <br/>
     *
     * @param request          HTTP 请求对象，请确保该对象在一次完整的RSO验证流程中的唯一性，
     * @param username        拳头账号登录用户名
     * @param password        拳头账号密码
     * @param multiFactorCode 两步验证邮箱代码
     * @param needRequestEntitlementsToken 是否需要请求 entitlements token （因为entitlements token是长期有效的，不需要每次都获取）
     * @return
     */
    RSO processRSO(HttpRequest request, String username, String password, String multiFactorCode, boolean needRequestEntitlementsToken);

    /**
     * 账户第一次录入，还未获取 userId 时，初始化生成 RSO 信息
     * @param username 用户名
     * @param password 密码
     * @param multiFactorCode 两步验证码
     * @return
     */
    RSO initRSO(String username, String password, String multiFactorCode);

    /**
     * 后续更新账户的 RSO 信息
     * @param userId 用户ID
     * @return
     */
    RSO updateRSO(String userId);

    /**
     * 为一个账号请求获取一个新的 RSO 对象（token是全新的）
     * @param riotAccount 账号对象
     * @return RSO 对象
     */
    RSO getRSOByAccount(RiotAccount riotAccount);

    /**
     * 使用 RiotAccount 对象其中的 token 字段拼装一个 RSO 对象（token有可能是过期的）
     * @param account 数据库记录
     * @return RSO 对象
     */
    RSO fromAccount(RiotAccount account);

    /**
     * 使用 userId 查找数据库，再用 RiotAccount 对象其中的 token 字段拼装一个 RSO 对象（token有可能是过期的）
     * @param userId 账号ID
     * @return RSO 对象
     */
    RSO fromAccount(String userId);

}
