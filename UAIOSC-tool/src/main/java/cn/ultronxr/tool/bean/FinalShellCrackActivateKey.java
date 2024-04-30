package cn.ultronxr.tool.bean;

import lombok.Data;

/**
 * @author Ultronxr
 * @date 2024/04/28 18:50:25
 * @description FinalShell 破解激活码
 */
@Data
public class FinalShellCrackActivateKey {

    /** 旧版本高级版激活码（版本号 < 3.9.6 (旧版)） */
    private String oldVersionAdvanced;
    /** 旧版本专业版激活码（版本号 < 3.9.6 (旧版)） */
    private String oldVersionProfessional;

    /** 新版本高级版激活码（版本号 >= 3.9.6 (新版)） */
    private String newVersionAdvanced;
    /** 新版本专业版激活码（版本号 >= 3.9.6 (新版)） */
    private String newVersionProfessional;

}
