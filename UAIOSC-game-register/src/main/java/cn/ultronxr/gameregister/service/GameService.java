package cn.ultronxr.gameregister.service;

import cn.ultronxr.gameregister.bean.mybatis.bean.Game;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author Ultronxr
 * @date 2023/02/08 12:26
 * @description
 */
public interface GameService extends IService<Game> {

    List<Game> list(Game game);

    /**
     * 从现成的 json 文件中导入游戏数据<br/>
     * 注：该方法是事务性的，导入的游戏数据要么全部成功（插入数据库），要么全部失败（数据库回滚至插入数据前的状态）
     *
     * @param accountId 游戏账户ID
     * @param shopId    游戏商城ID
     * @param file      json 文件
     */
    @Transactional
    boolean importJson(String accountId, String shopId, MultipartFile file);

    /**
     * 从请求 官方API 返回的数据中导入游戏数据（例如 Steam API、Epic API 等） <br/>
     * 注：该方法是事务性的，导入的游戏数据要么全部成功（插入数据库），要么全部失败（数据库回滚至插入数据前的状态）
     *
     * @param accountId 游戏账户ID
     * @param shopId    游戏商城ID
     */
    @Transactional
    boolean importAPI(String accountId, String shopId);

}
