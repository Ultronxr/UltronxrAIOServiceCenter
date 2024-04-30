package cn.ultronxr.tool.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.ultronxr.tool.service.RandomService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;

/**
 * @author Ultronxr
 * @date 2024/04/28 10:27:48
 * @description
 */
@Service
@Slf4j
public class RandomServiceImpl implements RandomService {

    /** 随机字符串默认长度 */
    private static final Integer RANDOM_STRING_DEFAULT_LENGTH = 8;


    @Override
    public String randomString(@Nullable Integer length) {
        return RandomUtil.randomString(length == null ? RANDOM_STRING_DEFAULT_LENGTH : length);
    }

}
