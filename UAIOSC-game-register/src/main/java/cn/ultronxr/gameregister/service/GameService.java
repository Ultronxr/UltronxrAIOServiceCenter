package cn.ultronxr.gameregister.service;

import cn.ultronxr.gameregister.bean.mybatis.bean.Game;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Ultronxr
 * @date 2023/02/08 12:26
 * @description
 */
public interface GameService extends IService<Game> {

    List<Game> list(Game game);

}
