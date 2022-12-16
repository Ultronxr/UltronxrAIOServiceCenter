-- ----------------------------
-- 创建数据库
-- ----------------------------

CREATE DATABASE `ultronxr_aio_service_center` DEFAULT CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_general_ci';


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

INSERT INTO system_user VALUES (1, '系统管理员', 'admin', 'admin', '', null, 'SQL', sysdate(), null, null);


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

INSERT INTO system_role VALUES (1, '系统管理员', 'admin', null, 'SQL', sysdate(), null, null);


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

INSERT INTO system_permission VALUES (1, null, '系统管理', 'system:*:*', '系统管理目录', 'DIRECTORY', 1, '#', null, 'SQL', sysdate(), null, null);
# INSERT INTO system_permission VALUES (2, null, '游戏统计', 'game-register:*:*', '游戏统计目录', 'DIRECTORY', 2, '#', null, 'SQL', sysdate(), null, null);

INSERT INTO system_permission VALUES (101, 1, '用户管理', 'system:user:*', '用户管理菜单', 'MENU', 1, '/system/user', null, 'SQL', sysdate(), null, null);
INSERT INTO system_permission VALUES (102, 1, '角色管理', 'system:role:*', '角色管理菜单', 'MENU', 2, '/system/role', null, 'SQL', sysdate(), null, null);
INSERT INTO system_permission VALUES (103, 1, '角色管理', 'system:permission:*', '权限管理菜单', 'MENU', 3, '/system/permission', null, 'SQL', sysdate(), null, null);

INSERT INTO system_permission VALUES (1001, 101, '查询用户', 'system:user:query', '查询用户按钮', 'BUTTON', 1, '/system/user/query', null, 'SQL', sysdate(), null, null);
INSERT INTO system_permission VALUES (1002, 101, '新增用户', 'system:user:create', '新增用户按钮', 'BUTTON', 2, '/system/user/create', null, 'SQL', sysdate(), null, null);
INSERT INTO system_permission VALUES (1003, 101, '修改用户', 'system:user:update', '修改用户按钮', 'BUTTON', 3, '/system/user/update', null, 'SQL', sysdate(), null, null);
INSERT INTO system_permission VALUES (1004, 101, '删除用户', 'system:user:delete', '删除用户按钮', 'BUTTON', 4, '/system/user/delete', null, 'SQL', sysdate(), null, null);
INSERT INTO system_permission VALUES (1011, 102, '查询角色', 'system:role:query', '查询角色按钮', 'BUTTON', 1, '/system/role/query', null, 'SQL', sysdate(), null, null);
INSERT INTO system_permission VALUES (1012, 102, '新增角色', 'system:role:create', '新增角色按钮', 'BUTTON', 2, '/system/role/create', null, 'SQL', sysdate(), null, null);
INSERT INTO system_permission VALUES (1013, 102, '修改角色', 'system:role:update', '修改角色按钮', 'BUTTON', 3, '/system/role/update', null, 'SQL', sysdate(), null, null);
INSERT INTO system_permission VALUES (1014, 102, '删除角色', 'system:role:delete', '删除角色按钮', 'BUTTON', 4, '/system/role/delete', null, 'SQL', sysdate(), null, null);
INSERT INTO system_permission VALUES (1021, 103, '查询权限', 'system:permission:query', '查询权限按钮', 'BUTTON', 1, '/system/permission/query', null, 'SQL', sysdate(), null, null);
INSERT INTO system_permission VALUES (1022, 103, '新增权限', 'system:permission:create', '新增权限按钮', 'BUTTON', 2, '/system/permission/create', null, 'SQL', sysdate(), null, null);
INSERT INTO system_permission VALUES (1023, 103, '修改权限', 'system:permission:update', '修改权限按钮', 'BUTTON', 3, '/system/permission/update', null, 'SQL', sysdate(), null, null);
INSERT INTO system_permission VALUES (1024, 103, '删除权限', 'system:permission:delete', '删除权限按钮', 'BUTTON', 4, '/system/permission/delete', null, 'SQL', sysdate(), null, null);


