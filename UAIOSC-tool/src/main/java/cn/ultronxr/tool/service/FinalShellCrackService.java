package cn.ultronxr.tool.service;

import cn.ultronxr.tool.bean.FinalShellCrackActivateKey;

/**
 * @author Ultronxr
 * @date 2024/04/28 16:53:56
 * @description FinalShell 破解工具 service
 */
public interface FinalShellCrackService {

    /**
     * 获取激活码
     * @param machineCode 机器码
     * @return {@code FinalShellCrackActivateKey} 激活码对象
     */
    FinalShellCrackActivateKey getActivateKey(String machineCode);

}
