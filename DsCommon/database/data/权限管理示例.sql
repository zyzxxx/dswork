
INSERT INTO ds_common_system (ID, NAME, ALIAS, PASSWORD, DOMAINURL, ROOTURL, MENUURL, STATUS, SEQ, MEMO) VALUES (0, '测试单机系统', NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL);
INSERT INTO ds_common_system (ID, NAME, ALIAS, PASSWORD, DOMAINURL, ROOTURL, MENUURL, STATUS, SEQ, MEMO) VALUES (1, '统一认证平台', 'DsCommon', '1', 'http://127.0.0.1', '/DsCommon', '/DsCommon/menu.jsp', 1, NULL, '');
INSERT INTO ds_common_system (ID, NAME, ALIAS, PASSWORD, DOMAINURL, ROOTURL, MENUURL, STATUS, SEQ, MEMO) VALUES (2, '门户', 'portal', '1', '', '/portal', '/portal/menu.jsp', 1, NULL, '');
INSERT INTO ds_common_system (ID, NAME, ALIAS, PASSWORD, DOMAINURL, ROOTURL, MENUURL, STATUS, SEQ, MEMO) VALUES (3, 'cas-client', 'sso-client', '1', 'http://127.0.0.1:888', '/sso-client', '/sso-client/menu.jsp', 1, NULL, '');


INSERT INTO ds_common_func (ID, PID, SYSTEMID, NAME, ALIAS, URI, IMG, STATUS, SEQ, MEMO, RESOURCES) VALUES (1, NULL, 1, '门户管理', '', '#', '', 1, 1, '', '');
INSERT INTO ds_common_func (ID, PID, SYSTEMID, NAME, ALIAS, URI, IMG, STATUS, SEQ, MEMO, RESOURCES) VALUES (2, NULL, 1, '业务管理', '', '#', '', 1, 2, '', '');
INSERT INTO ds_common_func (ID, PID, SYSTEMID, NAME, ALIAS, URI, IMG, STATUS, SEQ, MEMO, RESOURCES) VALUES (3, NULL, 1, '用户管理', '', '#', '', 1, 3, '', '');
INSERT INTO ds_common_func (ID, PID, SYSTEMID, NAME, ALIAS, URI, IMG, STATUS, SEQ, MEMO, RESOURCES) VALUES (11, 1, 1, '系统管理', '', '/common/system/getSystem.htm', '', 1, 0, '', '');
INSERT INTO ds_common_func (ID, PID, SYSTEMID, NAME, ALIAS, URI, IMG, STATUS, SEQ, MEMO, RESOURCES) VALUES (12, 1, 1, '组织管理', '', '/common/org/getOrgTree.htm', '', 1, 0, '', '');
INSERT INTO ds_common_func (ID, PID, SYSTEMID, NAME, ALIAS, URI, IMG, STATUS, SEQ, MEMO, RESOURCES) VALUES (13, 1, 1, '岗位授权管理', '', '/common/orgrole/getOrgTree.htm', '', 1, 0, '', '');
INSERT INTO ds_common_func (ID, PID, SYSTEMID, NAME, ALIAS, URI, IMG, STATUS, SEQ, MEMO, RESOURCES) VALUES (21, 2, 1, '字典管理', '', '/common/dict/getDict.htm', '', 1, 0, '', '');
INSERT INTO ds_common_func (ID, PID, SYSTEMID, NAME, ALIAS, URI, IMG, STATUS, SEQ, MEMO, RESOURCES) VALUES (22, 2, 1, '流程管理', '', '/common/flow/getFlowCategoryTree.htm', '', 1, 0, '', '');
INSERT INTO ds_common_func (ID, PID, SYSTEMID, NAME, ALIAS, URI, IMG, STATUS, SEQ, MEMO, RESOURCES) VALUES (31, 3, 1, '用户管理', '', '/common/user/getUser.htm', '', 1, 0, '', '');
INSERT INTO ds_common_func (ID, PID, SYSTEMID, NAME, ALIAS, URI, IMG, STATUS, SEQ, MEMO, RESOURCES) VALUES (32, 3, 1, '用户授权管理', '', '/common/userorg/getOrgTree.htm', '', 1, 0, '', '');
INSERT INTO ds_common_func (ID, PID, SYSTEMID, NAME, ALIAS, URI, IMG, STATUS, SEQ, MEMO, RESOURCES) VALUES (99, NULL, 3, 'ssoclient', 'ssoclient', '/', '', 1, 0, '', '');


