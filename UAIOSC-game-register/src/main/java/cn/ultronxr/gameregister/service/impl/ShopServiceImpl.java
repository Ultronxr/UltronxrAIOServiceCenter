package cn.ultronxr.gameregister.service.impl;

import cn.ultronxr.gameregister.bean.mybatis.bean.Shop;
import cn.ultronxr.gameregister.bean.mybatis.mapper.ShopMapper;
import cn.ultronxr.gameregister.service.ShopService;
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
 * @date 2022/12/29 16:47
 * @description
 */
@Service
@Slf4j
public class ShopServiceImpl extends ServiceImpl<ShopMapper, Shop> implements ShopService {

    @Autowired
    private ShopMapper mapper;


    @Override
    public List<Shop> listShop(Shop shop) {
        LambdaQueryWrapper<Shop> wrapper = Wrappers.lambdaQuery();
        wrapper.orderByAsc(Arrays.asList(Shop::getSort, Shop::getId));

        if(null != shop) {
            wrapper.like(StringUtils.isNotEmpty(shop.getId()), Shop::getId, shop.getId())
                    .like(StringUtils.isNotEmpty(shop.getName()), Shop::getName, shop.getName());
        }
        return mapper.selectList(wrapper);
    }

}
