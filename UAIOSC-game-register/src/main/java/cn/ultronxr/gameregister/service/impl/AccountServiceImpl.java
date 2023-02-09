package cn.ultronxr.gameregister.service.impl;

import cn.ultronxr.gameregister.bean.mybatis.bean.Account;
import cn.ultronxr.gameregister.bean.mybatis.mapper.AccountMapper;
import cn.ultronxr.gameregister.service.AccountService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Ultronxr
 * @date 2022/12/10 19:08
 * @description
 */
@Service
@Slf4j
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements AccountService {

    @Autowired
    private AccountMapper mapper;


    @Override
    public List<Account> listAccount(Account account) {
        LambdaQueryWrapper<Account> wrapper = Wrappers.lambdaQuery();
        wrapper.orderByAsc(Account::getCreateTime);

        if(null != account) {
            wrapper.like(StringUtils.isNotEmpty(account.getNick()), Account::getNick, account.getNick())
                    .like(StringUtils.isNotEmpty(account.getUsername()), Account::getUsername, account.getUsername())
                    .like(StringUtils.isNotEmpty(account.getEmail()), Account::getEmail, account.getEmail())
                    .like(StringUtils.isNotEmpty(account.getPhone()), Account::getPhone, account.getPhone())
                    .like(StringUtils.isNotEmpty(account.getPlatform()), Account::getPlatform, account.getPlatform())
                    .like(StringUtils.isNotEmpty(account.getShop()), Account::getShop, account.getShop())
                    .like(StringUtils.isNotEmpty(account.getRegion()), Account::getRegion, account.getRegion())
                    .like(StringUtils.isNotEmpty(account.getNote()), Account::getNote, account.getNote());
        }
        return mapper.selectList(wrapper);
    }

}
