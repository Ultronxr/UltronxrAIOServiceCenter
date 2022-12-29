package cn.ultronxr.gameregister.bean.mybatis.mapper;

import cn.ultronxr.gameregister.bean.mybatis.bean.Shop;
import cn.ultronxr.gameregister.bean.mybatis.bean.ShopExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ShopMapper {
    long countByExample(ShopExample example);

    int deleteByExample(ShopExample example);

    int deleteByPrimaryKey(String id);

    int insert(Shop row);

    int insertSelective(Shop row);

    List<Shop> selectByExample(ShopExample example);

    Shop selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("row") Shop row, @Param("example") ShopExample example);

    int updateByExample(@Param("row") Shop row, @Param("example") ShopExample example);

    int updateByPrimaryKeySelective(Shop row);

    int updateByPrimaryKey(Shop row);
}