-- ----------------------------
-- 创建数据库
-- ----------------------------

CREATE DATABASE `ultronxr_aio_service_center` DEFAULT CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_general_ci';

CREATE DATABASE `ultronxr_aio_service_center_dev` DEFAULT CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_general_ci';

-- ----------------------------
-- UAIOSC-system 系统管理模块
-- ----------------------------

CREATE TABLE system_user (
    `id`             BIGINT          NOT NULL AUTO_INCREMENT    COMMENT '用户ID，主键自增',
    `nick`           VARCHAR(255)    DEFAULT NULL               COMMENT '显示名称',
    `username`       VARCHAR(255)    NOT NULL                   COMMENT '登录用户名',
    `password`       VARCHAR(255)    DEFAULT NULL               COMMENT '登录密码',
    `avatar`         VARCHAR(255)    DEFAULT NULL               COMMENT '头像图片地址',
    `note`           VARCHAR(255)    DEFAULT NULL               COMMENT '备注',
    `create_by`      VARCHAR(100)    DEFAULT NULL               COMMENT '创建者',
    `create_time`    DATETIME        DEFAULT NULL               COMMENT '创建时间',
    `update_by`      VARCHAR(100)    DEFAULT NULL               COMMENT '更新者',
    `update_time`    DATETIME        DEFAULT NULL               COMMENT '更新时间',
    PRIMARY KEY(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT 'system 模块 - 用户信息表';

CREATE TABLE system_role (
    `id`             BIGINT          NOT NULL AUTO_INCREMENT    COMMENT '角色ID，主键自增',
    `name`           VARCHAR(100)    NOT NULL                   COMMENT '角色名称（例：系统管理员）',
    `code`           VARCHAR(100)    NOT NULL                   COMMENT '角色代码（例：system_admin）',
    `note`           VARCHAR(255)    DEFAULT NULL               COMMENT '备注',
    `create_by`      VARCHAR(100)    DEFAULT NULL               COMMENT '创建者',
    `create_time`    DATETIME        DEFAULT NULL               COMMENT '创建时间',
    `update_by`      VARCHAR(100)    DEFAULT NULL               COMMENT '更新者',
    `update_time`    DATETIME        DEFAULT NULL               COMMENT '更新时间',
    PRIMARY KEY(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT 'system 模块 - 角色信息表';

CREATE TABLE system_permission (
    `id`                 BIGINT          NOT NULL AUTO_INCREMENT    COMMENT '权限ID，主键',
    `parent_id`          VARCHAR(50)     DEFAULT NULL               COMMENT '父权限ID',
    `permission_name`    VARCHAR(100)    NOT NULL                   COMMENT '权限名称（例：用户删除权限）',
    `permission_code`    VARCHAR(100)    NOT NULL                   COMMENT '权限代码（例：system:user:delete）',
    `menu`               VARCHAR(255)    DEFAULT NULL               COMMENT '此权限对应的功能菜单',
    `menu_type`          VARCHAR(50)     DEFAULT NULL               COMMENT '菜单类型：目录 DIRECTORY / 菜单 MENU / 按钮 BUTTON',
    `menu_sort`          INT             DEFAULT NULL               COMMENT '菜单排序',
    `request`            VARCHAR(255)    DEFAULT NULL               COMMENT '请求接口',
    `note`               VARCHAR(255)    DEFAULT NULL               COMMENT '备注',
    `create_by`          VARCHAR(100)    DEFAULT NULL               COMMENT '创建者',
    `create_time`        DATETIME        DEFAULT NULL               COMMENT '创建时间',
    `update_by`          VARCHAR(100)    DEFAULT NULL               COMMENT '更新者',
    `update_time`        DATETIME        DEFAULT NULL               COMMENT '更新时间',
    PRIMARY KEY(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT 'system 模块 - 权限信息表';

CREATE TABLE system_user_role (
    `user_id`    BIGINT    NOT NULL    COMMENT '用户ID',
    `role_id`    BIGINT    NOT NULL    COMMENT '角色ID',
    PRIMARY KEY(`user_id`, `role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT 'system 模块 - 用户/角色 对应表';

CREATE TABLE system_role_permission (
    `role_id`          BIGINT    NOT NULL    COMMENT '角色ID',
    `permission_id`    BIGINT    NOT NULL    COMMENT '权限ID',
    PRIMARY KEY(`role_id`, `permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT 'system 模块 - 角色/权限 对应表';


-- ----------------------------
-- UAIOSC-game-register 游戏统计模块
-- ----------------------------

CREATE TABLE game_register_platform (
    `id`      VARCHAR(50)    NOT NULL    COMMENT '游戏平台ID',
    `name`    VARCHAR(50)    NOT NULL    COMMENT '游戏平台名称',
    `sort`    INT            NOT NULL    COMMENT '排序',
    PRIMARY KEY(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT 'game-register 模块 - 游戏平台表';

CREATE TABLE game_register_shop (
    `id`      VARCHAR(50)    NOT NULL    COMMENT '游戏商城ID',
    `name`    VARCHAR(50)    NOT NULL    COMMENT '游戏商城名称',
    `sort`    INT            NOT NULL    COMMENT '排序',
    PRIMARY KEY(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT 'game-register 模块 - 游戏商城表';

CREATE TABLE game_register_account (
    `id`             INT             NOT NULL AUTO_INCREMENT    COMMENT '账户ID，主键自增',
    `nick`           VARCHAR(255)    DEFAULT NULL               COMMENT '账户名称',
    `username`       VARCHAR(255)    DEFAULT NULL               COMMENT '账户登录名称',
    `email`          VARCHAR(100)    DEFAULT NULL               COMMENT '关联邮箱',
    `phone`          VARCHAR(100)    DEFAULT NULL               COMMENT '关联手机号',
    `platform`       VARCHAR(50)     DEFAULT NULL               COMMENT '平台，关联字段[game_register_platform]-[id]',
    `shop`           VARCHAR(50)     DEFAULT NULL               COMMENT '游戏商城，关联字段[game_register_shop]-[id]',
    `region`         VARCHAR(50)     DEFAULT NULL               COMMENT '账户所属地区',
    `note`           VARCHAR(255)    DEFAULT NULL               COMMENT '备注',
    `create_by`      VARCHAR(100)    DEFAULT NULL               COMMENT '创建者',
    `create_time`    DATETIME        DEFAULT NULL               COMMENT '创建时间',
    `update_by`      VARCHAR(100)    DEFAULT NULL               COMMENT '更新者',
    `update_time`    DATETIME        DEFAULT NULL               COMMENT '更新时间',
    PRIMARY KEY(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT 'game-register 模块 - 游戏账户信息表';

CREATE TABLE game_register_game (
    `game_id`                    BIGINT          NOT NULL AUTO_INCREMENT    COMMENT '游戏ID，主键',
    `parent_id`                  BIGINT          DEFAULT NULL               COMMENT '如果是单独的DLC，需要填写游戏本体的父ID',
    `account_id`                 INT             NOT NULL                   COMMENT '拥有此游戏的账户ID，主键，关联字段[game_register_account]-[id]',
    `app_id`                     INT             DEFAULT NULL               COMMENT '游戏商城中，该游戏商品的ID（以Steam AppID为例）',
    `shop`                       VARCHAR(50)     DEFAULT NULL               COMMENT '上架的游戏商城',
    `actual_play_shop`           VARCHAR(50)     DEFAULT NULL               COMMENT '实际游玩的商城（这个商城和账户的商城有所区分，例如Steam上购买的游戏实际需要通过Origin打开游戏）',
    `version`                    VARCHAR(50)     DEFAULT NULL               COMMENT '游戏版本：本体/DLC/捆绑包/其他',
    `name`                       VARCHAR(255)    DEFAULT NULL               COMMENT '游戏名',
    `name_eng`                   VARCHAR(255)    DEFAULT NULL               COMMENT '游戏名-英文',
    `name_nick`                  VARCHAR(255)    DEFAULT NULL               COMMENT '游戏名-别名',
    `name_bak`                   VARCHAR(255)    DEFAULT NULL               COMMENT '游戏名-备注',
    `description`                VARCHAR(800)    DEFAULT NULL               COMMENT '描述',
    `tag`                        VARCHAR(255)    DEFAULT NULL               COMMENT '标签，使用英文逗号,分割',
    `url`                        VARCHAR(255)    DEFAULT NULL               COMMENT '游戏链接',
    `logo`                       VARCHAR(255)    DEFAULT NULL               COMMENT 'logo链接',
    `img`                        VARCHAR(255)    DEFAULT NULL               COMMENT '大图链接',
    `developer`                  VARCHAR(100)    DEFAULT NULL               COMMENT '开发商',
    `publisher`                  VARCHAR(100)    DEFAULT NULL               COMMENT '发行商',
    `lowest_price_currency`      VARCHAR(50)     DEFAULT NULL               COMMENT '史低价格货币种类，默认人民币',
    `lowest_price`               DECIMAL(6,4)    DEFAULT NULL               COMMENT '史低价格',
    `lowest_price_rmb`           DECIMAL(6,4)    DEFAULT NULL               COMMENT '换算成人民币的史低价格',
    `purchase_date`              DATE            DEFAULT NULL               COMMENT '购买此游戏的日期',
    `purchase_price_currency`    VARCHAR(50)     DEFAULT NULL               COMMENT '购买此游戏的货币种类，默认人民币',
    `purchase_price`             DECIMAL(6,4)    DEFAULT NULL               COMMENT '购买此游戏的价格',
    `purchase_price_rmb`         DECIMAL(6,4)    DEFAULT NULL               COMMENT '换算成人民币的购买此游戏的价格',
    `note`                       VARCHAR(255)    DEFAULT NULL               COMMENT '备注',
    `create_by`                  VARCHAR(100)    DEFAULT NULL               COMMENT '创建者',
    `create_time`                DATETIME        DEFAULT NULL               COMMENT '创建时间',
    `update_by`                  VARCHAR(100)    DEFAULT NULL               COMMENT '更新者',
    `update_time`                DATETIME        DEFAULT NULL               COMMENT '更新时间',
    PRIMARY KEY(`game_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT 'game-register 模块 - 已购买的游戏信息表';


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

-- ----------------------------
-- UAIOSC-wechat 微信相关功能模块
-- ----------------------------

CREATE TABLE wechat_access_token (
    `app_id`          VARCHAR(100)    NOT NULL              COMMENT '微信公众号的唯一标识',
    `access_token`    VARCHAR(500)    NOT NULL              COMMENT '获取到的凭证',
    `expires_in`      INT             NOT NULL DEFAULT 0    COMMENT '凭证有效时间，单位：秒。小于等于7200',
    `create_time`     DATETIME        NOT NULL              COMMENT '创建时间',
    `update_time`     DATETIME        DEFAULT NULL          COMMENT '更新时间',
    PRIMARY KEY(`app_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT 'wechat 模块 - 微信 access token 表';
