package cn.ultronxr.gameregister.service;

import cn.ultronxr.gameregister.bean.mybatis.bean.Account;

import java.util.List;

/**
 * @author Ultronxr
 * @date 2022/12/10 19:08
 * @description
 */
public interface AccountService {

    int create(Account account);

    int delete(int id);

    int delete(List<Integer> idList);

    int update(Account account);

    List<Account> query(Account account);

}
