package cn.ultronxr.valorant.api;

/**
 * @author Ultronxr
 * @date 2023/02/21 16:32
 * @description 游戏内 API 枚举（GitHub上的大佬从客户端里扒出来的）<br/>
 *              <a href="https://github.com/HeyM1ke/ValorantClientAPI">API来源（部分API未更新已失效）</a>、
 *              <a href="https://github.com/staciax/Valorant-DiscordBot/blob/139aab8bf77fe4a1bdd186e6df4c5001fa5d203e/utils/valorant/endpoint.py">API来源（有效）</a>
 */
public enum InGameAPIEnum {

    // 获取合约、任务列表及进度
    Contracts         ("pd", "ap", "/contracts/v1/contracts/{userId}")
    // 获取游戏内的物品信息及其ID
    ,Content          ("shared", "ap", "/content-service/v3/content")
    // 获取账户等级、经验
    ,AccountXP        ("pd", "ap", "/account-xp/v1/players/{userId}")
    // 获取玩家的汇总信息
    ,MMR              ("pd", "ap", "/mmr/v1/players/{userId}")
    // 获取玩家当前的装备信息
    ,PlayerLoadout    ("pd", "ap", "/personalization/v2/players/{userId}/playerloadout")
    // 获取商城中所有的物品
    ,StoreOffers      ("pd", "ap", "/store/v1/offers")
    // 获取商城中当前出售的物品
    ,StoreFront       ("pd", "ap", "/store/v2/storefront/{userId}")
    // 获取玩家的VP点数、RP点数
    // 85ad13f7-3d1b-5128-9eb2-7cd8ee0b5741: VP
    // e59aa87c-4cbf-517a-5983-6e81511be9b7: RP
    ,StoreWallet      ("pd", "ap", "/store/v1/wallet/{userId}")
    // 获取商店订单信息（orderID可以在创建订单时获取）
    ,StoreOrder       ("pd", "ap", "/store/v1/order/{orderId}")
    // 获取玩家已拥有的物品
    // 01bb38e1-da47-4e6a-9b3d-945fe4655707: Agents
    // f85cb6f7-33e5-4dc8-b609-ec7212301948: Contracts
    // d5f120f8-ff8c-4aac-92ea-f2b5acbe9475: Sprays
    // dd3bf334-87f3-40bd-b043-682a57a8dc3a: Gun Buddies
    // 3f296c07-64c3-494c-923b-fe692a4fa1bd: Player Cards
    // e7c63390-eda7-46e0-bb7a-a6abdacd2433: Skins
    // 3ad1b2b2-acdb-4524-852f-954a76ddae0a: Skins chroma
    // de7caa6b-adf7-4588-bbd1-143831e786c6: Player titles
    ,StoreEntitlements("pd", "ap", "/store/v1/entitlements/{userId}/{itemType}")
    ;


    private static final String BASE_DOMAIN = "https://{endPoint}.{server}.a.pvp.net";

    private String url;

    InGameAPIEnum(String endPoint, String server, String URI) {
        this.url = BASE_DOMAIN
                .replace("{endPoint}", endPoint)
                .replace("{server}", server)
                + URI;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
