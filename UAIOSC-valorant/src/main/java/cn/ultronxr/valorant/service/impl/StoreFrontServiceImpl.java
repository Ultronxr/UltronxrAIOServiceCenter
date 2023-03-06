package cn.ultronxr.valorant.service.impl;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONObject;
import cn.ultronxr.valorant.api.impl.StoreFrontAPI;
import cn.ultronxr.valorant.auth.RSO;
import cn.ultronxr.valorant.bean.VO.StoreFrontVO;
import cn.ultronxr.valorant.bean.mybatis.bean.StoreFront;
import cn.ultronxr.valorant.bean.mybatis.mapper.StoreFrontMapper;
import cn.ultronxr.valorant.exception.APIUnauthorizedException;
import cn.ultronxr.valorant.service.RSOService;
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

    @Autowired
    private RSOService rsoService;


    @Override
    public List<StoreFront> singleItemOffers(String userId, String date) {
        List<StoreFront> list = queryDB(userId, date, false);

        // 如果数据库中没有数据且过了今天8点，则请求API获取，并插入数据库
        // 注意：已经过去的日期是无法再通过接口获取数据的，如果当天没有存入数据库，那么只能返回空结果！
        if((null == list || list.isEmpty()) && isDateValid(date)) {
            JSONObject jObj = requestAPI(userId);
            list = sfAPI.getSingleItemOffers(jObj, userId);
            this.saveOrUpdateBatchByMultiId(list);
        }

        return list;
    }

    @Override
    public List<StoreFront> bonusOffers(String userId, String date) {
        List<StoreFront> list = queryDB(userId, date, true);

        // 如果数据库中没有数据，则请求API获取，并插入数据库
        if((null == list || list.isEmpty()) && isDateValid(date)) {
            JSONObject jObj = requestAPI(userId);
            list = sfAPI.getBonusOffers(jObj, userId);
            this.saveOrUpdateBatchByMultiId(list);
        }

        return list;
    }

    private JSONObject requestAPI(String userId) {
        JSONObject jObj = null;
        RSO rso = rsoService.fromAccount(userId);
        try {
            jObj = sfAPI.process(rso);
        } catch (APIUnauthorizedException e1) {
            log.info("RSO token 已过期，尝试更新 token ...");
            rso = rsoService.updateRSO(userId);
            try {
                jObj = sfAPI.process(rso);
            } catch (APIUnauthorizedException e2) {

            }
        }
        return jObj;
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

    /**
     * 检查日期是否合法，合法的条件：<br/>
     *      先把 date ==> dateTime
     *      今天8点 < dateTime < 明天8点
     * @param date 前端传入的查询日期
     * @return 是否合法
     */
    private static boolean isDateValid(String date) {
        DateTime now = DateUtil.date();
        DateTime dateTime = DateUtil.parse(date)
                .setField(DateField.HOUR_OF_DAY, now.getField(DateField.HOUR_OF_DAY))
                .setField(DateField.MINUTE, now.getField(DateField.MINUTE))
                .setField(DateField.SECOND, now.getField(DateField.SECOND))
                .setField(DateField.MILLISECOND, now.getField(DateField.MILLISECOND));
        DateTime today8AM = DateUtil.date()
                .setField(DateField.HOUR_OF_DAY, 8)
                .setField(DateField.MINUTE, 0)
                .setField(DateField.SECOND, 0)
                .setField(DateField.MILLISECOND, 0);
        DateTime tomorrow8AM = DateUtil.date(
                DateUtils.addDays(DateUtil.date(today8AM).toJdkDate(), 1)
        );
        boolean valid = DateUtil.compare(today8AM, dateTime) < 0
                && DateUtil.compare(dateTime, tomorrow8AM) < 0;
        log.info("dateTime={}, today8AM={}, tomorrow8AM={}", dateTime.toString(), today8AM.toString(), tomorrow8AM.toString());
        log.info("valid={}", valid);
        return valid;
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
        return DateUtil.date(dateObj).toDateStr();
    }

}