INSERT INTO ds_common_role (ID, PID, SYSTEMID, NAME, SEQ, MEMO) VALUES (1, NULL, 1, '超级管理员', 0, '');
INSERT INTO ds_common_role (ID, PID, SYSTEMID, NAME, SEQ, MEMO) VALUES (2, NULL, 1, '门户管理', 0, '');
INSERT INTO ds_common_role (ID, PID, SYSTEMID, NAME, SEQ, MEMO) VALUES (3, NULL, 1, '业务管理', 0, '');
INSERT INTO ds_common_role (ID, PID, SYSTEMID, NAME, SEQ, MEMO) VALUES (4, NULL, 1, '用户管理', 0, '');
INSERT INTO ds_common_role (ID, PID, SYSTEMID, NAME, SEQ, MEMO) VALUES (11, NULL, 2, '测试菜单', 0, '');
INSERT INTO ds_common_role (ID, PID, SYSTEMID, NAME, SEQ, MEMO) VALUES (17, NULL, 0, '超级管理员', 0, '');
INSERT INTO ds_common_role (ID, PID, SYSTEMID, NAME, SEQ, MEMO) VALUES (21, NULL, 3, 'ssoclient', 0, '');


INSERT INTO ds_common_rolefunc (ID, SYSTEMID, ROLEID, FUNCID) VALUES (15, 1, 2, 1);
INSERT INTO ds_common_rolefunc (ID, SYSTEMID, ROLEID, FUNCID) VALUES (16, 1, 2, 11);
INSERT INTO ds_common_rolefunc (ID, SYSTEMID, ROLEID, FUNCID) VALUES (17, 1, 2, 12);
INSERT INTO ds_common_rolefunc (ID, SYSTEMID, ROLEID, FUNCID) VALUES (18, 1, 2, 13);
INSERT INTO ds_common_rolefunc (ID, SYSTEMID, ROLEID, FUNCID) VALUES (19, 1, 3, 2);
INSERT INTO ds_common_rolefunc (ID, SYSTEMID, ROLEID, FUNCID) VALUES (20, 1, 3, 21);
INSERT INTO ds_common_rolefunc (ID, SYSTEMID, ROLEID, FUNCID) VALUES (21, 1, 3, 22);
INSERT INTO ds_common_rolefunc (ID, SYSTEMID, ROLEID, FUNCID) VALUES (22, 1, 4, 3);
INSERT INTO ds_common_rolefunc (ID, SYSTEMID, ROLEID, FUNCID) VALUES (23, 1, 4, 31);
INSERT INTO ds_common_rolefunc (ID, SYSTEMID, ROLEID, FUNCID) VALUES (24, 1, 4, 32);
INSERT INTO ds_common_rolefunc (ID, SYSTEMID, ROLEID, FUNCID) VALUES (25, 1, 1, 1);
INSERT INTO ds_common_rolefunc (ID, SYSTEMID, ROLEID, FUNCID) VALUES (26, 1, 1, 11);
INSERT INTO ds_common_rolefunc (ID, SYSTEMID, ROLEID, FUNCID) VALUES (27, 1, 1, 12);
INSERT INTO ds_common_rolefunc (ID, SYSTEMID, ROLEID, FUNCID) VALUES (28, 1, 1, 13);
INSERT INTO ds_common_rolefunc (ID, SYSTEMID, ROLEID, FUNCID) VALUES (29, 1, 1, 2);
INSERT INTO ds_common_rolefunc (ID, SYSTEMID, ROLEID, FUNCID) VALUES (30, 1, 1, 21);
INSERT INTO ds_common_rolefunc (ID, SYSTEMID, ROLEID, FUNCID) VALUES (31, 1, 1, 22);
INSERT INTO ds_common_rolefunc (ID, SYSTEMID, ROLEID, FUNCID) VALUES (32, 1, 1, 3);
INSERT INTO ds_common_rolefunc (ID, SYSTEMID, ROLEID, FUNCID) VALUES (33, 1, 1, 31);
INSERT INTO ds_common_rolefunc (ID, SYSTEMID, ROLEID, FUNCID) VALUES (34, 1, 1, 32);
INSERT INTO ds_common_rolefunc (ID, SYSTEMID, ROLEID, FUNCID) VALUES (35, 3, 21, 99);


