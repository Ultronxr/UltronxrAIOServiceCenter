package cn.ultronxr.gameregister.service;

import cn.ultronxr.gameregister.bean.mybatis.bean.Game;

import java.util.List;

/**
 * @author Ultronxr
 * @date 2022/12/10 19:09
 * @description
 */
public interface GameService {

    int createGame(Game game);

    int deleteGame(long id);

    int updateGame(Game game);

    List<Game> queryGame(Game game);

}
