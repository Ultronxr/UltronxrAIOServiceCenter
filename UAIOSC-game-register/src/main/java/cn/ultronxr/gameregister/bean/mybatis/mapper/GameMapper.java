package cn.ultronxr.gameregister.bean.mybatis.mapper;

import cn.ultronxr.gameregister.bean.mybatis.bean.Game;
import cn.ultronxr.gameregister.bean.mybatis.bean.GameExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GameMapper {
    long countByExample(GameExample example);

    int deleteByExample(GameExample example);

    int deleteByPrimaryKey(Long gameId);

    int insert(Game row);

    int insertSelective(Game row);

    List<Game> selectByExample(GameExample example);

    Game selectByPrimaryKey(Long gameId);

    int updateByExampleSelective(@Param("row") Game row, @Param("example") GameExample example);

    int updateByExample(@Param("row") Game row, @Param("example") GameExample example);

    int updateByPrimaryKeySelective(Game row);

    int updateByPrimaryKey(Game row);
}