INSERT INTO ds_common_org (ID, PID, NAME, STATUS, SEQ, DUTYSCOPE, MEMO) VALUES (1, NULL, '系统管理组', 2, 14, '', '');
INSERT INTO ds_common_org (ID, PID, NAME, STATUS, SEQ, DUTYSCOPE, MEMO) VALUES (2, 1, '系统管理部门', 1, 0, '', '');
INSERT INTO ds_common_org (ID, PID, NAME, STATUS, SEQ, DUTYSCOPE, MEMO) VALUES (3, 1, '测试管理组', 2, 0, '', '');
INSERT INTO ds_common_org (ID, PID, NAME, STATUS, SEQ, DUTYSCOPE, MEMO) VALUES (4, 2, '系统管理岗位', 0, 0, '', '');
INSERT INTO ds_common_org (ID, PID, NAME, STATUS, SEQ, DUTYSCOPE, MEMO) VALUES (5, 3, '广州部门', 1, 0, '', '');
INSERT INTO ds_common_org (ID, PID, NAME, STATUS, SEQ, DUTYSCOPE, MEMO) VALUES (6, 5, '天河分部', 1, 0, '', '');
INSERT INTO ds_common_org (ID, PID, NAME, STATUS, SEQ, DUTYSCOPE, MEMO) VALUES (8, 5, '白云分部', 1, 0, '', '');
INSERT INTO ds_common_org (ID, PID, NAME, STATUS, SEQ, DUTYSCOPE, MEMO) VALUES (9, 6, '天河区局岗位', 0, 0, '', '');
INSERT INTO ds_common_org (ID, PID, NAME, STATUS, SEQ, DUTYSCOPE, MEMO) VALUES (10, 8, '白云区局岗位', 0, 0, '', '');
INSERT INTO ds_common_org (ID, PID, NAME, STATUS, SEQ, DUTYSCOPE, MEMO) VALUES (43, NULL, '广州省旅游局', 2, 2, '', '');
INSERT INTO ds_common_org (ID, PID, NAME, STATUS, SEQ, DUTYSCOPE, MEMO) VALUES (46, 43, '广州市旅游局', 2, 1, '', '');
INSERT INTO ds_common_org (ID, PID, NAME, STATUS, SEQ, DUTYSCOPE, MEMO) VALUES (47, 43, '办公室', 1, 0, '', '');
INSERT INTO ds_common_org (ID, PID, NAME, STATUS, SEQ, DUTYSCOPE, MEMO) VALUES (48, 43, '质量规范与管理处', 1, 0, '', '');
INSERT INTO ds_common_org (ID, PID, NAME, STATUS, SEQ, DUTYSCOPE, MEMO) VALUES (49, 43, '资源与市场开发处', 1, 0, '', '');
INSERT INTO ds_common_org (ID, PID, NAME, STATUS, SEQ, DUTYSCOPE, MEMO) VALUES (50, 43, '人事教育处', 1, 0, '', '');
INSERT INTO ds_common_org (ID, PID, NAME, STATUS, SEQ, DUTYSCOPE, MEMO) VALUES (51, 43, '广东省旅游发展研究中心', 1, 0, '', '');
INSERT INTO ds_common_org (ID, PID, NAME, STATUS, SEQ, DUTYSCOPE, MEMO) VALUES (52, 43, '省旅游质量监督管理所', 1, 0, '', '');
INSERT INTO ds_common_org (ID, PID, NAME, STATUS, SEQ, DUTYSCOPE, MEMO) VALUES (53, 43, '省旅游服务中心', 1, 0, '', '');
INSERT INTO ds_common_org (ID, PID, NAME, STATUS, SEQ, DUTYSCOPE, MEMO) VALUES (54, 43, '机关后勤保障中心', 1, 0, '', '');
INSERT INTO ds_common_org (ID, PID, NAME, STATUS, SEQ, DUTYSCOPE, MEMO) VALUES (55, 46, '办公室', 1, 0, '', '');
INSERT INTO ds_common_org (ID, PID, NAME, STATUS, SEQ, DUTYSCOPE, MEMO) VALUES (56, 46, '规划发展处', 1, 0, '', '');
INSERT INTO ds_common_org (ID, PID, NAME, STATUS, SEQ, DUTYSCOPE, MEMO) VALUES (57, 46, '法规与统计处', 1, 0, '', '');
INSERT INTO ds_common_org (ID, PID, NAME, STATUS, SEQ, DUTYSCOPE, MEMO) VALUES (58, 46, '市场推广处', 1, 0, '', '');
INSERT INTO ds_common_org (ID, PID, NAME, STATUS, SEQ, DUTYSCOPE, MEMO) VALUES (59, 46, '资源开发处', 1, 0, '', '');
INSERT INTO ds_common_org (ID, PID, NAME, STATUS, SEQ, DUTYSCOPE, MEMO) VALUES (60, 46, '旅游饭店管理处', 1, 0, '', '');
INSERT INTO ds_common_org (ID, PID, NAME, STATUS, SEQ, DUTYSCOPE, MEMO) VALUES (61, 46, '旅行社管理处', 1, 0, '', '');
INSERT INTO ds_common_org (ID, PID, NAME, STATUS, SEQ, DUTYSCOPE, MEMO) VALUES (62, 46, '行业培训指导处', 1, 0, '', '');
INSERT INTO ds_common_org (ID, PID, NAME, STATUS, SEQ, DUTYSCOPE, MEMO) VALUES (63, 46, '组织人事处 （与机关党委办公室合署）', 1, 0, '', '');
INSERT INTO ds_common_org (ID, PID, NAME, STATUS, SEQ, DUTYSCOPE, MEMO) VALUES (64, 46, '离退休干部工作处', 1, 0, '', '');
INSERT INTO ds_common_org (ID, PID, NAME, STATUS, SEQ, DUTYSCOPE, MEMO) VALUES (75, 46, '局长办公室', 1, 0, '', '');
INSERT INTO ds_common_org (ID, PID, NAME, STATUS, SEQ, DUTYSCOPE, MEMO) VALUES (76, 46, '执法部门', 1, 0, '', '');
INSERT INTO ds_common_org (ID, PID, NAME, STATUS, SEQ, DUTYSCOPE, MEMO) VALUES (77, 76, '科员', 0, 0, '', '');
INSERT INTO ds_common_org (ID, PID, NAME, STATUS, SEQ, DUTYSCOPE, MEMO) VALUES (78, 76, '科长', 0, 0, '', '');
INSERT INTO ds_common_org (ID, PID, NAME, STATUS, SEQ, DUTYSCOPE, MEMO) VALUES (79, 75, '局长', 0, 0, '', '');
INSERT INTO ds_common_org (ID, PID, NAME, STATUS, SEQ, DUTYSCOPE, MEMO) VALUES (80, 75, '副局长', 0, 0, '', '');
INSERT INTO ds_common_org (ID, PID, NAME, STATUS, SEQ, DUTYSCOPE, MEMO) VALUES (81, 57, '科长', 0, 0, '', '');
INSERT INTO ds_common_org (ID, PID, NAME, STATUS, SEQ, DUTYSCOPE, MEMO) VALUES (82, 57, '科员', 0, 0, '', '');
INSERT INTO ds_common_org (ID, PID, NAME, STATUS, SEQ, DUTYSCOPE, MEMO) VALUES (101, 46, '广州市旅游质量监督管理所', 2, 0, '', '81078213');
INSERT INTO ds_common_org (ID, PID, NAME, STATUS, SEQ, DUTYSCOPE, MEMO) VALUES (102, 46, '广州城市旅游问询服务中心', 2, 0, '（挂广州旅游紧急救援中心牌子）', '81078238');
INSERT INTO ds_common_org (ID, PID, NAME, STATUS, SEQ, DUTYSCOPE, MEMO) VALUES (103, 46, '广州市旅游局机关服务中心', 2, 0, '', '81074459');


