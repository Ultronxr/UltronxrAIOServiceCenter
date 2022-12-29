package cn.ultronxr.gameregister.service.impl;

import cn.hutool.db.sql.Condition;
import cn.hutool.db.sql.SqlUtil;
import cn.ultronxr.gameregister.bean.mybatis.bean.Shop;
import cn.ultronxr.gameregister.bean.mybatis.bean.ShopExample;
import cn.ultronxr.gameregister.bean.mybatis.mapper.ShopMapper;
import cn.ultronxr.gameregister.service.ShopService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Ultronxr
 * @date 2022/12/29 16:47
 * @description
 */
@Service
@Slf4j
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopMapper mapper;


    @Override
    public int create(Shop shop) {
        if(StringUtils.isEmpty(shop.getId()) || StringUtils.isEmpty(shop.getName()) || shop.getSort() == null) {
            return 0;
        }
        return mapper.insert(shop);
    }

    @Override
    public int delete(String id) {
        return mapper.deleteByPrimaryKey(id);
    }

    @Override
    public int update(Shop shop) {
        if(StringUtils.isEmpty(shop.getId()) || StringUtils.isEmpty(shop.getName()) || shop.getSort() == null) {
            return 0;
        }
        return mapper.updateByPrimaryKey(shop);
    }

    @Override
    public List<Shop> query(Shop shop) {
        ShopExample example = new ShopExample();
        example.setOrderByClause("sort asc, id asc");
        if(shop != null) {
            ShopExample.Criteria criteria = example.createCriteria();
            if(StringUtils.isNotEmpty(shop.getId())) {
                criteria.andIdLike(SqlUtil.buildLikeValue(shop.getId(), Condition.LikeType.Contains, false));
            }
            if(StringUtils.isNotEmpty(shop.getName())) {
                criteria.andNameLike(SqlUtil.buildLikeValue(shop.getName(), Condition.LikeType.Contains, false));
            }
        }
        return mapper.selectByExample(example);
    }
}
