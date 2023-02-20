package cn.ultronxr.gameregister.service.impl;

import cn.ultronxr.gameregister.bean.mybatis.bean.Game;
import cn.ultronxr.gameregister.bean.mybatis.mapper.GameMapper;
import cn.ultronxr.gameregister.service.GameService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author Ultronxr
 * @date 2023/02/08 12:26
 * @description
 */
@Service
@Slf4j
public class GameServiceImpl extends ServiceImpl<GameMapper, Game> implements GameService {

    @Autowired
    private GameMapper mapper;


    @Override
    public List<Game> list(Game game) {
        LambdaQueryWrapper<Game> wrapper = Wrappers.lambdaQuery();
        wrapper.orderByDesc(Game::getPurchaseDate);

        if(null != game) {
            wrapper.eq(null != game.getParentId(),               Game::getParentId,      game.getParentId())
                    .eq(null != game.getAccountId(),             Game::getAccountId,     game.getAccountId())
                    .eq(null != game.getAppId(),                 Game::getAppId,         game.getAppId())
                    .eq(StringUtils.isNotEmpty(game.getShop()),           Game::getShop,           game.getShop())
                    .eq(StringUtils.isNotEmpty(game.getActualPlayShop()), Game::getActualPlayShop, game.getActualPlayShop())
                    .eq(StringUtils.isNotEmpty(game.getVersion()),        Game::getVersion,        game.getVersion())
                    .and(StringUtils.isNotEmpty(game.getName()),
                            i -> i.like(Game::getName, game.getName())
                                .or().like(Game::getNameEng,  game.getName())
                                .or().like(Game::getNameNick, game.getName())
                                .or().like(Game::getNameBak,  game.getName()))
                    .like(StringUtils.isNotEmpty(game.getDescription()),  Game::getDescription,    game.getDescription())
                    .like(StringUtils.isNotEmpty(game.getTag()),          Game::getTag,            game.getTag())
                    .like(StringUtils.isNotEmpty(game.getUrl()),          Game::getUrl,            game.getUrl())
                    .like(StringUtils.isNotEmpty(game.getLogo()),         Game::getLogo,           game.getLogo())
                    .like(StringUtils.isNotEmpty(game.getImg()),          Game::getImg,            game.getImg())
                    .like(StringUtils.isNotEmpty(game.getDeveloper()),    Game::getDeveloper,      game.getDeveloper())
                    .like(StringUtils.isNotEmpty(game.getPublisher()),    Game::getPublisher,      game.getPublisher());
        }
        return mapper.selectList(wrapper);
    }

    @Override
    @Transactional
    public boolean importJson(String accountId, String shopId, MultipartFile file) {
        log.info(file.getOriginalFilename());


        return true;
    }

    @Override
    @Transactional
    public boolean importAPI(String accountId, String shopId) {


        return true;
    }

}
