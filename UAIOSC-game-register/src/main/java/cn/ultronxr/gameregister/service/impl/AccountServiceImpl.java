package cn.ultronxr.gameregister.service.impl;

import cn.hutool.core.date.CalendarUtil;
import cn.hutool.db.sql.Condition;
import cn.hutool.db.sql.SqlUtil;
import cn.ultronxr.gameregister.bean.mybatis.bean.Account;
import cn.ultronxr.gameregister.bean.mybatis.bean.AccountExample;
import cn.ultronxr.gameregister.bean.mybatis.mapper.AccountMapper;
import cn.ultronxr.gameregister.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountMapper mapper;


    @Override
    public int create(Account account) {
        account.setId(null);
        account.setCreateTime(CalendarUtil.calendar().getTime());
        account.setCreateBy("ReplaceMe!");
        return mapper.insertSelective(account);
    }

    @Override
    public int delete(int id) {
        return mapper.deleteByPrimaryKey(id);
    }

    @Override
    public int delete(List<Integer> idList) {
        AccountExample example = new AccountExample();
        example.createCriteria().andIdIn(idList);
        return mapper.deleteByExample(example);
    }

    @Override
    public int update(Account account) {
        account.setUpdateTime(CalendarUtil.calendar().getTime());
        account.setUpdateBy("ReplaceMe!");
        return mapper.updateByPrimaryKeySelective(account);
    }

    @Override
    public List<Account> query(Account account) {
        AccountExample example = new AccountExample();
        example.setOrderByClause("create_time asc");
        if(account != null) {
            AccountExample.Criteria criteria = example.createCriteria();

            if(StringUtils.isNotEmpty(account.getNick())) {
                criteria.andNickLike(SqlUtil.buildLikeValue(account.getNick(), Condition.LikeType.Contains, false));
            }
            if(StringUtils.isNotEmpty(account.getUsername())) {
                criteria.andUsernameLike(SqlUtil.buildLikeValue(account.getUsername(), Condition.LikeType.Contains, false));
            }
            if(StringUtils.isNotEmpty(account.getEmail())) {
                criteria.andEmailLike(SqlUtil.buildLikeValue(account.getEmail(), Condition.LikeType.Contains, false));
            }
            if(StringUtils.isNotEmpty(account.getPhone())) {
                criteria.andPhoneLike(SqlUtil.buildLikeValue(account.getPhone(), Condition.LikeType.Contains, false));
            }
            if(StringUtils.isNotEmpty(account.getPlatform())) {
                criteria.andPlatformLike(SqlUtil.buildLikeValue(account.getPlatform(), Condition.LikeType.Contains, false));
            }
            if(StringUtils.isNotEmpty(account.getShop())) {
                criteria.andShopLike(SqlUtil.buildLikeValue(account.getShop(), Condition.LikeType.Contains, false));
            }
            if(StringUtils.isNotEmpty(account.getRegion())) {
                criteria.andRegionLike(SqlUtil.buildLikeValue(account.getRegion(), Condition.LikeType.Contains, false));
            }
            if(StringUtils.isNotEmpty(account.getNote())) {
                criteria.andNoteLike(SqlUtil.buildLikeValue(account.getNote(), Condition.LikeType.Contains, false));
            }
        }
        return mapper.selectByExample(example);
    }

}
