package cn.ultronxr.gameregister.service;

import cn.ultronxr.gameregister.bean.mybatis.bean.Account;

import java.util.List;

/**
 * @author Ultronxr
 * @date 2022/12/10 19:08
 * @description
 */
public interface AccountService {

    int createAccount(Account account);

    int deleteAccount(int id);

    int updateAccount(Account account);

    List<Account> queryAccount(Account account);

}
