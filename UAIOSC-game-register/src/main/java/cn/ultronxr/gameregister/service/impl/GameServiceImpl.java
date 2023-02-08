package cn.ultronxr.gameregister.service.impl;

import cn.hutool.core.date.CalendarUtil;
import cn.hutool.db.sql.Condition;
import cn.hutool.db.sql.SqlUtil;
import cn.ultronxr.framework.cache.user.UserCache;
import cn.ultronxr.gameregister.bean.mybatis.bean.Game;
import cn.ultronxr.gameregister.bean.mybatis.bean.GameExample;
import cn.ultronxr.gameregister.bean.mybatis.mapper.GameMapper;
import cn.ultronxr.gameregister.service.GameService;
import cn.ultronxr.system.bean.mybatis.bean.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Ultronxr
 * @date 2022/12/10 19:09
 * @description
 */
@Service
@Slf4j
public class GameServiceImpl implements GameService {

    @Autowired
    private GameMapper gameMapper;


    @Override
    public int create(Game game) {
        game.setGameId(null);
        game.setCreateTime(CalendarUtil.calendar().getTime());
        game.setCreateBy(((User) UserCache.getUser()).getUsername());
        return gameMapper.insert(game);
    }

    @Override
    public int delete(long id) {
        return gameMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int update(Game game) {
        game.setUpdateTime(CalendarUtil.calendar().getTime());
        game.setUpdateBy(((User) UserCache.getUser()).getUsername());
        return gameMapper.updateByPrimaryKey(game);
    }

    @Override
    public List<Game> query(Game game) {
        GameExample example = new GameExample();
        example.setOrderByClause("purchase_date desc");
        if(game != null) {
            GameExample.Criteria criteria = example.createCriteria();

            if(game.getParentId() != null) {
                criteria.andParentIdEqualTo(game.getParentId());
            }
            if(game.getAccountId() != null) {
                criteria.andAccountIdEqualTo(game.getAccountId());
            }
            if(game.getAppId() != null) {
                criteria.andAppIdEqualTo(game.getAppId());
            }
            if(StringUtils.isNotEmpty(game.getShop())) {
                criteria.andShopLike(SqlUtil.buildLikeValue(game.getShop(), Condition.LikeType.Contains, false));
            }
            if(StringUtils.isNotEmpty(game.getActualPlayShop())) {
                criteria.andActualPlayShopLike(SqlUtil.buildLikeValue(game.getActualPlayShop(), Condition.LikeType.Contains, false));
            }
            if(StringUtils.isNotEmpty(game.getVersion())) {
                criteria.andVersionLike(SqlUtil.buildLikeValue(game.getVersion(), Condition.LikeType.Contains, false));
            }
            //// 查询的时候游戏名只有一个输入框，直接用这个游戏名搜索所有名称相关字段
            //if(StringUtils.isNotEmpty(game.getName())) {
            //    criteria.andNameLike(SqlUtil.buildLikeValue(game.getName(), Condition.LikeType.Contains, false));
            //    //criteria.andNameEngLike(SqlUtil.buildLikeValue(game.getName(), Condition.LikeType.Contains, false));
            //    //criteria.andNameNickLike(SqlUtil.buildLikeValue(game.getName(), Condition.LikeType.Contains, false));
            //    //criteria.andNameBakLike(SqlUtil.buildLikeValue(game.getName(), Condition.LikeType.Contains, false));
            //}
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
            // TODO 2023年2月8日12点15分 完成此处模糊查询条件
            // 查询的时候游戏名只有一个输入框，直接用这个游戏名搜索所有名称相关字段
            if(StringUtils.isNotEmpty(game.getName())) {
                example.or().andNameLike(SqlUtil.buildLikeValue(game.getName(), Condition.LikeType.Contains, false));
                example.or().andNameEngLike(SqlUtil.buildLikeValue(game.getName(), Condition.LikeType.Contains, false));
                example.or().andNameNickLike(SqlUtil.buildLikeValue(game.getName(), Condition.LikeType.Contains, false));
                example.or().andNameBakLike(SqlUtil.buildLikeValue(game.getName(), Condition.LikeType.Contains, false));
                //criteria.andNameLike(SqlUtil.buildLikeValue(game.getName(), Condition.LikeType.Contains, false));
                //criteria.andNameEngLike(SqlUtil.buildLikeValue(game.getName(), Condition.LikeType.Contains, false));
                //criteria.andNameNickLike(SqlUtil.buildLikeValue(game.getName(), Condition.LikeType.Contains, false));
                //criteria.andNameBakLike(SqlUtil.buildLikeValue(game.getName(), Condition.LikeType.Contains, false));
            }
        }
        return gameMapper.selectByExample(example);
    }
}
