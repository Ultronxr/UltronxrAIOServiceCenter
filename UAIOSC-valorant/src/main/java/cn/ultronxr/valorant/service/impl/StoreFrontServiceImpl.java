package cn.ultronxr.valorant.service.impl;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONObject;
import cn.ultronxr.valorant.api.impl.StoreFrontAPI;
import cn.ultronxr.valorant.bean.VO.StoreFrontVO;
import cn.ultronxr.valorant.bean.mybatis.bean.StoreFront;
import cn.ultronxr.valorant.bean.mybatis.mapper.StoreFrontMapper;
import cn.ultronxr.valorant.service.StoreFrontService;
import cn.ultronxr.valorant.service.WeaponSkinService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.jeffreyning.mybatisplus.service.MppServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Ultronxr
 * @date 2023/02/22 10:21
 * @description
 */
@Service
@Slf4j
public class StoreFrontServiceImpl extends MppServiceImpl<StoreFrontMapper, StoreFront> implements StoreFrontService {

    @Autowired
    private StoreFrontAPI sfAPI;

    @Autowired
    private StoreFrontMapper sfMapper;

    @Autowired
    private WeaponSkinService wsService;


    @Override
    public List<StoreFront> singleItemOffers(String userId, String date) {
        List<StoreFront> list = queryDB(userId, date, false);

        // 如果数据库中没有数据且过了今天8点，则请求API获取，并插入数据库
        if((null == list || list.isEmpty()) && isNowAfterToday8AM()) {
            JSONObject jObj = sfAPI.process(userId);
            list = sfAPI.getSingleItemOffers(jObj, userId);
            this.saveOrUpdateBatchByMultiId(list);
        }

        return list;
    }

    @Override
    public List<StoreFront> bonusOffers(String userId, String date) {
        List<StoreFront> list = queryDB(userId, date, true);

        // 如果数据库中没有数据，则请求API获取，并插入数据库
        if((null == list || list.isEmpty()) && isNowAfterToday8AM()) {
            JSONObject jObj = sfAPI.process(userId);
            list = sfAPI.getBonusOffers(jObj, userId);
            this.saveOrUpdateBatchByMultiId(list);
        }

        return list;
    }

    @Override
    public List<StoreFrontVO> toVO(List<StoreFront> sfList) {
        List<StoreFrontVO> voList = new ArrayList<>(sfList.size());
        sfList.forEach(sf -> {
            StoreFrontVO sfv = new StoreFrontVO().toVO(sf);
            sfv.setWeaponSkin(wsService.getById(sf.getItemId()));
            // 注意这里的 itemId 并不是直接的 皮肤ID，而是 皮肤升级ID，要先查出其父皮肤ID
            String skinId = wsService.getParentSkinIdByLevelOrChromaId(sf.getItemId());
            sfv.setWeaponSkinLevels(wsService.getLevels(skinId));
            sfv.setWeaponSkinChromas(wsService.getChromas(skinId));
            voList.add(sfv);
        });
        return voList;
    }

    private List<StoreFront> queryDB(String userId, String date, boolean isBonus) {
        // 早上8点前查询，返回的是前一天的数据
        if(!isNowAfterToday8AM()) {
            date = addDays(date, -1);
        }
        LambdaQueryWrapper<StoreFront> wrapper = Wrappers.lambdaQuery();
        wrapper.orderByAsc(StoreFront::getOfferId)
                .eq(StoreFront::getUserId, userId)
                .eq(StoreFront::getIsBonus, isBonus)
                .eq(!isBonus, StoreFront::getDate, date)
                .gt(isBonus, StoreFront::getDate, date);
        return sfMapper.selectList(wrapper);
    }

    private static boolean isNowAfterToday8AM() {
        DateTime now = DateUtil.date();
        DateTime today8AM = DateUtil.date()
                .setField(DateField.HOUR_OF_DAY, 8)
                .setField(DateField.MINUTE, 0)
                .setField(DateField.SECOND, 0)
                .setField(DateField.MILLISECOND, 0);
        return DateUtil.compare(now, today8AM) >= 0;
    }

    private static String addDays(String date, int amount) {
        Date dateObj = DateUtil.parseDate(date);
        dateObj = DateUtils.addDays(dateObj, amount);
        return dateObj.toString();
    }

}
