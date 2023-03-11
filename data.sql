-- ----------------------------
-- UAIOSC-system 系统管理模块
-- ----------------------------

INSERT INTO system_user VALUES (1, '系统管理员', 'admin', 'admin', '', null, 'SQL', sysdate(), null, null);

INSERT INTO system_role VALUES (1, '系统管理员', 'admin', null, 'SQL', sysdate(), null, null);

INSERT INTO system_permission VALUES (1, null, '系统管理', 'system:*:*', '系统管理目录', 'DIRECTORY', 1, '#', null, 'SQL', sysdate(), null, null);
-- INSERT INTO system_permission VALUES (2, null, '游戏统计', 'game-register:*:*', '游戏统计目录', 'DIRECTORY', 2, '#', null, 'SQL', sysdate(), null, null);
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

INSERT INTO system_user_role VALUES (1, 1);

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

