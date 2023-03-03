package cn.ultronxr.valorant.api;

/**
 * @author Ultronxr
 * @date 2023/02/21 17:42
 * @description 来自 <a href="https://dash.valorant-api.com/">valorant-api.com</a> 的第三方API 枚举<br/>
 *              这些API汇总了游戏本身的内容，不包含有关玩家账户个人信息
 */
public enum ValorantDotComAPIEnum {

    Weapon            ("zh-TW", "/weapons")
    ,WeaponSkin       ("zh-TW", "/weapons/skins")
    ,RiotClientVersion("zh-TW", "/version")
    ;


    private static final String BASE_DOMAIN = "https://valorant-api.com/v1{URI}?language={language}";

    private String url;

    ValorantDotComAPIEnum(String language, String URI) {
        this.url = BASE_DOMAIN
                .replace("{language}", language)
                .replace("{URI}", URI);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
