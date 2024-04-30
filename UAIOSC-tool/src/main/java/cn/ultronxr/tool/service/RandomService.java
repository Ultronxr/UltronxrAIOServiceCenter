package cn.ultronxr.tool.service;

import org.jetbrains.annotations.Nullable;

/**
 * @author Ultronxr
 * @date 2024/04/28 10:27:14
 * @description 随机 工具 service
 */
public interface RandomService {

    /**
     * 生成随机字符串
     * @param length 字符串长度；<br/>
     *               可为 {@code null} ，此时使用默认长度 8
     * @return {@code String} 指定长度的随机字符串；若未指定长度，则使用默认长度
     */
    String randomString(@Nullable Integer length);

}
