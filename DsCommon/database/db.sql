/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2015-02-05 15:53:54                           */
/*==============================================================*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
--  Table structure for `ds_cms_category`
-- ----------------------------
DROP TABLE IF EXISTS `ds_cms_category`;
CREATE TABLE `ds_cms_category` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `PID` bigint(20) DEFAULT NULL COMMENT '父ID',
  `SITEID` bigint(20) DEFAULT NULL COMMENT '站点ID',
  `NAME` varchar(300) DEFAULT NULL COMMENT '栏目名称',
  `FOLDER` varchar(300) DEFAULT NULL COMMENT '目录名称',
  `STATUS` int(11) DEFAULT NULL COMMENT '类型(0列表，1单页，2外链)',
  `URL` varchar(300) DEFAULT NULL COMMENT '链接',
  `IMG` varchar(300) DEFAULT NULL COMMENT '图片',
  `KEYWORDS` varchar(300) DEFAULT NULL COMMENT '关键词',
  `CONTENT` text COMMENT '内容',
  `SEQ` varchar(300) DEFAULT NULL COMMENT '排序',
  `VIEWSITE` varchar(300) DEFAULT NULL COMMENT '网站模板',
  `VIEWAPP` varchar(300) DEFAULT NULL COMMENT 'APP模板',
  `PAGEVIEWSITE` varchar(300) DEFAULT NULL COMMENT '内容网站模板',
  `PAGEVIEWAPP` varchar(300) DEFAULT NULL COMMENT '内容APP模板',
  PRIMARY KEY (`ID`),
  KEY `FK_DS_CMS_CATEGORY` (`PID`),
  CONSTRAINT `FK_DS_CMS_CATEGORY` FOREIGN KEY (`PID`) REFERENCES `ds_cms_category` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='栏目';

-- ----------------------------
--  Table structure for `ds_cms_page`
-- ----------------------------
DROP TABLE IF EXISTS `ds_cms_page`;
CREATE TABLE `ds_cms_page` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `SITEID` bigint(20) DEFAULT NULL COMMENT '站点ID',
  `CATEGORYID` bigint(20) DEFAULT NULL COMMENT '栏目ID',
  `TITLE` varchar(300) DEFAULT NULL COMMENT '标题',
  `URL` varchar(300) DEFAULT NULL COMMENT '链接',
  `KEYWORDS` varchar(300) DEFAULT NULL COMMENT '关键词',
  `SUMMARY` varchar(300) DEFAULT NULL COMMENT '摘要',
  `CONTENT` text COMMENT '内容',
  `RELEASETIME` varchar(19) DEFAULT NULL COMMENT '发布时间',
  `IMG` varchar(300) DEFAULT NULL COMMENT '图片',
  `IMGTOP` int(11) DEFAULT NULL COMMENT '焦点图(0否，1是)',
  `PAGETOP` int(11) DEFAULT NULL COMMENT '首页推荐(0否，1是)',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='内容';

-- ----------------------------
--  Table structure for `ds_cms_site`
-- ----------------------------
DROP TABLE IF EXISTS `ds_cms_site`;
CREATE TABLE `ds_cms_site` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `QYBM` varchar(300) DEFAULT NULL COMMENT '企业编码',
  `NAME` varchar(300) DEFAULT NULL COMMENT '站点名称',
  `FOLDER` varchar(300) DEFAULT NULL COMMENT '目录名称',
  `URL` varchar(300) DEFAULT NULL COMMENT '链接',
  `IMG` varchar(300) DEFAULT NULL COMMENT '图片',
  `VIEWSITE` varchar(300) DEFAULT NULL COMMENT '网站模板',
  `VIEWAPP` varchar(300) DEFAULT NULL COMMENT 'APP模板',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='站点';

-- ----------------------------
--  Table structure for `ds_common_dict`
-- ----------------------------
DROP TABLE IF EXISTS `ds_common_dict`;
CREATE TABLE `ds_common_dict` (
  `ID` bigint(18) NOT NULL COMMENT '主键',
  `NAME` varchar(300) DEFAULT NULL COMMENT '引用名',
  `LABEL` varchar(300) DEFAULT NULL COMMENT '名称',
  `STATUS` int(1) DEFAULT NULL COMMENT '状态(1:树形，0:列表)',
  `SEQ` bigint(18) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='字典分类';

-- ----------------------------
--  Table structure for `ds_common_dict_data`
-- ----------------------------
DROP TABLE IF EXISTS `ds_common_dict_data`;
CREATE TABLE `ds_common_dict_data` (
  `ID` bigint(18) NOT NULL COMMENT '主键',
  `PID` bigint(18) DEFAULT NULL COMMENT '上级ID(本表,所属字典项)',
  `NAME` varchar(300) DEFAULT NULL COMMENT '引用名',
  `LABEL` varchar(300) DEFAULT NULL COMMENT '名称',
  `ALIAS` varchar(128) DEFAULT NULL COMMENT '标识',
  `STATUS` int(1) DEFAULT NULL COMMENT '状态(1:树叉，0:树叶)',
  `SEQ` bigint(18) DEFAULT NULL COMMENT '排序',
  `MEMO` varchar(300) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`ID`),
  KEY `FKDSDICTDATA` (`PID`),
  CONSTRAINT `fk_ds_common_dict_data` FOREIGN KEY (`PID`) REFERENCES `ds_common_dict_data` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='字典项';

-- ----------------------------
--  Table structure for `ds_common_func`
-- ----------------------------
DROP TABLE IF EXISTS `ds_common_func`;
CREATE TABLE `ds_common_func` (
  `ID` bigint(18) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `PID` bigint(18) DEFAULT NULL COMMENT '上级ID(本表)',
  `SYSTEMID` bigint(18) DEFAULT NULL COMMENT '所属系统ID',
  `NAME` varchar(300) DEFAULT NULL COMMENT '名称',
  `ALIAS` varchar(300) DEFAULT NULL COMMENT '标识',
  `URI` varchar(300) DEFAULT NULL COMMENT '对应的URI',
  `IMG` varchar(100) DEFAULT NULL COMMENT '显示图标',
  `STATUS` int(11) DEFAULT NULL COMMENT '状态(0不是菜单,1菜单,不是菜单不能添加下级)',
  `SEQ` bigint(18) DEFAULT NULL COMMENT '排序',
  `MEMO` varchar(3000) DEFAULT NULL COMMENT '扩展信息',
  `RESOURCES` varchar(4000) DEFAULT NULL COMMENT '资源集合',
  PRIMARY KEY (`ID`),
  KEY `FK_DS_COMMON_FUNC` (`PID`),
  KEY `FK_DS_COMMON_FUNC_SYSTEM` (`SYSTEMID`),
  CONSTRAINT `FK_DS_COMMON_FUNC` FOREIGN KEY (`PID`) REFERENCES `ds_common_func` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_DS_COMMON_FUNC_SYSTEM` FOREIGN KEY (`SYSTEMID`) REFERENCES `ds_common_system` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=76 DEFAULT CHARSET=utf8 COMMENT='功能菜单';

-- ----------------------------
--  Table structure for `ds_common_login`
-- ----------------------------
DROP TABLE IF EXISTS `ds_common_login`;
CREATE TABLE `ds_common_login` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `LOGINTIME` varchar(19) DEFAULT NULL COMMENT '登录时间',
  `LOGOUTTIME` varchar(19) DEFAULT NULL COMMENT '登出时间',
  `TIMEOUTTIME` varchar(19) DEFAULT NULL COMMENT '超时时间',
  `PWDTIME` varchar(19) DEFAULT NULL COMMENT '密码修改时间，没修改则为空，修改成功后直接登出',
  `TICKET` varchar(128) DEFAULT NULL COMMENT '登录标识',
  `IP` varchar(128) DEFAULT NULL COMMENT 'IP地址',
  `ACCOUNT` varchar(64) DEFAULT NULL COMMENT '操作人账号',
  `NAME` varchar(30) DEFAULT NULL COMMENT '操作人名称',
  `STATUS` int(11) DEFAULT NULL COMMENT '状态(0失败,1成功)',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=131 DEFAULT CHARSET=utf8 COMMENT='系统登录日志';

-- ----------------------------
--  Table structure for `ds_common_org`
-- ----------------------------
DROP TABLE IF EXISTS `ds_common_org`;
CREATE TABLE `ds_common_org` (
  `ID` bigint(18) NOT NULL AUTO_INCREMENT COMMENT '部门ID',
  `PID` bigint(18) DEFAULT NULL COMMENT '上级ID(本表,所属组织机构)',
  `NAME` varchar(300) DEFAULT NULL COMMENT '名称',
  `STATUS` int(1) DEFAULT NULL COMMENT '是否单位(1单位,0部门)',
  `SEQ` bigint(18) DEFAULT NULL COMMENT '排序',
  `DUTYSCOPE` varchar(3000) DEFAULT NULL COMMENT '职责范围',
  `MEMO` varchar(3000) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`ID`),
  KEY `FK_DS_COMMON_ORG` (`PID`),
  CONSTRAINT `FK_DS_COMMON_ORG` FOREIGN KEY (`PID`) REFERENCES `ds_common_org` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=83 DEFAULT CHARSET=utf8 COMMENT='组织机构';

-- ----------------------------
--  Table structure for `ds_common_orgrole`
-- ----------------------------
DROP TABLE IF EXISTS `ds_common_orgrole`;
CREATE TABLE `ds_common_orgrole` (
  `ID` bigint(18) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `ORGID` bigint(18) NOT NULL COMMENT '岗位ID',
  `ROLEID` bigint(18) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`ID`),
  KEY `FK_DS_COMMON_ORGROLE_ORG` (`ORGID`),
  KEY `FK_DS_COMMON_ORGROLE_ROLE` (`ROLEID`),
  CONSTRAINT `FK_DS_COMMON_ORGROLE_ORG` FOREIGN KEY (`ORGID`) REFERENCES `ds_common_org` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_DS_COMMON_ORGROLE_ROLE` FOREIGN KEY (`ROLEID`) REFERENCES `ds_common_role` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8 COMMENT='岗位角色关系';

-- ----------------------------
--  Table structure for `ds_common_role`
-- ----------------------------
DROP TABLE IF EXISTS `ds_common_role`;
CREATE TABLE `ds_common_role` (
  `ID` bigint(18) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `PID` bigint(18) DEFAULT NULL COMMENT '上级ID(本表)',
  `SYSTEMID` bigint(18) NOT NULL COMMENT '所属系统ID',
  `NAME` varchar(300) DEFAULT NULL COMMENT '名称',
  `SEQ` bigint(18) DEFAULT NULL COMMENT '排序',
  `MEMO` varchar(300) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`ID`),
  KEY `FK_DS_COMMON_ROLE` (`PID`),
  KEY `FK_DS_COMMON_ROLE_SYSTEM` (`SYSTEMID`),
  CONSTRAINT `FK_DS_COMMON_ROLE` FOREIGN KEY (`PID`) REFERENCES `ds_common_role` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_DS_COMMON_ROLE_SYSTEM` FOREIGN KEY (`SYSTEMID`) REFERENCES `ds_common_system` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='系统角色';

-- ----------------------------
--  Table structure for `ds_common_rolefunc`
-- ----------------------------
DROP TABLE IF EXISTS `ds_common_rolefunc`;
CREATE TABLE `ds_common_rolefunc` (
  `ID` bigint(18) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `SYSTEMID` bigint(18) DEFAULT NULL COMMENT '系统ID',
  `ROLEID` bigint(18) DEFAULT NULL COMMENT '角色ID',
  `FUNCID` bigint(18) DEFAULT NULL COMMENT '功能ID',
  PRIMARY KEY (`ID`),
  KEY `FK_DS_COMMON_ROLEFUNC_FUNC` (`FUNCID`),
  KEY `FK_DS_COMMON_ROLEFUNC_ROLE` (`ROLEID`),
  KEY `FK_DS_COMMON_ROLEFUNC_SYSTEM` (`SYSTEMID`),
  CONSTRAINT `FK_DS_COMMON_ROLEFUNC_FUNC` FOREIGN KEY (`FUNCID`) REFERENCES `ds_common_func` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_DS_COMMON_ROLEFUNC_ROLE` FOREIGN KEY (`ROLEID`) REFERENCES `ds_common_role` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_DS_COMMON_ROLEFUNC_SYSTEM` FOREIGN KEY (`SYSTEMID`) REFERENCES `ds_common_system` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=135 DEFAULT CHARSET=utf8 COMMENT='系统角色功能关系';

-- ----------------------------
--  Table structure for `ds_common_system`
-- ----------------------------
DROP TABLE IF EXISTS `ds_common_system`;
CREATE TABLE `ds_common_system` (
  `ID` bigint(18) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `NAME` varchar(300) DEFAULT NULL COMMENT '名称',
  `ALIAS` varchar(128) DEFAULT NULL COMMENT '系统别名',
  `PASSWORD` varchar(64) DEFAULT NULL COMMENT '访问密码',
  `DOMAINURL` varchar(300) DEFAULT NULL COMMENT '网络地址和端口',
  `ROOTURL` varchar(300) DEFAULT NULL COMMENT '应用根路径',
  `MENUURL` varchar(300) DEFAULT NULL COMMENT '菜单地址',
  `STATUS` int(1) DEFAULT NULL COMMENT '状态(0,禁止,1,允许)',
  `SEQ` bigint(18) DEFAULT NULL COMMENT '排序',
  `MEMO` varchar(300) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='应用系统';

-- ----------------------------
--  Table structure for `ds_common_user`
-- ----------------------------
DROP TABLE IF EXISTS `ds_common_user`;
CREATE TABLE `ds_common_user` (
  `ID` bigint(18) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `ACCOUNT` varchar(64) NOT NULL COMMENT '帐号',
  `PASSWORD` varchar(256) DEFAULT NULL COMMENT '密码',
  `NAME` varchar(30) DEFAULT NULL COMMENT '姓名',
  `IDCARD` varchar(64) DEFAULT NULL COMMENT '身份证号',
  `STATUS` int(1) DEFAULT NULL COMMENT '状态(0,禁止,1,允许)',
  `EMAIL` varchar(300) DEFAULT NULL COMMENT '电子邮件',
  `MOBILE` varchar(30) DEFAULT NULL COMMENT '手机',
  `PHONE` varchar(30) DEFAULT NULL COMMENT '电话',
  `WORKCARD` varchar(64) DEFAULT NULL COMMENT '工作证号',
  `CAKEY` varchar(1024) DEFAULT NULL COMMENT 'CA证书的KEY',
  `CREATETIME` varchar(19) DEFAULT NULL COMMENT '创建时间',
  `ORGPID` bigint(18) DEFAULT NULL COMMENT '单位ID',
  `ORGID` bigint(18) DEFAULT NULL COMMENT '部门ID',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8 COMMENT='系统用户';

-- ----------------------------
--  Table structure for `ds_common_userorg`
-- ----------------------------
DROP TABLE IF EXISTS `ds_common_userorg`;
CREATE TABLE `ds_common_userorg` (
  `ID` bigint(18) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `ORGID` bigint(18) NOT NULL COMMENT '岗位ID',
  `USERID` bigint(18) DEFAULT NULL COMMENT '用户ID',
  PRIMARY KEY (`ID`),
  KEY `FK_DS_COMMON_USERORG_ORG` (`ORGID`),
  KEY `FK_DS_COMMON_USERORG_USER` (`USERID`),
  CONSTRAINT `FK_DS_COMMON_USERORG_ORG` FOREIGN KEY (`ORGID`) REFERENCES `ds_common_org` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_DS_COMMON_USERORG_USER` FOREIGN KEY (`USERID`) REFERENCES `ds_common_user` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8 COMMENT='用户岗位关系';

-- ----------------------------
--  Table structure for `ds_common_userrole`
-- ----------------------------
DROP TABLE IF EXISTS `ds_common_userrole`;
CREATE TABLE `ds_common_userrole` (
  `ID` bigint(18) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `ROLEID` bigint(18) NOT NULL COMMENT '角色ID',
  `USERID` bigint(18) NOT NULL COMMENT '用户ID',
  PRIMARY KEY (`ID`),
  KEY `FK_DS_COMMON_USERORG_USER` (`USERID`),
  KEY `FK_DS_COMMON_USERORG_ROLE` (`ROLEID`) USING BTREE,
  CONSTRAINT `ds_common_userrole_ibfk_1` FOREIGN KEY (`ROLEID`) REFERENCES `ds_common_role` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `ds_common_userrole_ibfk_2` FOREIGN KEY (`USERID`) REFERENCES `ds_common_user` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8 COMMENT='用户角色关系';

-- ----------------------------
--  Table structure for `ds_ep_enterprise`
-- ----------------------------
DROP TABLE IF EXISTS `ds_ep_enterprise`;
CREATE TABLE `ds_ep_enterprise` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `NAME` varchar(300) DEFAULT NULL COMMENT '企业名称',
  `SSXQ` varchar(30) DEFAULT NULL COMMENT '所属辖区',
  `QYBM` varchar(64) DEFAULT NULL COMMENT '编码',
  `STATUS` int(11) DEFAULT NULL COMMENT '状态',
  `TYPE` varchar(300) DEFAULT NULL COMMENT '类型',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='企业';

-- ----------------------------
--  Table structure for `ds_ep_user`
-- ----------------------------
DROP TABLE IF EXISTS `ds_ep_user`;
CREATE TABLE `ds_ep_user` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `QYBM` varchar(64) DEFAULT NULL COMMENT '企业编码',
  `ACCOUNT` varchar(64) DEFAULT NULL COMMENT '账号',
  `PASSWORD` varchar(256) DEFAULT NULL COMMENT '密码',
  `NAME` varchar(30) DEFAULT NULL COMMENT '姓名',
  `IDCARD` varchar(64) DEFAULT NULL COMMENT '身份证号',
  `STATUS` int(11) DEFAULT NULL COMMENT '状态',
  `USERTYPE` int(11) DEFAULT NULL COMMENT '用户类型',
  `EMAIL` varchar(300) DEFAULT NULL COMMENT '电子邮件',
  `MOBILE` varchar(30) DEFAULT NULL COMMENT '手机',
  `PHONE` varchar(30) DEFAULT NULL COMMENT '电话',
  `WORKCARD` varchar(64) DEFAULT NULL COMMENT '工作证号',
  `CAKEY` varchar(1024) DEFAULT NULL COMMENT 'CA证书的KEY',
  `CREATETIME` varchar(19) DEFAULT NULL COMMENT '创建时间',
  `SSDW` varchar(300) DEFAULT NULL COMMENT '所属单位',
  `SSBM` varchar(300) DEFAULT NULL COMMENT '所属部门',
  `FAX` varchar(30) DEFAULT NULL COMMENT '传真',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='企业用户';

-- ----------------------------
--  Table structure for `ds_flow`
-- ----------------------------
DROP TABLE IF EXISTS `ds_flow`;
CREATE TABLE `ds_flow` (
  `ID` bigint(18) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `CATEGORYID` bigint(18) DEFAULT NULL COMMENT '分类',
  `ALIAS` varchar(300) DEFAULT NULL COMMENT '流程标识',
  `VNUM` int(11) DEFAULT NULL COMMENT '内部版本0为编辑版本',
  `DEPLOYID` varchar(300) DEFAULT NULL COMMENT '流程发布ID，VNUM为0的放最新版本',
  `NAME` varchar(300) DEFAULT NULL COMMENT '名字',
  `STATUS` int(1) DEFAULT NULL COMMENT '状态(1启用,0禁用)',
  PRIMARY KEY (`ID`),
  KEY `FK_DS_FLOW` (`CATEGORYID`),
  CONSTRAINT `FK_DS_FLOW` FOREIGN KEY (`CATEGORYID`) REFERENCES `ds_flow_category` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='流程';

-- ----------------------------
--  Table structure for `ds_flow_category`
-- ----------------------------
DROP TABLE IF EXISTS `ds_flow_category`;
CREATE TABLE `ds_flow_category` (
  `ID` bigint(18) NOT NULL AUTO_INCREMENT COMMENT '分类ID',
  `NAME` varchar(300) DEFAULT NULL COMMENT '分类名称',
  `PID` bigint(18) DEFAULT NULL COMMENT '父类',
  `SEQ` bigint(18) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='流程分类';

-- ----------------------------
--  Table structure for `ds_flow_pi`
-- ----------------------------
DROP TABLE IF EXISTS `ds_flow_pi`;
CREATE TABLE `ds_flow_pi` (
  `ID` bigint(18) NOT NULL AUTO_INCREMENT COMMENT '主键ID（流程实例ID）',
  `YWLSH` varchar(300) DEFAULT NULL COMMENT '业务流水号',
  `ALIAS` varchar(300) DEFAULT NULL COMMENT '流程标识',
  `FLOWID` bigint(18) DEFAULT NULL COMMENT '流程ID（对应deployid）',
  `DEPLOYID` varchar(300) DEFAULT NULL COMMENT '流程发布ID',
  `PIDAY` int(11) DEFAULT NULL COMMENT '时限天数',
  `PIDAYTYPE` int(1) DEFAULT NULL COMMENT '时限天数类型(0日历日,1工作日)',
  `PISTART` varchar(19) DEFAULT NULL COMMENT '开始时间',
  `PIEND` varchar(19) DEFAULT NULL COMMENT '结束时间',
  `PIUPSTART` varchar(19) DEFAULT NULL COMMENT '挂起开始时间',
  `PIUPEND` varchar(19) DEFAULT NULL COMMENT '挂起结束时间',
  `STATUS` int(1) DEFAULT NULL COMMENT '流程状态(1,申请,2,运行,3挂起,0结束)',
  `CACCOUNT` varchar(300) DEFAULT NULL COMMENT '承办人账号',
  `CNAME` varchar(300) DEFAULT NULL COMMENT '承办人',
  `PIALIAS` varchar(4000) DEFAULT NULL COMMENT '当前任务标识，以逗号分隔',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8 COMMENT='流程实例';

-- ----------------------------
--  Table structure for `ds_flow_pi_data`
-- ----------------------------
DROP TABLE IF EXISTS `ds_flow_pi_data`;
CREATE TABLE `ds_flow_pi_data` (
  `ID` bigint(18) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `PIID` bigint(18) DEFAULT NULL COMMENT '流程实例ID',
  `TALIAS` varchar(300) DEFAULT NULL COMMENT '任务标识',
  `TNAME` varchar(300) DEFAULT NULL COMMENT '任务名称',
  `STATUS` int(1) DEFAULT NULL COMMENT '状态(0已处理,1代办,2挂起,3取消挂起)',
  `PACCOUNT` varchar(300) DEFAULT NULL COMMENT '经办人ID',
  `PNAME` varchar(300) DEFAULT NULL COMMENT '经办人姓名',
  `PTIME` varchar(19) DEFAULT NULL COMMENT '经办时间',
  `PTYPE` varchar(300) DEFAULT NULL COMMENT '经办类型(0拒绝,1同意等等)',
  `MEMO` varchar(4000) DEFAULT NULL COMMENT '意见',
  PRIMARY KEY (`ID`),
  KEY `FK_DS_FLOW_PI_DATA` (`PIID`),
  CONSTRAINT `FK_DS_FLOW_PI_DATA` FOREIGN KEY (`PIID`) REFERENCES `ds_flow_pi` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8 COMMENT='流程执行明细';

-- ----------------------------
--  Table structure for `ds_flow_pi_waiting`
-- ----------------------------
DROP TABLE IF EXISTS `ds_flow_pi_waiting`;
CREATE TABLE `ds_flow_pi_waiting` (
  `ID` bigint(18) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `PIID` bigint(18) DEFAULT NULL COMMENT '实例ID',
  `YWLSH` varchar(300) DEFAULT NULL COMMENT '业务流水号',
  `FLOWID` bigint(18) DEFAULT NULL COMMENT '流程ID',
  `FLOWNAME` varchar(300) DEFAULT NULL COMMENT '流程名称',
  `TALIAS` varchar(300) DEFAULT NULL COMMENT '任务标识',
  `TNAME` varchar(300) DEFAULT NULL COMMENT '任务名称',
  `TCOUNT` int(11) DEFAULT NULL COMMENT '合并任务个数（只有一个任务时等于1，其余大于1）',
  `TNEXT` varchar(4000) DEFAULT NULL COMMENT '下级任务（以逗号分隔节点标识， 以|线分隔分支任务）',
  `TSTART` varchar(19) DEFAULT NULL COMMENT '任务开始时间',
  `TUSER` varchar(4000) DEFAULT NULL COMMENT '经办人',
  `TUSERS` varchar(4000) DEFAULT NULL COMMENT '候选经办人',
  `TMEMO` varchar(4000) DEFAULT NULL COMMENT '参数',
  `TINTERFACE` varchar(300) DEFAULT NULL COMMENT '处理接口类',
  PRIMARY KEY (`ID`),
  KEY `FK_DS_FLOW_PI_DOING` (`PIID`),
  CONSTRAINT `FK_DS_FLOW_PI_DOING` FOREIGN KEY (`PIID`) REFERENCES `ds_flow_pi` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COMMENT='流程待办事项';

-- ----------------------------
--  Table structure for `ds_flow_task`
-- ----------------------------
DROP TABLE IF EXISTS `ds_flow_task`;
CREATE TABLE `ds_flow_task` (
  `ID` bigint(18) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `FLOWID` bigint(18) DEFAULT NULL COMMENT '流程ID',
  `DEPLOYID` varchar(300) DEFAULT NULL COMMENT '流程发布ID，当前版本此值为空',
  `TALIAS` varchar(300) DEFAULT NULL COMMENT '节点标识（start开始，end结束）',
  `TNAME` varchar(300) DEFAULT NULL COMMENT '节点名称',
  `TCOUNT` int(11) DEFAULT NULL COMMENT '合并任务个数（只有一个任务时等于1，其余大于1）',
  `TNEXT` varchar(4000) DEFAULT NULL COMMENT '下级任务（以逗号分隔节点标识， 以|线分隔分支任务）',
  `TUSERS` varchar(4000) DEFAULT NULL COMMENT '当前任务的用户ID（以逗号分隔节点标识）',
  `TMEMO` varchar(4000) DEFAULT NULL COMMENT '参数',
  PRIMARY KEY (`ID`),
  KEY `FK_DS_FLOW_TASK` (`FLOWID`),
  CONSTRAINT `FK_DS_FLOW_TASK` FOREIGN KEY (`FLOWID`) REFERENCES `ds_flow` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8 COMMENT='流程任务';

-- ----------------------------
--  Table structure for `ds_person_user`
-- ----------------------------
DROP TABLE IF EXISTS `ds_person_user`;
CREATE TABLE `ds_person_user` (
  `ID` bigint(18) NOT NULL COMMENT '主键',
  `IDCARD` varchar(64) DEFAULT NULL COMMENT '身份证号',
  `ACCOUNT` varchar(64) DEFAULT NULL COMMENT '账号',
  `PASSWORD` varchar(256) DEFAULT NULL COMMENT '密码',
  `NAME` varchar(30) DEFAULT NULL COMMENT '姓名',
  `STATUS` int(11) DEFAULT NULL COMMENT '状态',
  `EMAIL` varchar(300) DEFAULT NULL COMMENT '电子邮件',
  `MOBILE` varchar(30) DEFAULT NULL COMMENT '手机',
  `PHONE` varchar(30) DEFAULT NULL COMMENT '电话',
  `CAKEY` varchar(1024) DEFAULT NULL COMMENT 'CA证书的KEY',
  `CREATETIME` varchar(19) DEFAULT NULL COMMENT '创建时间',
  `USERTYPE` varchar(30) DEFAULT NULL COMMENT '用户类型',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='个人用户';

-- ----------------------------
--  Table structure for `ds_xzsp`
-- ----------------------------
DROP TABLE IF EXISTS `ds_xzsp`;
CREATE TABLE `ds_xzsp` (
  `ID` bigint(18) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `SBLSH` varchar(19) DEFAULT NULL COMMENT '申办流水号',
  `SPTYPE` int(11) DEFAULT NULL COMMENT '对象类型0ShenBan，1YuShouLi，2ShouLi，3ShenPi，4BanJie，5TeBieChengXuQiDong，6TeBieChengXuBanJie，7BuJiaoGaoZhi，8BuJiaoShouLi，9LingQuDengJi',
  `FSZT` int(11) DEFAULT NULL COMMENT '发送状态0待发1已发',
  `FSCS` int(11) DEFAULT NULL COMMENT '发送次数',
  `FSSJ` varchar(19) DEFAULT NULL COMMENT '发送时间',
  `SPOBJECT` varchar(4000) DEFAULT NULL COMMENT '序列化对象',
  `MEMO` varchar(4000) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='电子政务行政审批';

-- ----------------------------
--  Table structure for `ssyy`
-- ----------------------------
DROP TABLE IF EXISTS `ssyy`;
CREATE TABLE `ssyy` (
  `ID` bigint(20) NOT NULL DEFAULT '0',
  `TNAME` varchar(300) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '大类',
  `NAME` varchar(300) DEFAULT NULL COMMENT '小类',
  `MSG` longtext,
  `C1` varchar(300) DEFAULT NULL COMMENT '热量(千卡)',
  `C2` varchar(300) DEFAULT NULL COMMENT '硫胺素(毫克)',
  `C3` varchar(300) DEFAULT NULL COMMENT '钙(毫克)',
  `C4` varchar(300) DEFAULT NULL COMMENT '蛋白质(克)',
  `C5` varchar(300) DEFAULT NULL COMMENT '核黄素(毫克)',
  `C6` varchar(300) DEFAULT NULL COMMENT '镁(毫克)',
  `C7` varchar(300) DEFAULT NULL COMMENT '脂肪(克)',
  `C8` varchar(300) DEFAULT NULL COMMENT '烟酸(毫克)',
  `C9` varchar(300) DEFAULT NULL COMMENT '铁(毫克)',
  `C10` varchar(300) DEFAULT NULL COMMENT '碳水化合物(克)',
  `C11` varchar(300) DEFAULT NULL COMMENT '维生素C(毫克)',
  `C12` varchar(300) DEFAULT NULL COMMENT '锰(毫克)',
  `C13` varchar(300) DEFAULT NULL COMMENT '膳食纤维(克)',
  `C14` varchar(300) DEFAULT NULL COMMENT '维生素E(毫克)',
  `C15` varchar(300) DEFAULT NULL COMMENT '锌(毫克)',
  `C16` varchar(300) DEFAULT NULL COMMENT '维生素A(微克)',
  `C17` varchar(300) DEFAULT NULL COMMENT '胆固醇(毫克)',
  `C18` varchar(300) DEFAULT NULL COMMENT '铜(毫克)',
  `C19` varchar(300) DEFAULT NULL COMMENT '胡罗卜素(微克)',
  `C20` varchar(300) DEFAULT NULL COMMENT '钾(毫克)',
  `C21` varchar(300) DEFAULT NULL COMMENT '磷(毫克)',
  `C22` varchar(300) DEFAULT NULL COMMENT '视黄醇当量(微克)',
  `C23` varchar(300) DEFAULT NULL COMMENT '钠(毫克)',
  `C24` varchar(300) DEFAULT NULL COMMENT '硒(微克)',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='膳食营养';

