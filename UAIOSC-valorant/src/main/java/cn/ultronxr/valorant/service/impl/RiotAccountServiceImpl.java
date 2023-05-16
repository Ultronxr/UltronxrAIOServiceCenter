package cn.ultronxr.valorant.service.impl;

import cn.hutool.http.HttpRequest;
import cn.ultronxr.valorant.auth.RSO;
import cn.ultronxr.valorant.bean.mybatis.bean.RiotAccount;
import cn.ultronxr.valorant.bean.mybatis.mapper.RiotAccountMapper;
import cn.ultronxr.valorant.service.RSOService;
import cn.ultronxr.valorant.service.RiotAccountService;
import cn.ultronxr.valorant.util.RSOUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Ultronxr
 * @date 2023/02/22 15:11
 * @description
 */
@Service
@Slf4j
public class RiotAccountServiceImpl extends ServiceImpl<RiotAccountMapper, RiotAccount> implements RiotAccountService {

    @Autowired
    private RiotAccountMapper accountMapper;

    @Autowired
    private RSOService rsoService;


    @Override
    public boolean create(RiotAccount account) {
        RSO rso = rsoService.getRSOByAccount(account);
        if(null != rso) {
            account.setUserId(rso.getUserId());
            account.setAccessToken(rso.getAccessToken());
            account.setEntitlementsToken(rso.getEntitlementsToken());
            this.save(account);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateMultiFactor(RiotAccount account) {
        RSO rso = null;
        String multiFactorCode = account.getMultiFactor();
        try {
            HttpRequest request = HttpRequest.post(RSOUtils.AUTH_URL);
            if(StringUtils.isNotEmpty(account.getUserId()) && StringUtils.isEmpty(account.getPassword())) {
                account = this.getById(account.getUserId());
            }
            rso = rsoService.processRSO(request, account.getUsername(), account.getPassword(), multiFactorCode, false);
        } catch (Exception e) {
            return false;
        }
        account.setAccessToken(rso.getAccessToken());
        account.setEntitlementsToken(rso.getEntitlementsToken());
        account.setMultiFactor(multiFactorCode);
        this.updateById(account);
        return true;
    }

    //@Override
    //public boolean update(RiotAccount account) {
    //    return false;
    //}

    @Override
    public List<RiotAccount> queryAccount(RiotAccount account) {
        LambdaQueryWrapper<RiotAccount> wrapper = Wrappers.lambdaQuery();
        wrapper.select(RiotAccount::getUserId, RiotAccount::getUsername, RiotAccount::getSocialName, RiotAccount::getSocialTag, RiotAccount::getMultiFactor)
                .orderByAsc(RiotAccount::getUsername);
        if(null != account) {
            wrapper.like(StringUtils.isNotEmpty(account.getUsername()), RiotAccount::getUsername, account.getUsername())
                    .like(StringUtils.isNotEmpty(account.getSocialName()), RiotAccount::getSocialName, account.getSocialName())
                    .like(StringUtils.isNotEmpty(account.getSocialTag()), RiotAccount::getSocialTag, account.getSocialTag());
        }
        List<RiotAccount> list = accountMapper.selectList(wrapper);
        if(CollectionUtils.isNotEmpty(list)) {
            list.forEach(ac -> {
                if(StringUtils.isNotEmpty(ac.getMultiFactor())) {
                    ac.setMultiFactor("true");
                }
            });
        }
        return list;
    }

}