CREATE TABLE system_user_role (
    `user_id`    BIGINT    NOT NULL    COMMENT '用户ID',
    `role_id`    BIGINT    NOT NULL    COMMENT '角色ID',
    PRIMARY KEY(`user_id`, `role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT 'system 模块 - 用户/角色 对应表';

INSERT INTO system_user_role VALUES (1, 1);


CREATE TABLE system_role_permission (
    `role_id`          BIGINT    NOT NULL    COMMENT '角色ID',
    `permission_id`    BIGINT    NOT NULL    COMMENT '权限ID',
    PRIMARY KEY(`role_id`, `permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT 'system 模块 - 角色/权限 对应表';

INSERT INTO system_role_permission VALUES (1, 1);
INSERT INTO system_role_permission VALUES (1, 101);
INSERT INTO system_role_permission VALUES (1, 102);
INSERT INTO system_role_permission VALUES (1, 103);
INSERT INTO system_role_permission VALUES (1, 1001);
INSERT INTO system_role_permission VALUES (1, 1002);
INSERT INTO system_role_permission VALUES (1, 1003);
INSERT INTO system_role_permission VALUES (1, 1004);
INSERT INTO system_role_permission VALUES (1, 1011);
INSERT INTO system_role_permission VALUES (1, 1012);
INSERT INTO system_role_permission VALUES (1, 1013);
INSERT INTO system_role_permission VALUES (1, 1014);
INSERT INTO system_role_permission VALUES (1, 1021);
INSERT INTO system_role_permission VALUES (1, 1022);
INSERT INTO system_role_permission VALUES (1, 1023);
INSERT INTO system_role_permission VALUES (1, 1024);


-- ----------------------------
-- UAIOSC-game-register 游戏统计模块
-- ----------------------------

CREATE TABLE game_register_account (
    `id`             INT             NOT NULL AUTO_INCREMENT    COMMENT '账户ID，主键自增',
    `nick`           VARCHAR(255)    DEFAULT NULL               COMMENT '账户名称',
    `username`       VARCHAR(255)    DEFAULT NULL               COMMENT '账户登录名称',
    `email`          VARCHAR(100)    DEFAULT NULL               COMMENT '关联邮箱',
    `phone`          VARCHAR(100)    DEFAULT NULL               COMMENT '关联手机号',
    `platform`       VARCHAR(50)     DEFAULT NULL               COMMENT '平台，PC/Android等',
    `shop`           VARCHAR(50)     DEFAULT NULL               COMMENT '游戏商城，Steam/Epic/Uplay/Origin等',
    `region`         VARCHAR(50)     DEFAULT NULL               COMMENT '账户所属地区',
    `note`           VARCHAR(255)    DEFAULT NULL               COMMENT '备注',
    `create_by`      VARCHAR(100)    DEFAULT NULL               COMMENT '创建者',
    `create_time`    DATETIME        DEFAULT NULL               COMMENT '创建时间',
    `update_by`      VARCHAR(100)    DEFAULT NULL               COMMENT '更新者',
    `update_time`    DATETIME        DEFAULT NULL               COMMENT '更新时间',
    PRIMARY KEY(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT 'game-register 模块 - 游戏账户信息表';

CREATE TABLE game_register_game (
    `id`                         BIGINT          NOT NULL AUTO_INCREMENT    COMMENT '游戏ID，主键自增',
    `parent_id`                  BIGINT          DEFAULT NULL               COMMENT '如果是单独的DLC，需要填写游戏本体的父ID',
    `version`                    VARCHAR(50)     DEFAULT NULL               COMMENT '游戏版本：本体/DLC/其他',
    `name`                       VARCHAR(255)    DEFAULT NULL               COMMENT '游戏名',
    `name_eng`                   VARCHAR(255)    DEFAULT NULL               COMMENT '游戏名-英文',
    `name_nick`                  VARCHAR(255)    DEFAULT NULL               COMMENT '游戏名-别名',
    `name_bak`                   VARCHAR(255)    DEFAULT NULL               COMMENT '游戏名-备注',
    `description`                VARCHAR(255)    DEFAULT NULL               COMMENT '描述',
    `tag`                        VARCHAR(255)    DEFAULT NULL               COMMENT '标签，使用英文逗号,分割',
    `url`                        VARCHAR(255)    DEFAULT NULL               COMMENT '游戏链接',
    `logo`                       VARCHAR(255)    DEFAULT NULL               COMMENT 'logo链接',
    `img`                        VARCHAR(255)    DEFAULT NULL               COMMENT '大图链接',
    `developer`                  VARCHAR(255)    DEFAULT NULL               COMMENT '开发商',
    `publisher`                  VARCHAR(255)    DEFAULT NULL               COMMENT '发行商',
    `lowest_price_currency`      VARCHAR(255)    DEFAULT NULL               COMMENT '史低价格货币种类，默认人民币',
    `lowest_price`               DECIMAL(6,4)    DEFAULT NULL               COMMENT '史低价格',
    `lowest_price_rmb`           DECIMAL(6,4)    DEFAULT NULL               COMMENT '换算成人民币的史低价格',
    `bought_account_id`          INT             DEFAULT NULL               COMMENT '购买此游戏的账户ID，关联字段[game_register_service_account]-[id]',
    `purchase_date`              DATE            DEFAULT NULL               COMMENT '购买此游戏的日期',
    `purchase_price_currency`    VARCHAR(255)    DEFAULT NULL               COMMENT '购买此游戏的货币种类，默认人民币',
    `purchase_price`             DECIMAL(6,4)    DEFAULT NULL               COMMENT '购买此游戏的价格',
    `purchase_price_rmb`         DECIMAL(6,4)    DEFAULT NULL               COMMENT '换算成人民币的购买此游戏的价格',
    `actual_shop`                VARCHAR(255)    DEFAULT NULL               COMMENT '实际游玩的商城（这个商城和账户的商城有所区分，例如steam上购买的游戏实际需要通过origin打开游戏）',
    `note`                       VARCHAR(255)    DEFAULT NULL               COMMENT '备注',
    `create_by`                  VARCHAR(100)    DEFAULT NULL               COMMENT '创建者',
    `create_time`                DATETIME        DEFAULT NULL               COMMENT '创建时间',
    `update_by`                  VARCHAR(100)    DEFAULT NULL               COMMENT '更新者',
    `update_time`                DATETIME        DEFAULT NULL               COMMENT '更新时间',
    PRIMARY KEY(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT 'game-register 模块 - 已购买的游戏信息表';


-- ----------------------------
-- UAIOSC- 模块
-- ----------------------------
