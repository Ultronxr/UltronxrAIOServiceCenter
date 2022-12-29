package cn.ultronxr.gameregister.service;

import cn.ultronxr.gameregister.bean.mybatis.bean.Shop;

import java.util.List;

/**
 * @author Ultronxr
 * @date 2022/12/29 16:46
 * @description
 */
public interface ShopService {

    int create(Shop shop);

    int delete(String id);

    int update(Shop shop);

    List<Shop> query(Shop shop);

}
