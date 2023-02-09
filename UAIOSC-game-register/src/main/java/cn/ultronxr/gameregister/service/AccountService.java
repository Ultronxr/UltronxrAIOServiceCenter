package cn.ultronxr.gameregister.service;

import cn.ultronxr.gameregister.bean.mybatis.bean.Account;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Ultronxr
 * @date 2022/12/10 19:08
 * @description
 */
public interface AccountService extends IService<Account> {

    List<Account> listAccount(Account account);

}
