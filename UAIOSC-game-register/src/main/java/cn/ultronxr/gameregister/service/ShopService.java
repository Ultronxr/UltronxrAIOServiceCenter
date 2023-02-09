package cn.ultronxr.gameregister.service;


import cn.ultronxr.gameregister.bean.mybatis.bean.Shop;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Ultronxr
 * @date 2022/12/29 16:46
 * @description
 */
public interface ShopService extends IService<Shop> {

    List<Shop> listShop(Shop shop);

}
