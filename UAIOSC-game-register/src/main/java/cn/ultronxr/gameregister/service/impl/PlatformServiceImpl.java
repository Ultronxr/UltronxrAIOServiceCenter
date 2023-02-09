package cn.ultronxr.gameregister.service.impl;

import cn.ultronxr.gameregister.bean.mybatis.bean.Platform;
import cn.ultronxr.gameregister.bean.mybatis.mapper.PlatformMapper;
import cn.ultronxr.gameregister.service.PlatformService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @author Ultronxr
 * @date 2022/12/29 16:46
 * @description
 */
@Service
@Slf4j
public class PlatformServiceImpl extends ServiceImpl<PlatformMapper, Platform> implements PlatformService {

    @Autowired
    private PlatformMapper mapper;


    @Override
    public List<Platform> listPlatform(Platform platform) {
        LambdaQueryWrapper<Platform> wrapper = Wrappers.lambdaQuery();
        wrapper.orderByAsc(Arrays.asList(Platform::getSort, Platform::getId));

        if(null != platform) {
            wrapper.like(StringUtils.isNotEmpty(platform.getId()), Platform::getId, platform.getId())
                    .like(StringUtils.isNotEmpty(platform.getName()), Platform::getName, platform.getName());
        }
        return mapper.selectList(wrapper);
    }

}