INSERT INTO ds_common_orgrole (ID, ORGID, ROLEID) VALUES (21, 8, 1);
INSERT INTO ds_common_orgrole (ID, ORGID, ROLEID) VALUES (23, 8, 2);
INSERT INTO ds_common_orgrole (ID, ORGID, ROLEID) VALUES (27, 10, 11);
INSERT INTO ds_common_orgrole (ID, ORGID, ROLEID) VALUES (28, 10, 1);
INSERT INTO ds_common_orgrole (ID, ORGID, ROLEID) VALUES (31, 9, 2);
INSERT INTO ds_common_orgrole (ID, ORGID, ROLEID) VALUES (32, 9, 4);
INSERT INTO ds_common_orgrole (ID, ORGID, ROLEID) VALUES (33, 4, 1);
INSERT INTO ds_common_orgrole (ID, ORGID, ROLEID) VALUES (34, 4, 21);


INSERT INTO ds_common_user (ID, ACCOUNT, PASSWORD, NAME, IDCARD, STATUS, EMAIL, MOBILE, PHONE, WORKCARD, CAKEY, CREATETIME, ORGPID, ORGID) VALUES (-1, 'admin', '670B14728AD9902AECBA32E22FA4F6BD', '超级管理员', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, 1, 2);
INSERT INTO ds_common_user (ID, ACCOUNT, PASSWORD, NAME, IDCARD, STATUS, EMAIL, MOBILE, PHONE, WORKCARD, CAKEY, CREATETIME, ORGPID, ORGID) VALUES (1, 'user', '670B14728AD9902AECBA32E22FA4F6BD', '用户', '', 1, '', '', '', '', '', '2014-06-04 16:13:30', 3, 6);
INSERT INTO ds_common_user (ID, ACCOUNT, PASSWORD, NAME, IDCARD, STATUS, EMAIL, MOBILE, PHONE, WORKCARD, CAKEY, CREATETIME, ORGPID, ORGID) VALUES (2, 'useradmin', '670B14728AD9902AECBA32E22FA4F6BD', '用户', '', 1, '', '', '', '', '', '2014-06-04 16:14:06', 3, 8);
INSERT INTO ds_common_user (ID, ACCOUNT, PASSWORD, NAME, IDCARD, STATUS, EMAIL, MOBILE, PHONE, WORKCARD, CAKEY, CREATETIME, ORGPID, ORGID) VALUES (3, 'z1', '670B14728AD9902AECBA32E22FA4F6BD', '张一', '', 1, '', '', '', '', '', '2014-06-10 11:18:27', 3, 6);
INSERT INTO ds_common_user (ID, ACCOUNT, PASSWORD, NAME, IDCARD, STATUS, EMAIL, MOBILE, PHONE, WORKCARD, CAKEY, CREATETIME, ORGPID, ORGID) VALUES (4, 'z2', '670B14728AD9902AECBA32E22FA4F6BD', '张二', '', 1, '', '', '', '', '', '2014-06-10 11:18:39', 3, 6);
INSERT INTO ds_common_user (ID, ACCOUNT, PASSWORD, NAME, IDCARD, STATUS, EMAIL, MOBILE, PHONE, WORKCARD, CAKEY, CREATETIME, ORGPID, ORGID) VALUES (5, 'z3', '670B14728AD9902AECBA32E22FA4F6BD', '张三', '', 0, '', '', '', '', '', '2014-06-10 11:18:50', 3, 6);
INSERT INTO ds_common_user (ID, ACCOUNT, PASSWORD, NAME, IDCARD, STATUS, EMAIL, MOBILE, PHONE, WORKCARD, CAKEY, CREATETIME, ORGPID, ORGID) VALUES (6, 'h1', '670B14728AD9902AECBA32E22FA4F6BD', '黄一', '', 1, '', '', '', '', '', '2014-06-10 11:19:25', 2, 4);
INSERT INTO ds_common_user (ID, ACCOUNT, PASSWORD, NAME, IDCARD, STATUS, EMAIL, MOBILE, PHONE, WORKCARD, CAKEY, CREATETIME, ORGPID, ORGID) VALUES (7, 'h2', '670B14728AD9902AECBA32E22FA4F6BD', '黄二', '', 1, '', '', '', '', '', '2014-06-10 11:19:33', 2, 4);
INSERT INTO ds_common_user (ID, ACCOUNT, PASSWORD, NAME, IDCARD, STATUS, EMAIL, MOBILE, PHONE, WORKCARD, CAKEY, CREATETIME, ORGPID, ORGID) VALUES (8, 'h3', '670B14728AD9902AECBA32E22FA4F6BD', '黄三', '', 0, '', '', '', '', '', '2014-06-10 11:19:44', 2, 4);


INSERT INTO ds_common_userorg (ID, ORGID, USERID) VALUES (22, 8, 6);
INSERT INTO ds_common_userorg (ID, ORGID, USERID) VALUES (24, 9, 6);
INSERT INTO ds_common_userorg (ID, ORGID, USERID) VALUES (27, 8, 7);
INSERT INTO ds_common_userorg (ID, ORGID, USERID) VALUES (29, 8, 8);
INSERT INTO ds_common_userorg (ID, ORGID, USERID) VALUES (31, 9, 8);
INSERT INTO ds_common_userorg (ID, ORGID, USERID) VALUES (33, 9, 1);
INSERT INTO ds_common_userorg (ID, ORGID, USERID) VALUES (35, 4, -1);


INSERT INTO ds_common_userrole (ID, ROLEID, USERID) VALUES (49, 17, 2);
INSERT INTO ds_common_userrole (ID, ROLEID, USERID) VALUES (51, 17, 3);
INSERT INTO ds_common_userrole (ID, ROLEID, USERID) VALUES (54, 17, 5);
INSERT INTO ds_common_userrole (ID, ROLEID, USERID) VALUES (55, 17, 6);
INSERT INTO ds_common_userrole (ID, ROLEID, USERID) VALUES (56, 17, -1);

