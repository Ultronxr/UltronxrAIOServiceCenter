package cn.ultronxr.valorant.bean.mybatis.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import lombok.Data;

/**
 * @author Ultronxr
 * @date 2023/02/22 10:13
 * @description
 */
@Data
@TableName("valorant_store_front")
public class StoreFront {

    @MppMultiId
    private String userId;

    @MppMultiId
    private String offerId;

    @MppMultiId
    private String date;

    private String endDate;

    private Boolean isDirectPurchase;

    private String currencyId;

    private Integer cost;

    private String itemTypeId;

    private String itemId;

    private Integer quantity;

    private Integer discountPercent;

    private Integer discountCost;

    private Boolean isBonus;

}
