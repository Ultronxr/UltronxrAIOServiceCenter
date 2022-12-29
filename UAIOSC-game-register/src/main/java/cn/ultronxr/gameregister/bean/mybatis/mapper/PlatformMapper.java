package cn.ultronxr.gameregister.bean.mybatis.mapper;

import cn.ultronxr.gameregister.bean.mybatis.bean.Platform;
import cn.ultronxr.gameregister.bean.mybatis.bean.PlatformExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PlatformMapper {
    long countByExample(PlatformExample example);

    int deleteByExample(PlatformExample example);

    int deleteByPrimaryKey(String id);

    int insert(Platform row);

    int insertSelective(Platform row);

    List<Platform> selectByExample(PlatformExample example);

    Platform selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("row") Platform row, @Param("example") PlatformExample example);

    int updateByExample(@Param("row") Platform row, @Param("example") PlatformExample example);

    int updateByPrimaryKeySelective(Platform row);

    int updateByPrimaryKey(Platform row);
}