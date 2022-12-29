package cn.ultronxr.gameregister.service.impl;

import cn.hutool.db.sql.Condition;
import cn.hutool.db.sql.SqlUtil;
import cn.ultronxr.gameregister.bean.mybatis.bean.Game;
import cn.ultronxr.gameregister.bean.mybatis.bean.GameExample;
import cn.ultronxr.gameregister.bean.mybatis.mapper.GameMapper;
import cn.ultronxr.gameregister.service.GameService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Ultronxr
 * @date 2022/12/10 19:09
 * @description
 */
@Service
@Slf4j
public class GameServiceImpl implements GameService {

    @Resource
    private GameMapper gameMapper;


    @Override
    public int createGame(Game game) {
        game.setId(null);
        return gameMapper.insert(game);
    }

    @Override
    public int deleteGame(long id) {
        return gameMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int updateGame(Game game) {
        return gameMapper.updateByPrimaryKey(game);
    }

    @Override
    public List<Game> queryGame(Game game) {
        GameExample example = new GameExample();
        example.setOrderByClause("purchase_date desc");
        if(game != null) {
            GameExample.Criteria criteria = example.createCriteria();

            if(game.getParentId() != null) {
                criteria.andParentIdEqualTo(game.getParentId());
            }
            if(StringUtils.isNotEmpty(game.getVersion())) {
                criteria.andVersionLike(SqlUtil.buildLikeValue(game.getVersion(), Condition.LikeType.Contains, false));
            }
            if(StringUtils.isNotEmpty(game.getName())) {
                criteria.andNameLike(SqlUtil.buildLikeValue(game.getName(), Condition.LikeType.Contains, false));
            }
            if(StringUtils.isNotEmpty(game.getNameEng())) {
                criteria.andNameEngLike(SqlUtil.buildLikeValue(game.getNameEng(), Condition.LikeType.Contains, false));
            }
            if(StringUtils.isNotEmpty(game.getNameNick())) {
                criteria.andNameNickLike(SqlUtil.buildLikeValue(game.getNameNick(), Condition.LikeType.Contains, false));
            }
            if(StringUtils.isNotEmpty(game.getNameBak())) {
                criteria.andNameBakLike(SqlUtil.buildLikeValue(game.getNameBak(), Condition.LikeType.Contains, false));
            }
            if(StringUtils.isNotEmpty(game.getDescription())) {
                criteria.andDescriptionLike(SqlUtil.buildLikeValue(game.getDescription(), Condition.LikeType.Contains, false));
            }
            if(StringUtils.isNotEmpty(game.getTag())) {
                criteria.andTagLike(SqlUtil.buildLikeValue(game.getTag(), Condition.LikeType.Contains, false));
            }
            if(StringUtils.isNotEmpty(game.getUrl())) {
                criteria.andUrlLike(SqlUtil.buildLikeValue(game.getUrl(), Condition.LikeType.Contains, false));
            }
            if(StringUtils.isNotEmpty(game.getLogo())) {
                criteria.andLogoLike(SqlUtil.buildLikeValue(game.getLogo(), Condition.LikeType.Contains, false));
            }
            if(StringUtils.isNotEmpty(game.getImg())) {
                criteria.andImgLike(SqlUtil.buildLikeValue(game.getImg(), Condition.LikeType.Contains, false));
            }
            if(StringUtils.isNotEmpty(game.getDeveloper())) {
                criteria.andDeveloperLike(SqlUtil.buildLikeValue(game.getDeveloper(), Condition.LikeType.Contains, false));
            }
            if(StringUtils.isNotEmpty(game.getPublisher())) {
                criteria.andPublisherLike(SqlUtil.buildLikeValue(game.getPublisher(), Condition.LikeType.Contains, false));
            }
            if(StringUtils.isNotEmpty(game.getActualShop())) {
                criteria.andActualShopLike(SqlUtil.buildLikeValue(game.getActualShop(), Condition.LikeType.Contains, false));
            }
        }
        return gameMapper.selectByExample(example);
    }
}
