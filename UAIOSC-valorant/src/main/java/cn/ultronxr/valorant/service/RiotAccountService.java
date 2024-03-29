package cn.ultronxr.valorant.service;

import cn.ultronxr.valorant.bean.mybatis.bean.RiotAccount;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Ultronxr
 * @date 2023/02/22 15:10
 * @description
 */
public interface RiotAccountService extends IService<RiotAccount> {

    boolean create(RiotAccount account);

    /**
     * 为已经在数据库中存在的、且开启了两步验证的账号，更新两步验证账号的验证码（相当于重新登录）
     * @return
     */
    boolean updateMultiFactor(RiotAccount account);

    //boolean update(RiotAccount account);

    /**
     * 查询拳头账户列表
     * @param account 查询条件
     * @return 返回的账户列表中不包含敏感信息（密码、token等）
     */
    List<RiotAccount> queryAccount(RiotAccount account);

}
