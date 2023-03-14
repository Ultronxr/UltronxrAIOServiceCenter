-- ----------------------------
-- UAIOSC-system 系统管理模块
-- ----------------------------

INSERT INTO system_user VALUES (1, '系统管理员', 'admin', 'admin123', '', null, 'SQL', sysdate(), null, null);
INSERT INTO system_user VALUES (2, '用户', 'user', 'user123', '', null, 'SQL', sysdate(), null, null);
INSERT INTO system_user VALUES (3, '游客', 'guest', 'guest123', '', null, 'SQL', sysdate(), null, null);
INSERT INTO system_user VALUES (4, '游戏注册统计用户', 'gameregister', 'gameregister123', '', null, 'SQL', sysdate(), null, null);
INSERT INTO system_user VALUES (5, '瓦罗兰特用户', 'valorant', 'valorant123', '', null, 'SQL', sysdate(), null, null);


INSERT INTO system_role VALUES (1, '系统管理', 'SYSTEM', null, 'SQL', sysdate(), null, null);
-- INSERT INTO system_role VALUES (2, '普通用户', 'USER', null, 'SQL', sysdate(), null, null);
INSERT INTO system_role VALUES (3, '游客', 'GUEST', null, 'SQL', sysdate(), null, null);
INSERT INTO system_role VALUES (4, '游戏注册统计', 'GAME_REGISTER', null, 'SQL', sysdate(), null, null);
INSERT INTO system_role VALUES (5, '瓦罗兰特', 'VALORANT', null, 'SQL', sysdate(), null, null);


INSERT INTO system_permission VALUES (1, null, '系统管理', 'system:*:*', '系统管理目录', 'DIRECTORY', 1, '#', null, 'SQL', sysdate(), null, null);
INSERT INTO system_permission VALUES (2, null, '游戏统计', 'game-register:*:*', '游戏注册统计目录', 'DIRECTORY', 2, '#', null, 'SQL', sysdate(), null, null);
INSERT INTO system_permission VALUES (3, null, '瓦罗兰特', 'valorant:*:*', '瓦罗兰特目录', 'DIRECTORY', 3, '#', null, 'SQL', sysdate(), null, null);

INSERT INTO system_permission VALUES (101, 1, '用户管理', 'system:user:*', '用户管理菜单', 'MENU', 1, '/system/user', null, 'SQL', sysdate(), null, null);
INSERT INTO system_permission VALUES (102, 1, '角色管理', 'system:role:*', '角色管理菜单', 'MENU', 2, '/system/role', null, 'SQL', sysdate(), null, null);
INSERT INTO system_permission VALUES (103, 1, '角色管理', 'system:permission:*', '权限管理菜单', 'MENU', 3, '/system/permission', null, 'SQL', sysdate(), null, null);
INSERT INTO system_permission VALUES (201, 2, '账户管理', 'game-register:account:*', '账户管理菜单', 'MENU', 1, '/game-register/account', null, 'SQL', sysdate(), null, null);
INSERT INTO system_permission VALUES (202, 2, '游戏管理', 'game-register:game:*', '游戏管理菜单', 'MENU', 2, '/game-register/game', null, 'SQL', sysdate(), null, null);
INSERT INTO system_permission VALUES (301, 3, '拳头账户管理', 'valorant:account:*', '账户管理菜单', 'MENU', 1, '/valorant/account', null, 'SQL', sysdate(), null, null);
INSERT INTO system_permission VALUES (302, 3, '每日商店', 'valorant:singleItemOffers:*', '每次商店菜单', 'MENU', 2, '/valorant/singleItemOffers', null, 'SQL', sysdate(), null, null);
INSERT INTO system_permission VALUES (303, 3, '夜市', 'valorant:bonusOffers:*', '每次商店菜单', 'MENU', 3, '/valorant/bonusOffers', null, 'SQL', sysdate(), null, null);

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
INSERT INTO system_permission VALUES (2011, 201, '查询账户', 'game-register:account:query', '查询账户按钮', 'BUTTON', 1, '/game-register/account/query', null, 'SQL', sysdate(), null, null);
INSERT INTO system_permission VALUES (2012, 201, '新增账户', 'game-register:account:create', '新增账户按钮', 'BUTTON', 2, '/game-register/account/create', null, 'SQL', sysdate(), null, null);
INSERT INTO system_permission VALUES (2013, 201, '修改账户', 'game-register:account:update', '修改账户按钮', 'BUTTON', 3, '/game-register/account/update', null, 'SQL', sysdate(), null, null);
INSERT INTO system_permission VALUES (2014, 201, '删除账户', 'game-register:account:delete', '删除账户按钮', 'BUTTON', 4, '/game-register/account/delete', null, 'SQL', sysdate(), null, null);
INSERT INTO system_permission VALUES (2021, 202, '查询游戏', 'game-register:game:query', '查询游戏按钮', 'BUTTON', 1, '/game-register/game/query', null, 'SQL', sysdate(), null, null);
INSERT INTO system_permission VALUES (2022, 202, '新增游戏', 'game-register:game:create', '新增游戏按钮', 'BUTTON', 2, '/game-register/game/create', null, 'SQL', sysdate(), null, null);
INSERT INTO system_permission VALUES (2023, 202, '修改游戏', 'game-register:game:update', '修改游戏按钮', 'BUTTON', 3, '/game-register/game/update', null, 'SQL', sysdate(), null, null);
INSERT INTO system_permission VALUES (2024, 202, '删除游戏', 'game-register:game:delete', '删除游戏按钮', 'BUTTON', 4, '/game-register/game/delete', null, 'SQL', sysdate(), null, null);
INSERT INTO system_permission VALUES (3011, 301, '查询账户', 'valorant:account:query', '查询账户按钮', 'BUTTON', 1, '/valorant/account/query', null, 'SQL', sysdate(), null, null);
INSERT INTO system_permission VALUES (3012, 301, '新增账户', 'valorant:account:create', '新增账户按钮', 'BUTTON', 2, '/valorant/account/create', null, 'SQL', sysdate(), null, null);
INSERT INTO system_permission VALUES (3013, 301, '删除账户', 'valorant:account:delete', '删除账户按钮', 'BUTTON', 3, '/valorant/account/delete', null, 'SQL', sysdate(), null, null);
INSERT INTO system_permission VALUES (3021, 302, '查询每日商店', 'valorant:singleItemOffers:query', '查询每日商店按钮', 'BUTTON', 1, '/valorant/singleItemOffers/query', null, 'SQL', sysdate(), null, null);
INSERT INTO system_permission VALUES (3031, 303, '查询夜市', 'valorant:bonusOffers:query', '查询夜市按钮', 'BUTTON', 1, '/valorant/bonusOffers/query', null, 'SQL', sysdate(), null, null);


