# UAIOSC-valorant 瓦洛兰特模块

包括拳头游戏《瓦洛兰特》的各项数据统计功能。

[Notion 文档](https://ultronxr2ws.notion.site/UAIOSC-valorant-GitHub-Valorant-API-0ac20cd4c5b744148a74c6cd0f3380dc)

## MySQL

```mysql
-- ----------------------------
-- UAIOSC-valorant 模块
-- ----------------------------

CREATE TABLE valorant_riot_account (
    `user_id`               VARCHAR(100)     NOT NULL         COMMENT '账户ID（从拳头RSO接口中获取）',
    `username`              VARCHAR(100)     DEFAULT NULL     COMMENT '账户登录名',
    `password`              VARCHAR(500)     DEFAULT NULL     COMMENT '默认皮肤ID',
    `social_name`           VARCHAR(100)     DEFAULT NULL     COMMENT '社交用户名（AA#BB中的AA部分）',
    `social_tag`            VARCHAR(100)     DEFAULT NULL     COMMENT '社交用户tag（AA#BB中的BB部分）',
    `access_token`          VARCHAR(3000)    DEFAULT NULL     COMMENT 'API用户验证token（从拳头RSO接口中获取）',
    `entitlements_token`    VARCHAR(3000)    DEFAULT NULL     COMMENT 'API用户验证token（从拳头RSO接口中获取）',
    `multi_factor`          VARCHAR(3000)    DEFAULT NULL     COMMENT '两步验证信息',
    `is_verified`           TINYINT(1)       DEFAULT NULL     COMMENT '该账户信息是否通过验证：1-true; 0-false',
    PRIMARY KEY(`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT 'valorant 模块 - 拳头账户信息';

CREATE TABLE valorant_weapon (
    `uuid`                 VARCHAR(100)    NOT NULL         COMMENT '对象ID，主键',
    `asset_path`           VARCHAR(300)    DEFAULT NULL     COMMENT '游戏素材路径',
    `category`             VARCHAR(100)    DEFAULT NULL     COMMENT '分类',
    `default_skin_uuid`    VARCHAR(100)    DEFAULT NULL     COMMENT '默认皮肤ID',
    `display_name`         VARCHAR(100)    DEFAULT NULL     COMMENT '显示名称',
    `display_icon`         VARCHAR(300)    DEFAULT NULL     COMMENT '显示图标',
    `kill_stream_icon`     VARCHAR(300)    DEFAULT NULL     COMMENT '击杀信息中显示的图标',
    PRIMARY KEY(`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT 'valorant 模块 - 武器信息';

CREATE TABLE valorant_weapon_skin (
    `uuid`                  VARCHAR(100)    NOT NULL         COMMENT '对象ID，主键',
    `type`                  VARCHAR(10)     NOT NULL         COMMENT '皮肤类别（皮肤/升级/炫彩）：skin/level/chroma',
    `parent_weapon_uuid`    VARCHAR(100)    DEFAULT NULL     COMMENT '父武器ID（皮肤/升级/炫彩指向其父武器ID）',
    `parent_skin_uuid`      VARCHAR(100)    DEFAULT NULL     COMMENT '父皮肤ID（升级/炫彩指向其父皮肤ID）',
    `asset_path`            VARCHAR(300)    DEFAULT NULL     COMMENT '游戏素材路径',
    `content_tier_uuid`     VARCHAR(100)    DEFAULT NULL     COMMENT '系列ID',
    `theme_uuid`            VARCHAR(100)    DEFAULT NULL     COMMENT '主题ID',
    `display_name`          VARCHAR(100)    DEFAULT NULL     COMMENT '显示名称',
    `swatch`                VARCHAR(300)    DEFAULT NULL     COMMENT '样品缩略图',
    `display_icon`          VARCHAR(300)    DEFAULT NULL     COMMENT '显示图标',
    `full_render`           VARCHAR(300)    DEFAULT NULL     COMMENT '完整图片',
    `wallpaper`             VARCHAR(300)    DEFAULT NULL     COMMENT '壁纸',
    `streamed_video`        VARCHAR(300)    DEFAULT NULL     COMMENT '演示视频',
    PRIMARY KEY(`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT 'valorant 模块 - 武器皮肤（包括皮肤、升级、炫彩）';

CREATE TABLE valorant_store_front (
    `user_id`               VARCHAR(100)    NOT NULL         COMMENT '账户ID',
    `offer_id`              VARCHAR(100)    NOT NULL         COMMENT '供应ID（一般与 item_id 相同）',
    `date`                  VARCHAR(20)     NOT NULL         COMMENT '供应日期',
    `end_date`              VARCHAR(20)     DEFAULT NULL     COMMENT '供应结束日期',
    `is_direct_purchase`    TINYINT(1)      DEFAULT NULL     COMMENT '是否直售：1 - true / 0 - false',
    `currency_id`           VARCHAR(100)    DEFAULT NULL     COMMENT '游戏货币ID（VP:85ad13f7-3d1b-5128-9eb2-7cd8ee0b5741）',
    `cost`                  INT             DEFAULT NULL     COMMENT '原价',
    `item_type_id`          VARCHAR(100)    DEFAULT NULL     COMMENT '出售货物类别ID',
    `item_id`               VARCHAR(100)    DEFAULT NULL     COMMENT '出售货物ID',
    `quantity`              INT             DEFAULT NULL     COMMENT '出售数量',
    `discount_percent`      INT             DEFAULT NULL     COMMENT '打折百分比',
    `discount_cost`         INT             DEFAULT NULL     COMMENT '打折后的价格',
    `is_bonus`              TINYINT(1)      DEFAULT NULL     COMMENT '是否是Bonus商店（夜市）货物：1 - true / 0 - false',
    PRIMARY KEY(`user_id`, `offer_id`, `date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT 'valorant 模块 - 每日商店出售物品';
```
