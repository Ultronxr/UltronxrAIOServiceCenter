package cn.ultronxr.valorant.api.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.ultronxr.valorant.api.BaseAPI;
import cn.ultronxr.valorant.api.InGameAPIEnum;
import cn.ultronxr.valorant.auth.RSO;
import cn.ultronxr.valorant.bean.mybatis.bean.StoreFront;
import cn.ultronxr.valorant.exception.APIUnauthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ultronxr
 * @date 2023/02/20 14:49
 * @description <b>获取商城中当前出售物品</b>API<br/>
 */
@Component
@Slf4j
public class StoreFrontAPI extends BaseAPI {

    private static final String API = InGameAPIEnum.StoreFront.getUrl();

    private static final String GAME_CURRENCY_VP_ID = "85ad13f7-3d1b-5128-9eb2-7cd8ee0b5741";


    public JSONObject process(RSO rso) throws APIUnauthorizedException {
        String responseBody = requestAPI(API.replace("{userId}", rso.getUserId()), rso.getRequestHeaderMap());
        return parseData(responseBody);
    }

    @Override
    public JSONObject parseData(String responseBody) throws APIUnauthorizedException {
        JSONObject obj = JSONUtil.parseObj(responseBody, false);
        if(obj.getInt("httpStatus", 200) == 400 && obj.getStr("errorCode", "OK").equals("BAD_CLAIMS")) {
            throw new APIUnauthorizedException();
        }
        return obj;
    }

    public List<StoreFront> getSingleItemOffers(JSONObject rootJsonObj, String userId) {
        List<StoreFront> list = new ArrayList<>();
        Long remainDurationInSeconds = rootJsonObj.getByPath("SkinsPanelLayout.SingleItemOffersRemainingDurationInSeconds", Long.class);
        JSONArray singleItemOffersArray = rootJsonObj.getByPath("SkinsPanelLayout.SingleItemStoreOffers", JSONArray.class);
        singleItemOffersArray.forEach(obj -> {
            JSONObject jObj = (JSONObject) obj;
            StoreFront storeFront = new StoreFront();
            storeFront.setUserId(userId);
            storeFront.setOfferId(jObj.getStr("OfferID"));
            storeFront.setIsDirectPurchase(jObj.getBool("IsDirectPurchase"));
            storeFront.setDate(parseResponseDate(jObj.getStr("StartDate"), remainDurationInSeconds));
            storeFront.setCurrencyId(GAME_CURRENCY_VP_ID);
            storeFront.setCost(jObj.getJSONObject("Cost").getInt(GAME_CURRENCY_VP_ID));
            storeFront.setItemTypeId(jObj.getByPath("Rewards[0].ItemTypeID", String.class));
            storeFront.setItemId(jObj.getByPath("Rewards[0].ItemID", String.class));
            storeFront.setQuantity(jObj.getByPath("Rewards[0].Quantity", Integer.class));
            storeFront.setIsBonus(false);
            list.add(storeFront);
        });
        return list;
    }

    public List<StoreFront> getBonusOffers(JSONObject rootJsonObj, String userId) {
        List<StoreFront> list = new ArrayList<>();
        Long remainDurationInSeconds = rootJsonObj.getByPath("BonusStore.BonusStoreRemainingDurationInSeconds", Long.class);
        JSONArray bonusOffersArray = rootJsonObj.getByPath("BonusStore.BonusStoreOffers", JSONArray.class);
        bonusOffersArray.forEach(obj -> {
            JSONObject jObj = (JSONObject) obj;
            StoreFront storeFront = new StoreFront();
            storeFront.setUserId(userId);
            storeFront.setOfferId(jObj.getByPath("Offer.OfferID", String.class));
            storeFront.setIsDirectPurchase(jObj.getByPath("Offer.IsDirectPurchase", Boolean.class));
            storeFront.setDate(parseResponseDate(jObj.getByPath("Offer.StartDate", String.class), remainDurationInSeconds));
            storeFront.setCurrencyId(GAME_CURRENCY_VP_ID);
            storeFront.setCost(jObj.getByPath("Offer.Cost", JSONObject.class).getInt(GAME_CURRENCY_VP_ID));
            storeFront.setItemTypeId(jObj.getByPath("Offer.Rewards[0].ItemTypeID", String.class));
            storeFront.setItemId(jObj.getByPath("Offer.Rewards[0].ItemID", String.class));
            storeFront.setQuantity(jObj.getByPath("Offer.Rewards[0].Quantity", Integer.class));
            storeFront.setDiscountPercent(jObj.getInt("DiscountPercent"));
            storeFront.setDiscountCost(jObj.getJSONObject("DiscountCosts").getInt(GAME_CURRENCY_VP_ID));
            storeFront.setIsBonus(true);
            list.add(storeFront);
        });
        return list;
    }

    /**
     * json返回的日期时间为 UTC 格式，且存在时差，需要进行如下处理转换成最终日期：<br/>
     * （注：每日商店每天早上8点刷新）                                         <br/>
     *      0. 原格式：                    2023-02-21T10:03:37.931986544Z    <br/>
     *      1. 删除毫秒部分：              2023-02-21T10:03:37Z               <br/>
     *      2. 转换成标准格式、UTC+8时区：  2023-02-21 18:03:37                <br/>
     *      3. 加上剩余的秒数：50182       2023-02-22 07:59:59                <br/>
     *      4. 截取日期部分：              2023-02-22
     */
    private static String parseResponseDate(String dateTime, Long remainDurationInSeconds) {
        dateTime = dateTime.substring(0, dateTime.lastIndexOf(".")) + "Z";
        long time = DateUtil.parseUTC(dateTime).getTime() + remainDurationInSeconds*1000L;
        return DateUtil.date(time).toDateStr();
    }

    public static void main(String[] args) {
        System.out.println(parseResponseDate("2023-02-21T10:03:37.931146533Z", 50182L));
    }

}