INSERT INTO system_user_role VALUES (1, 1);
INSERT INTO system_user_role VALUES (1, 3);
INSERT INTO system_user_role VALUES (1, 4);
INSERT INTO system_user_role VALUES (1, 5);

INSERT INTO system_user_role VALUES (2, 3);
INSERT INTO system_user_role VALUES (2, 4);
INSERT INTO system_user_role VALUES (2, 5);

INSERT INTO system_user_role VALUES (3, 3);

INSERT INTO system_user_role VALUES (4, 4);

INSERT INTO system_user_role VALUES (5, 5);


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

INSERT INTO system_role_permission VALUES (4, 2);
INSERT INTO system_role_permission VALUES (4, 201);
INSERT INTO system_role_permission VALUES (4, 202);
INSERT INTO system_role_permission VALUES (4, 2011);
INSERT INTO system_role_permission VALUES (4, 2012);
INSERT INTO system_role_permission VALUES (4, 2013);
INSERT INTO system_role_permission VALUES (4, 2014);
INSERT INTO system_role_permission VALUES (4, 2021);
INSERT INTO system_role_permission VALUES (4, 2022);
INSERT INTO system_role_permission VALUES (4, 2023);
INSERT INTO system_role_permission VALUES (4, 2024);

INSERT INTO system_role_permission VALUES (5, 3);
INSERT INTO system_role_permission VALUES (5, 301);
INSERT INTO system_role_permission VALUES (5, 302);
INSERT INTO system_role_permission VALUES (5, 303);
INSERT INTO system_role_permission VALUES (5, 3011);
INSERT INTO system_role_permission VALUES (5, 3012);
INSERT INTO system_role_permission VALUES (5, 3013);
INSERT INTO system_role_permission VALUES (5, 3021);
INSERT INTO system_role_permission VALUES (5, 3031);


-- ----------------------------
-- UAIOSC-game-register 游戏统计模块
-- ----------------------------

INSERT INTO game_register_platform VALUES ('PC', 'PC端', 1);
INSERT INTO game_register_platform VALUES ('Android', 'Android安卓端', 2);
INSERT INTO game_register_platform VALUES ('IOS', 'IOS苹果端', 3);

INSERT INTO game_register_shop VALUES ('Steam', 'Steam', 1);
INSERT INTO game_register_shop VALUES ('Epic', 'Epic', 2);
INSERT INTO game_register_shop VALUES ('EA/Origin', 'EA/Origin', 3);
INSERT INTO game_register_shop VALUES ('Uplay', 'Uplay 育碧', 4);
INSERT INTO game_register_shop VALUES ('Riot', 'Riot 拳头', 5);
INSERT INTO game_register_shop VALUES ('RStar', 'RStar R星', 6);
INSERT INTO game_register_shop VALUES ('BattleNet', 'BattleNet 暴雪战网', 7);
INSERT INTO game_register_shop VALUES ('Microsoft', 'Microsoft 微软', 8);

