package cn.ultronxr.valorant.service.impl;

import cn.ultronxr.common.executor.PythonExecutor;
import cn.ultronxr.valorant.auth.RSO;
import cn.ultronxr.valorant.bean.mybatis.bean.RiotAccount;
import cn.ultronxr.valorant.bean.mybatis.mapper.RiotAccountMapper;
import cn.ultronxr.valorant.service.RSOService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Ultronxr
 * @date 2023/03/04 12:30
 * @description
 */
@Service
@Slf4j
public class RSOServiceImpl implements RSOService {

    private static final String PYTHON_FILE_NAME = "example_auth_argv.py";

    @Autowired
    private RiotAccountMapper accountMapper;


    @Override
    public RSO initRSO(String username, String password) {
        RSO rso = null;
        List<String> res = PythonExecutor.execPythonFileSync(PYTHON_FILE_NAME, username, password);
        log.info("{}", res);
        if(res != null && res.size() > 0) {
            rso = new RSO();
            String accessToken = res.get(1).replaceFirst("Access Token: ", ""),
                    entitlementsToken = res.get(2).replaceFirst("Entitlements Token: ", ""),
                    userId = res.get(3).replaceFirst("User ID: ", "");
            rso.updateToken(accessToken, entitlementsToken, userId);
        }
        return rso;
    }

    @Override
    public RSO updateRSO(String userId) {
        RSO rso = null;
        RiotAccount account = accountMapper.selectById(userId);
        List<String> res = PythonExecutor.execPythonFileSync(PYTHON_FILE_NAME, account.getUsername(), account.getPassword());
        log.info("{}", res);
        if(res != null && res.size() > 0) {
            rso = new RSO();
            String accessToken = res.get(1).replaceFirst("Access Token: ", ""),
                    entitlementsToken = res.get(2).replaceFirst("Entitlements Token: ", "");
            rso.updateToken(accessToken, entitlementsToken, userId);

            account.setAccessToken(accessToken);
            account.setEntitlementsToken(entitlementsToken);
            accountMapper.updateById(account);
        }
        return rso;
    }

    @Override
    public RSO getRSOByAccount(RiotAccount account) {
        RSO rso;
        if(StringUtils.isNotEmpty(account.getUserId())) {
            rso = updateRSO(account.getUserId());
        } else {
            rso = initRSO(account.getUsername(), account.getPassword());
        }
        return rso;
    }

    @Override
    public RSO fromAccount(RiotAccount account) {
        RSO rso = new RSO();
        rso.updateToken(account.getAccessToken(), account.getEntitlementsToken(), account.getUserId());
        return rso;
    }

    @Override
    public RSO fromAccount(String userId) {
        RiotAccount account = accountMapper.selectById(userId);
        return fromAccount(account);
    }

}
