package cn.ultronxr.gameregister.service.impl;

import cn.hutool.db.sql.Condition;
import cn.hutool.db.sql.SqlUtil;
import cn.ultronxr.gameregister.bean.mybatis.bean.Platform;
import cn.ultronxr.gameregister.bean.mybatis.bean.PlatformExample;
import cn.ultronxr.gameregister.bean.mybatis.mapper.PlatformMapper;
import cn.ultronxr.gameregister.service.PlatformService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Ultronxr
 * @date 2022/12/29 16:46
 * @description
 */
@Service
@Slf4j
public class PlatformServiceImpl implements PlatformService {

    @Autowired
    private PlatformMapper mapper;


    @Override
    public int create(Platform platform) {
        if(StringUtils.isEmpty(platform.getId()) || StringUtils.isEmpty(platform.getName()) || platform.getSort() == null) {
            return 0;
        }
        return mapper.insert(platform);
    }

    @Override
    public int delete(String id) {
        return mapper.deleteByPrimaryKey(id);
    }

    @Override
    public int update(Platform platform) {
        if(StringUtils.isEmpty(platform.getId()) || StringUtils.isEmpty(platform.getName()) || platform.getSort() == null) {
            return 0;
        }
        return mapper.updateByPrimaryKey(platform);
    }

    @Override
    public List<Platform> query(Platform platform) {
        PlatformExample example = new PlatformExample();
        example.setOrderByClause("sort asc, id asc");
        if(platform != null) {
            PlatformExample.Criteria criteria = example.createCriteria();
            if(StringUtils.isNotEmpty(platform.getId())) {
                criteria.andIdLike(SqlUtil.buildLikeValue(platform.getId(), Condition.LikeType.Contains, false));
            }
            if(StringUtils.isNotEmpty(platform.getName())) {
                criteria.andNameLike(SqlUtil.buildLikeValue(platform.getName(), Condition.LikeType.Contains, false));
            }
        }
        return mapper.selectByExample(example);
    }
}
