/*
MySQL Backup
Source Server Version: 5.5.45
Source Database: dswork
Date: 2016/4/13 18:11:37
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
--  Table structure for `ds_bbs_forum`
-- ----------------------------
DROP TABLE IF EXISTS `ds_bbs_forum`;
CREATE TABLE `ds_bbs_forum` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `PID` bigint(20) DEFAULT NULL COMMENT '父ID',
  `SITEID` bigint(20) DEFAULT NULL COMMENT '站点ID',
  `NAME` varchar(300) DEFAULT NULL COMMENT '名称',
  `SUMMARY` varchar(300) DEFAULT NULL COMMENT '摘要',
  `STATUS` int(1) DEFAULT NULL COMMENT '状态(1启用,0禁用)',
  `SEQ` varchar(300) DEFAULT NULL COMMENT '排序',
  `VIEWSITE` varchar(300) DEFAULT NULL COMMENT '模板',
  PRIMARY KEY (`ID`),
  KEY `FK_DS_BBS_FORUM` (`PID`),
  KEY `FK_DS_BBS_FORUM_SITE` (`SITEID`),
  CONSTRAINT `FK_DS_BBS_FORUM` FOREIGN KEY (`PID`) REFERENCES `ds_bbs_forum` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_DS_BBS_FORUM_SITE` FOREIGN KEY (`SITEID`) REFERENCES `ds_bbs_site` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COMMENT='版块';

-- ----------------------------
--  Table structure for `ds_bbs_page`
-- ----------------------------
DROP TABLE IF EXISTS `ds_bbs_page`;
CREATE TABLE `ds_bbs_page` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `SITEID` bigint(20) DEFAULT NULL COMMENT '站点ID',
  `FORUMID` bigint(20) DEFAULT NULL COMMENT '版块ID',
  `USERID` varchar(300) DEFAULT NULL COMMENT '发表人ID',
  `TITLE` varchar(300) DEFAULT NULL COMMENT '标题',
  `URL` varchar(300) DEFAULT NULL COMMENT '链接',
  `SUMMARY` varchar(300) DEFAULT NULL COMMENT '摘要',
  `ISESSENCE` int(11) DEFAULT NULL COMMENT '精华(0否，1是)',
  `ISTOP` int(11) DEFAULT NULL COMMENT '置顶(0否，1是)',
  `METAKEYWORDS` varchar(300) DEFAULT NULL COMMENT 'meta关键词',
  `METADESCRIPTION` varchar(300) DEFAULT NULL COMMENT 'meta描述',
  `RELEASEUSER` varchar(30) DEFAULT NULL COMMENT '发表人',
  `RELEASETIME` varchar(19) DEFAULT NULL COMMENT '发表时间',
  `OVERTIME` varchar(19) DEFAULT NULL COMMENT '结贴时间',
  `LASTUSER` varchar(300) DEFAULT NULL COMMENT '最后回复人',
  `LASTTIME` varchar(300) DEFAULT NULL COMMENT '最后回复时间',
  `NUMPV` int(11) DEFAULT NULL COMMENT '点击量',
  `NUMHT` int(11) DEFAULT NULL COMMENT '回贴数',
  `CONTENT` text COMMENT '内容',
  PRIMARY KEY (`ID`),
  KEY `FK_DS_BBS_PAGE_SITE` (`SITEID`),
  CONSTRAINT `FK_DS_BBS_PAGE_SITE` FOREIGN KEY (`SITEID`) REFERENCES `ds_bbs_site` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='主题';

-- ----------------------------
--  Table structure for `ds_bbs_site`
-- ----------------------------
DROP TABLE IF EXISTS `ds_bbs_site`;
CREATE TABLE `ds_bbs_site` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `OWN` varchar(300) DEFAULT NULL COMMENT '拥有者',
  `NAME` varchar(300) DEFAULT NULL COMMENT '站点名称',
  `FOLDER` varchar(300) DEFAULT NULL COMMENT '目录名称',
  `URL` varchar(300) DEFAULT NULL COMMENT '链接',
  `IMG` varchar(300) DEFAULT NULL COMMENT '图片',
  `VIEWSITE` varchar(300) DEFAULT NULL COMMENT '网站模板',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='站点';

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
  `SEQ` varchar(300) DEFAULT NULL COMMENT '排序',
  `VIEWSITE` varchar(300) DEFAULT NULL COMMENT '网站模板',
  `PAGEVIEWSITE` varchar(300) DEFAULT NULL COMMENT '内容网站模板',
  `METAKEYWORDS` varchar(300) DEFAULT NULL COMMENT 'meta关键词',
  `METADESCRIPTION` varchar(300) DEFAULT NULL COMMENT 'meta描述',
  `CONTENT` text COMMENT '内容',
  PRIMARY KEY (`ID`),
  KEY `FK_DS_CMS_CATEGORY` (`PID`),
  KEY `FK_DS_CMS_CATEGORY_SITE` (`SITEID`),
  CONSTRAINT `FK_DS_CMS_CATEGORY` FOREIGN KEY (`PID`) REFERENCES `ds_cms_category` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_DS_CMS_CATEGORY_SITE` FOREIGN KEY (`SITEID`) REFERENCES `ds_cms_site` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='栏目';

-- ----------------------------
--  Table structure for `ds_cms_page`
-- ----------------------------
DROP TABLE IF EXISTS `ds_cms_page`;
CREATE TABLE `ds_cms_page` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `SITEID` bigint(20) DEFAULT NULL COMMENT '站点ID',
  `CATEGORYID` bigint(20) DEFAULT NULL COMMENT '栏目ID',
  `STATUS` int(10) DEFAULT NULL COMMENT '状态',
  `TITLE` varchar(300) DEFAULT NULL COMMENT '标题',
  `URL` varchar(300) DEFAULT NULL COMMENT '链接',
  `METAKEYWORDS` varchar(300) DEFAULT NULL COMMENT 'meta关键词',
  `METADESCRIPTION` varchar(300) DEFAULT NULL COMMENT 'meta描述',
  `SUMMARY` varchar(300) DEFAULT NULL COMMENT '摘要',
  `RELEASETIME` varchar(19) DEFAULT NULL COMMENT '发布时间',
  `RELEASESOURCE` varchar(300) DEFAULT NULL COMMENT '来源',
  `RELEASEUSER` varchar(300) DEFAULT NULL COMMENT '作者',
  `IMG` varchar(300) DEFAULT NULL COMMENT '图片',
  `IMGTOP` int(11) DEFAULT NULL COMMENT '焦点图(0否，1是)',
  `PAGETOP` int(11) DEFAULT NULL COMMENT '首页推荐(0否，1是)',
  `CONTENT` text COMMENT '内容',
  PRIMARY KEY (`ID`),
  KEY `FK_DS_CMS_PAGE_SITE` (`SITEID`),
  CONSTRAINT `FK_DS_CMS_PAGE_SITE` FOREIGN KEY (`SITEID`) REFERENCES `ds_cms_site` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8 COMMENT='内容';

-- ----------------------------
--  Table structure for `ds_cms_site`
-- ----------------------------
DROP TABLE IF EXISTS `ds_cms_site`;
CREATE TABLE `ds_cms_site` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `OWN` varchar(300) DEFAULT NULL COMMENT '拥有者',
  `NAME` varchar(300) DEFAULT NULL COMMENT '站点名称',
  `FOLDER` varchar(300) DEFAULT NULL COMMENT '目录名称',
  `URL` varchar(300) DEFAULT NULL COMMENT '链接',
  `IMG` varchar(300) DEFAULT NULL COMMENT '图片',
  `VIEWSITE` varchar(300) DEFAULT NULL COMMENT '网站模板',
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
  `ID` bigint(18) NOT NULL COMMENT '主键',
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='功能菜单';

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
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='系统登录日志';

-- ----------------------------
--  Table structure for `ds_common_org`
-- ----------------------------
DROP TABLE IF EXISTS `ds_common_org`;
CREATE TABLE `ds_common_org` (
  `ID` bigint(18) NOT NULL COMMENT '部门ID',
  `PID` bigint(18) DEFAULT NULL COMMENT '上级ID(本表,所属组织机构)',
  `NAME` varchar(300) DEFAULT NULL COMMENT '名称',
  `STATUS` int(1) DEFAULT NULL COMMENT '是否单位(1单位,0部门)',
  `SEQ` bigint(18) DEFAULT NULL COMMENT '排序',
  `DUTYSCOPE` varchar(3000) DEFAULT NULL COMMENT '职责范围',
  `MEMO` varchar(3000) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`ID`),
  KEY `FK_DS_COMMON_ORG` (`PID`),
  CONSTRAINT `FK_DS_COMMON_ORG` FOREIGN KEY (`PID`) REFERENCES `ds_common_org` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='组织机构';

-- ----------------------------
--  Table structure for `ds_common_orgrole`
-- ----------------------------
DROP TABLE IF EXISTS `ds_common_orgrole`;
CREATE TABLE `ds_common_orgrole` (
  `ID` bigint(18) NOT NULL COMMENT '主键ID',
  `ORGID` bigint(18) NOT NULL COMMENT '岗位ID',
  `ROLEID` bigint(18) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`ID`),
  KEY `FK_DS_COMMON_ORGROLE_ORG` (`ORGID`),
  KEY `FK_DS_COMMON_ORGROLE_ROLE` (`ROLEID`),
  CONSTRAINT `FK_DS_COMMON_ORGROLE_ORG` FOREIGN KEY (`ORGID`) REFERENCES `ds_common_org` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_DS_COMMON_ORGROLE_ROLE` FOREIGN KEY (`ROLEID`) REFERENCES `ds_common_role` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='岗位角色关系';

-- ----------------------------
--  Table structure for `ds_common_role`
-- ----------------------------
DROP TABLE IF EXISTS `ds_common_role`;
CREATE TABLE `ds_common_role` (
  `ID` bigint(18) NOT NULL COMMENT '主键',
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统角色';

-- ----------------------------
--  Table structure for `ds_common_rolefunc`
-- ----------------------------
DROP TABLE IF EXISTS `ds_common_rolefunc`;
CREATE TABLE `ds_common_rolefunc` (
  `ID` bigint(18) NOT NULL COMMENT '主键ID',
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统角色功能关系';

-- ----------------------------
--  Table structure for `ds_common_system`
-- ----------------------------
DROP TABLE IF EXISTS `ds_common_system`;
CREATE TABLE `ds_common_system` (
  `ID` bigint(18) NOT NULL COMMENT '主键',
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='应用系统';

-- ----------------------------
--  Table structure for `ds_common_user`
-- ----------------------------
DROP TABLE IF EXISTS `ds_common_user`;
CREATE TABLE `ds_common_user` (
  `ID` bigint(18) NOT NULL COMMENT '主键',
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统用户';

-- ----------------------------
--  Table structure for `ds_common_userorg`
-- ----------------------------
DROP TABLE IF EXISTS `ds_common_userorg`;
CREATE TABLE `ds_common_userorg` (
  `ID` bigint(18) NOT NULL COMMENT '主键ID',
  `ORGID` bigint(18) NOT NULL COMMENT '岗位ID',
  `USERID` bigint(18) DEFAULT NULL COMMENT '用户ID',
  PRIMARY KEY (`ID`),
  KEY `FK_DS_COMMON_USERORG_ORG` (`ORGID`),
  KEY `FK_DS_COMMON_USERORG_USER` (`USERID`),
  CONSTRAINT `FK_DS_COMMON_USERORG_ORG` FOREIGN KEY (`ORGID`) REFERENCES `ds_common_org` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_DS_COMMON_USERORG_USER` FOREIGN KEY (`USERID`) REFERENCES `ds_common_user` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户岗位关系';

-- ----------------------------
--  Table structure for `ds_common_userrole`
-- ----------------------------
DROP TABLE IF EXISTS `ds_common_userrole`;
CREATE TABLE `ds_common_userrole` (
  `ID` bigint(18) NOT NULL COMMENT '主键ID',
  `ROLEID` bigint(18) NOT NULL COMMENT '角色ID',
  `USERID` bigint(18) NOT NULL COMMENT '用户ID',
  PRIMARY KEY (`ID`),
  KEY `FK_DS_COMMON_USERORG_USER` (`USERID`),
  KEY `FK_DS_COMMON_USERORG_ROLE` (`ROLEID`) USING BTREE,
  CONSTRAINT `ds_common_userrole_ibfk_1` FOREIGN KEY (`ROLEID`) REFERENCES `ds_common_role` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `ds_common_userrole_ibfk_2` FOREIGN KEY (`USERID`) REFERENCES `ds_common_user` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色关系';

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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='企业用户';

-- ----------------------------
--  Table structure for `ds_flow`
-- ----------------------------
DROP TABLE IF EXISTS `ds_flow`;
CREATE TABLE `ds_flow` (
  `ID` bigint(18) NOT NULL COMMENT '主键',
  `CATEGORYID` bigint(18) DEFAULT NULL COMMENT '分类',
  `ALIAS` varchar(300) DEFAULT NULL COMMENT '流程标识',
  `VNUM` int(11) DEFAULT NULL COMMENT '内部版本0为编辑版本',
  `DEPLOYID` varchar(300) DEFAULT NULL COMMENT '流程发布ID，VNUM为0的放最新版本',
  `NAME` varchar(300) DEFAULT NULL COMMENT '名字',
  `STATUS` int(1) DEFAULT NULL COMMENT '状态(1启用,0禁用)',
  PRIMARY KEY (`ID`),
  KEY `FK_DS_FLOW` (`CATEGORYID`),
  CONSTRAINT `FK_DS_FLOW` FOREIGN KEY (`CATEGORYID`) REFERENCES `ds_flow_category` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='流程';

-- ----------------------------
--  Table structure for `ds_flow_category`
-- ----------------------------
DROP TABLE IF EXISTS `ds_flow_category`;
CREATE TABLE `ds_flow_category` (
  `ID` bigint(18) NOT NULL COMMENT '分类ID',
  `NAME` varchar(300) DEFAULT NULL COMMENT '分类名称',
  `PID` bigint(18) DEFAULT NULL COMMENT '父类',
  `SEQ` bigint(18) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='流程分类';

-- ----------------------------
--  Table structure for `ds_flow_pi`
-- ----------------------------
DROP TABLE IF EXISTS `ds_flow_pi`;
CREATE TABLE `ds_flow_pi` (
  `ID` bigint(18) NOT NULL COMMENT '主键ID（流程实例ID）',
  `YWLSH` varchar(300) DEFAULT NULL COMMENT '业务流水号',
  `SBLSH` varchar(300) DEFAULT NULL COMMENT '申办流水号',
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='流程实例';

-- ----------------------------
--  Table structure for `ds_flow_pi_data`
-- ----------------------------
DROP TABLE IF EXISTS `ds_flow_pi_data`;
CREATE TABLE `ds_flow_pi_data` (
  `ID` bigint(18) NOT NULL COMMENT '主键ID',
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='流程执行明细';

-- ----------------------------
--  Table structure for `ds_flow_pi_waiting`
-- ----------------------------
DROP TABLE IF EXISTS `ds_flow_pi_waiting`;
CREATE TABLE `ds_flow_pi_waiting` (
  `ID` bigint(18) NOT NULL COMMENT '主键ID',
  `PIID` bigint(18) DEFAULT NULL COMMENT '实例ID',
  `YWLSH` varchar(300) DEFAULT NULL COMMENT '业务流水号',
  `SBLSH` varchar(300) DEFAULT NULL COMMENT '申办流水号',
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='流程待办事项';

-- ----------------------------
--  Table structure for `ds_flow_task`
-- ----------------------------
DROP TABLE IF EXISTS `ds_flow_task`;
CREATE TABLE `ds_flow_task` (
  `ID` bigint(18) NOT NULL COMMENT '主键',
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='流程任务';

-- ----------------------------
--  Table structure for `ds_person_user`
-- ----------------------------
DROP TABLE IF EXISTS `ds_person_user`;
CREATE TABLE `ds_person_user` (
  `ID` bigint(18) NOT NULL AUTO_INCREMENT COMMENT '主键',
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='个人用户';

-- ----------------------------
--  Table structure for `ds_xzsp`
-- ----------------------------
DROP TABLE IF EXISTS `ds_xzsp`;
CREATE TABLE `ds_xzsp` (
  `ID` bigint(18) NOT NULL COMMENT '主键',
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
--  Table structure for `gx_user`
-- ----------------------------
DROP TABLE IF EXISTS `gx_user`;
CREATE TABLE `gx_user` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `ALIAS` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '标识编码(企业编码、身份证...)',
  `NAME` varchar(300) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '名称',
  `PASSWORD` varchar(256) DEFAULT NULL COMMENT '密码',
  `TYPE` varchar(1) DEFAULT NULL COMMENT '类型（0个人，1旅行社，2导游公司，3景区，4饭店...）',
  `STATE` int(1) DEFAULT NULL COMMENT '状态（0未知，1正常，2已注销...）',
  `MEMO` varchar(3000) DEFAULT NULL COMMENT '预留字段',
  `VJSON` varchar(3000) DEFAULT NULL COMMENT '其他数据',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

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

-- ----------------------------
--  Table structure for `zw_gkyjx`
-- ----------------------------
DROP TABLE IF EXISTS `zw_gkyjx`;
CREATE TABLE `zw_gkyjx` (
  `ID` bigint(20) NOT NULL,
  `QUERYID` varchar(300) CHARACTER SET utf8 DEFAULT NULL COMMENT '查询码',
  `NAME` varchar(30) CHARACTER SET utf8 DEFAULT NULL COMMENT '姓名',
  `UNITNAME` varchar(300) CHARACTER SET utf8 DEFAULT NULL COMMENT '单位',
  `CARDNO` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '身份证号码',
  `VTELEPHONE` varchar(30) CHARACTER SET utf8 DEFAULT NULL COMMENT '联系电话',
  `VEMAIL` varchar(300) CHARACTER SET utf8 DEFAULT NULL COMMENT '电子邮箱',
  `VADDRESS` varchar(300) CHARACTER SET utf8 DEFAULT NULL COMMENT '联系地址',
  `TITLE` varchar(300) CHARACTER SET utf8 DEFAULT NULL COMMENT '标题',
  `MSG` varchar(3000) CHARACTER SET utf8 DEFAULT NULL COMMENT '内容',
  `STATUS` int(2) DEFAULT NULL COMMENT '状态',
  `CREATETIME` varchar(19) CHARACTER SET utf8 DEFAULT NULL COMMENT '申请时间',
  `REPLYTIME` varchar(19) CHARACTER SET utf8 DEFAULT NULL COMMENT '处理时间',
  `REPLYRESULT` varchar(3000) CHARACTER SET utf8 DEFAULT NULL COMMENT '处理结果',
  `REPLYUSER` varchar(30) CHARACTER SET utf8 DEFAULT NULL COMMENT '处理人',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='信息公开意见箱';

-- ----------------------------
--  Table structure for `zw_jzxx`
-- ----------------------------
DROP TABLE IF EXISTS `zw_jzxx`;
CREATE TABLE `zw_jzxx` (
  `ID` bigint(20) DEFAULT NULL,
  `NAME` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '用户姓名',
  `CARDTYPE` varchar(30) CHARACTER SET utf8 DEFAULT NULL COMMENT '证件类型',
  `CARDNO` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '证件号码',
  `PHONE` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '联系电话',
  `EMAIL` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '电子邮件',
  `TITLE` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '信件主题',
  `MSG` varchar(3000) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '信件内容',
  `STATUS` int(10) DEFAULT NULL COMMENT '状态',
  `CREATETIME` varchar(19) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建时间',
  `REPLYTIME` varchar(19) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '回复时间',
  `REPLYRESULT` varchar(3000) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '回复意见',
  `REPLYUSER` varchar(30) CHARACTER SET utf8 DEFAULT NULL COMMENT '回复人'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='局长信箱';

-- ----------------------------
--  Table structure for `zw_ysqgk`
-- ----------------------------
DROP TABLE IF EXISTS `zw_ysqgk`;
CREATE TABLE `zw_ysqgk` (
  `ID` bigint(20) NOT NULL,
  `QUERYID` varchar(300) CHARACTER SET utf8 DEFAULT NULL COMMENT '查询码',
  `NAME` varchar(30) CHARACTER SET utf8 DEFAULT NULL COMMENT '姓名/法人代表',
  `UNITNAME` varchar(300) CHARACTER SET utf8 DEFAULT NULL COMMENT '单位名称',
  `DWJGDM` varchar(30) CHARACTER SET utf8 DEFAULT NULL COMMENT '单位机构代码',
  `DWYYZZ` varchar(30) CHARACTER SET utf8 DEFAULT NULL COMMENT '单位营业执照注册号',
  `CARDTYPE` varchar(30) CHARACTER SET utf8 DEFAULT NULL COMMENT '证件类型',
  `CARDNO` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '证件号码',
  `VNAME` varchar(30) CHARACTER SET utf8 DEFAULT NULL COMMENT '联系人/联系人电话',
  `VPOST` varchar(30) CHARACTER SET utf8 DEFAULT NULL COMMENT '邮政编码',
  `VTELEPHONE` varchar(30) CHARACTER SET utf8 DEFAULT NULL COMMENT '联系电话',
  `VFAX` varchar(30) CHARACTER SET utf8 DEFAULT NULL COMMENT '传真',
  `VEMAIL` varchar(300) CHARACTER SET utf8 DEFAULT NULL COMMENT '电子邮箱',
  `VADDRESS` varchar(300) CHARACTER SET utf8 DEFAULT NULL COMMENT '联系地址',
  `CONTENTNAME` varchar(300) CHARACTER SET utf8 DEFAULT NULL COMMENT '所需政府信息文件名称',
  `CONTENTNO` varchar(300) CHARACTER SET utf8 DEFAULT NULL COMMENT '所需政府信息文号',
  `CONTENT` varchar(3000) CHARACTER SET utf8 DEFAULT NULL COMMENT '所需政府信息描述',
  `PURPOSE` varchar(3000) CHARACTER SET utf8 DEFAULT NULL COMMENT '所需政府信息用途',
  `PURPOSEFILE` varchar(30) CHARACTER SET utf8 DEFAULT NULL COMMENT '证明材料',
  `USERFREE` varchar(30) CHARACTER SET utf8 DEFAULT NULL COMMENT '是否申请减免费用',
  `USERFREEFILE` varchar(30) CHARACTER SET utf8 DEFAULT NULL COMMENT '申请材料',
  `INFOGET` varchar(300) CHARACTER SET utf8 DEFAULT NULL COMMENT '提供政府信息的指定方式',
  `INFOTO` varchar(300) CHARACTER SET utf8 DEFAULT NULL COMMENT '获取政府信息的方式',
  `OTHER` varchar(3000) CHARACTER SET utf8 DEFAULT NULL COMMENT '若本机关无法按照指定方式提供所需信息，也可接受其他方式',
  `MEMO` varchar(3000) CHARACTER SET utf8 DEFAULT NULL COMMENT '备注',
  `TYPE` int(2) DEFAULT NULL COMMENT '类型(1公民，2机构，3企业法人，4社团组织，5其他)',
  `STATUS` int(2) DEFAULT NULL COMMENT '状态',
  `CREATETIME` varchar(19) CHARACTER SET utf8 DEFAULT NULL COMMENT '申请时间',
  `ACCEPTTIME` varchar(19) CHARACTER SET utf8 DEFAULT NULL COMMENT '受理时间',
  `ACCEPTRESULT` varchar(3000) CHARACTER SET utf8 DEFAULT NULL COMMENT '受理结果',
  `ACCEPTUSER` varchar(30) CHARACTER SET utf8 DEFAULT NULL COMMENT '受理人',
  `REPLYTIME` varchar(19) CHARACTER SET utf8 DEFAULT NULL COMMENT '处理时间',
  `REPLYRESULT` varchar(3000) CHARACTER SET utf8 DEFAULT NULL COMMENT '处理结果',
  `REPLYUSER` varchar(30) CHARACTER SET utf8 DEFAULT NULL COMMENT '处理人',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='依申请公开';

-- ----------------------------
--  Records 
-- ----------------------------
INSERT INTO `ds_bbs_forum` VALUES ('1',NULL,'0','Windows','PC操作系统','1','9999',''), ('2',NULL,'0','Mac','PC操作系统','1','9999',''), ('3',NULL,'0','Linux','PC操作系统','1','9999',''), ('4',NULL,'0','Android','移动操作系统','1','9999',''), ('5',NULL,'0','iOS','移动操作系统','1','9999',''), ('6','1','0','WindowsXP','PC操作系统','1','9999',''), ('7','1','0','WindowsVista','PC操作系统','1','9999',''), ('8','1','0','Windows7','PC操作系统','1','9999',''), ('9','1','0','Windows8','PC操作系统','1','9999',''), ('10','1','0','Windows10','PC操作系统','1','9999',''), ('11','6','0','WindowsXPSP1','无','1','1',''), ('12','6','0','WindowsXPSP1','无','1','2',''), ('13','6','0','WindowsXPSP3','无','1','3',''), ('14',NULL,'1','Windows','PC操作系统','1','9999',''), ('15',NULL,'1','Mac','PC操作系统','1','9999',''), ('16',NULL,'1','Linux','PC操作系统','1','9999',''), ('17','14','1','WindowsXP','PC操作系统','1','9999',''), ('18','14','1','Windows7','PC操作系统','1','9999',''), ('19','14','1','Windows10','PC操作系统','1','9999',''), ('20','17','1','WindowsXPSP3','无','1','9999','');
INSERT INTO `ds_bbs_page` VALUES ('1','0','6','','Windows10主题','','Windows10主题。。。','1','1','','','admin','2015-04-08 12:01:01','','admin','2015-04-08 12:01:01','0','0','Windows10主题'), ('2','0','6','','Windows10主题2','','Windows10主题。。。','1','0','','','admin','2015-04-08 12:01:01','','admin','2015-04-08 12:01:01','0','0','Windows10主题2');
INSERT INTO `ds_bbs_site` VALUES ('0','adminadmin','默认',NULL,NULL,NULL,NULL), ('1','ep100000','操作系统','','','',''), ('2','ep100000','测试BBS','','','','');
INSERT INTO `ds_cms_category` VALUES ('1',NULL,'0','公司介绍','gsjj','1','/a/gsjj/index.html','','1','show.jsp','','佳作',' ','手机端操作'), ('2',NULL,'0','成功案例','cgal','0','/a/cgal/index.html','','3','category.jsp','page.jsp',' ',' ',' '), ('3',NULL,'0','公司产品','gscp','0','/a/gscp/index.html','','2','list.jsp','page.jsp',' ',' ',' '), ('4','2','0','政府案例','','2','http://www.baidu.com','','3','','',' ',' ',' '), ('5','2','0','企业案例','qyal','0','/a/qyal/index.html','','1','list.jsp','page.jsp',' ',' ',' '), ('6',NULL,'0','政务','zw','0','/a/zw/index.html','','90','category.jsp','page.jsp','','',''), ('7','6','0','公开意见箱','gkyjx','1','/a/gkyjx/index.html','','91','show.jsp','','','','<iframe name=\"zwiframe\" id=\"zwiframe\" src=\"/zw/zwclient/gkyjx/gkyjx.html\" frameborder=\"0\" scrolling=\"no\" style=\"border: 0px currentColor; border-image: none; width: 680px; height: 1000px;\"></iframe>'), ('8','6','0','局长信箱','jzxx','1','/a/jzxx/index.html','','92','show.jsp','','','','<iframe name=\"zwiframe\" id=\"zwiframe\" src=\"/zw/zwclient/jzxx/jzxx.html\" frameborder=\"0\" scrolling=\"no\" style=\"border: 0px currentColor; border-image: none; width: 680px; height: 1000px;\"></iframe>'), ('9','6','0','依申请公开','ysqgk','1','/a/ysqgk/index.html','','93','show.jsp','','','','<iframe name=\"zwiframe\" id=\"zwiframe\" src=\"/zw/zwclient/ysqgk/ysqgk.html\" frameborder=\"0\" scrolling=\"no\" style=\"border: 0px currentColor; border-image: none; width: 680px; height: 1000px;\"></iframe>');
INSERT INTO `ds_cms_page` VALUES ('1','0','5','1','dd','/a/qyal/1.html','ddd','','dddd','2014-09-06 11:13:43',NULL,NULL,'','1','1','ddd'), ('2','0','3','1','vv','/a/gscp/2.html','vv','','vv','2014-09-16 14:32:54',NULL,NULL,'','0','0','计算机管理控制程序计算机管理控制程序'), ('3','0','5','1','你好','/a/qyal/3.html','你好你好你好','你好你好你好你好','你好你好','2015-05-12 18:11:36',NULL,NULL,'','0','1','你好你好你好你好你好你好'), ('4','0','3','1','计算机管理控制程序','/a/gscp/4.html','','','计算机管理控制程序计算机管理控制程序计算机管理控制程序计算机管理控制程序','2015-05-23 14:35:33',NULL,NULL,'','0','0','计算机管理控制程序计算机管理控制程序计算机管理控制程序'), ('5','0','3','1','计算机管理控制程序','/a/gscp/5.html','','','计算机管理控制程序计算机管理控制程序','2015-05-23 14:35:41',NULL,NULL,'','0','0','计算机管理控制程序计算机管理控制程序'), ('6','0','3','1','计算机管理控制程序','/a/gscp/6.html','','','','2015-05-23 14:42:01',NULL,NULL,'','0','0','计算机管理控制程序计算机管理控制程序'), ('7','0','3','1','计算机管理控制程序','/a/gscp/7.html','','','','2015-05-23 14:42:01',NULL,NULL,'','0','0','计算机管理控制程序计算机管理控制程序'), ('8','0','3','1','计算机管理控制程序','/a/gscp/8.html','','','','2015-05-23 14:42:01',NULL,NULL,'','0','0','计算机管理控制程序计算机管理控制程序'), ('9','0','3','1','计算机管理控制程序','/a/gscp/9.html','','','','2015-05-23 14:42:01',NULL,NULL,'','0','0','计算机管理控制程序计算机管理控制程序'), ('10','0','3','1','计算机管理控制程序','/a/gscp/10.html','','','','2015-05-23 14:42:01',NULL,NULL,'','0','0','计算机管理控制程序计算机管理控制程序'), ('11','0','3','1','计算机管理控制程序','/a/gscp/11.html','','','','2015-05-23 14:42:01',NULL,NULL,'','0','0','计算机管理控制程序计算机管理控制程序'), ('12','0','3','1','计算机管理控制程序','/a/gscp/12.html','','','','2015-05-23 14:42:01',NULL,NULL,'','0','0','计算机管理控制程序计算机管理控制程序'), ('13','0','3','1','计算机管理控制程序','/a/gscp/13.html','','','','2015-05-23 14:42:01',NULL,NULL,'','0','0','计算机管理控制程序计算机管理控制程序'), ('14','0','3','1','计算机管理控制程序','/a/gscp/14.html','','','','2015-05-23 14:42:01',NULL,NULL,'','0','0','计算机管理控制程序计算机管理控制程序'), ('15','0','3','1','计算机管理控制程序','/a/gscp/15.html','','','','2015-05-23 14:42:01',NULL,NULL,'','0','0','计算机管理控制程序计算机管理控制程序'), ('16','0','3','1','计算机管理控制程序','/a/gscp/16.html','','','','2015-05-23 14:42:01',NULL,NULL,'','0','0','计算机管理控制程序计算机管理控制程序'), ('17','0','3','1','计算机管理控制程序','/a/gscp/17.html','','','','2015-05-23 14:42:01',NULL,NULL,'','0','0','计算机管理控制程序计算机管理控制程序'), ('18','0','3','1','计算机管理控制程序','/a/gscp/18.html','','','','2015-05-23 14:42:01',NULL,NULL,'','0','0','计算机管理控制程序计算机管理控制程序'), ('19','0','3','1','计算机管理控制程序','/a/gscp/19.html','','','','2015-05-23 14:42:01',NULL,NULL,'','0','0','计算机管理控制程序计算机管理控制程序'), ('20','0','3','1','计算机管理控制程序','/a/gscp/20.html','','','','2015-05-23 14:42:01',NULL,NULL,'','0','0','计算机管理控制程序计算机管理控制程序'), ('21','0','3','1','计算机管理控制程序','/a/gscp/21.html','','','','2015-05-23 14:42:01',NULL,NULL,'','0','0','计算机管理控制程序计算机管理控制程序'), ('22','0','3','1','计算机管理控制程序','/a/gscp/22.html','','','','2015-05-23 14:42:01',NULL,NULL,'','0','0','计算机管理控制程序计算机管理控制程序'), ('23','0','3','1','计算机管理控制程序','/a/gscp/23.html','','','','2015-05-23 14:42:01',NULL,NULL,'','0','0','计算机管理控制程序计算机管理控制程序'), ('24','0','3','1','计算机管理控制程序','/a/gscp/24.html','','','','2015-05-23 14:42:01',NULL,NULL,'','0','0','计算机管理控制程序计算机管理控制程序'), ('25','0','3','1','计算机管理控制程序','/a/gscp/25.html','','','','2015-05-23 14:42:01',NULL,NULL,'','0','0','计算机管理控制程序计算机管理控制程序'), ('26','0','3','1','计算机管理控制程序','/a/gscp/26.html','','','','2015-05-23 14:42:01',NULL,NULL,'','0','0','计算机管理控制程序计算机管理控制程序'), ('27','0','3','1','计算机管理控制程序','/a/gscp/27.html','','','','2015-05-23 14:42:01',NULL,NULL,'','0','0','计算机管理控制程序计算机管理控制程序'), ('28','0','3','1','计算机管理控制程序','/a/gscp/28.html','','','','2015-05-23 14:42:01',NULL,NULL,'','0','0','计算机管理控制程序计算机管理控制程序'), ('29','0','3','1','计算机管理控制程序','/a/gscp/29.html','','','','2015-05-23 14:42:01',NULL,NULL,'','0','0','计算机管理控制程序计算机管理控制程序'), ('30','0','3','1','计算机管理控制程序','/a/gscp/30.html','','','','2015-05-23 14:42:01',NULL,NULL,'','0','0','计算机管理控制程序计算机管理控制程序'), ('31','0','3','1','计算机管理控制程序','/a/gscp/31.html','','','','2015-05-23 14:42:01',NULL,NULL,'','0','0','计算机管理控制程序计算机管理控制程序'), ('32','0','3','1','计算机管理控制程序','/a/gscp/32.html','','','','2015-05-23 14:42:01',NULL,NULL,'','0','0','计算机管理控制程序计算机管理控制程序'), ('33','0','3','1','计算机管理控制程序','/a/gscp/33.html','','','','2015-05-23 14:42:01',NULL,NULL,'','0','0','计算机管理控制程序计算机管理控制程序'), ('34','0','3','1','计算机管理控制程序','/a/gscp/34.html','','','','2015-05-23 14:42:01',NULL,NULL,'','0','0','计算机管理控制程序计算机管理控制程序'), ('35','0','3','1','计算机管理控制程序','/a/gscp/35.html','','','','2015-05-23 14:42:01',NULL,NULL,'','0','0','计算机管理控制程序计算机管理控制程序'), ('36','0','3','1','计算机管理控制程序','/a/gscp/36.html','','','','2015-05-23 14:42:01',NULL,NULL,'','0','0','计算机管理控制程序计算机管理控制程序'), ('37','0','3','1','计算机管理控制程序','/a/gscp/37.html','','','','2015-05-23 14:42:01',NULL,NULL,'','0','0','计算机管理控制程序计算机管理控制程序'), ('38','0','3','1','计算机管理控制程序','/a/gscp/38.html','','','','2015-05-23 14:42:01',NULL,NULL,'','0','0','计算机管理控制程序计算机管理控制程序'), ('39','0','3','1','计算机管理控制程序','/a/gscp/39.html','','','','2015-05-23 14:42:01',NULL,NULL,'','0','0','计算机管理控制程序计算机管理控制程序'), ('40','0','3','1','计算机管理控制程序','/a/gscp/40.html','','','','2015-05-23 14:42:01',NULL,NULL,'','0','0','计算机管理控制程序计算机管理控制程序'), ('41','0','3','1','计算机管理控制程序','/a/gscp/41.html','','','','2015-05-23 14:42:01',NULL,NULL,'','0','0','计算机管理控制程序计算机管理控制程序'), ('42','0','3','1','计算机管理控制程序','/a/gscp/42.html','','','','2015-05-23 14:42:01',NULL,NULL,'','0','0','计算机管理控制程序计算机管理控制程序'), ('43','0','3','1','计算机管理控制程序','/a/gscp/43.html','','','','2015-05-23 14:42:01',NULL,NULL,'','0','0','计算机管理控制程序计算机管理控制程序'), ('44','0','3','1','计算机管理控制程序','/a/gscp/44.html','','','','2015-05-23 14:42:01',NULL,NULL,'','0','0','计算机管理控制程序计算机管理控制程序'), ('45','0','3','1','计算机管理控制程序','/a/gscp/45.html','','','','2015-05-23 14:42:01',NULL,NULL,'','0','0','计算机管理控制程序计算机管理控制程序'), ('46','0','3','1','计算机管理控制程序','/a/gscp/46.html','','','','2015-05-23 14:42:01',NULL,NULL,'','0','0','计算机管理控制程序计算机管理控制程序'), ('47','0','3','1','计算机管理控制程序','/a/gscp/47.html','','','','2015-05-23 14:42:01',NULL,NULL,'','0','0','计算机管理控制程序计算机管理控制程序'), ('48','0','3','1','计算机管理控制程序','/a/gscp/48.html','','','','2015-05-23 14:42:01',NULL,NULL,'','0','0','计算机管理控制程序计算机管理控制程序'), ('49','0','3','1','计算机管理控制程序','/a/gscp/49.html','','','','2015-05-23 14:42:01',NULL,NULL,'','0','0','计算机管理控制程序计算机管理控制程序'), ('50','0','3','1','计算机管理控制程序','/a/gscp/50.html','','','','2015-05-23 14:42:01',NULL,NULL,'','0','0','计算机管理控制程序计算机管理控制程序'), ('51','0','5','1','wefwe','/a/qyal/51.html','dsfsd','','sdfsdf','2015-09-02 14:18:03','','','','0','0','');
INSERT INTO `ds_cms_site` VALUES ('0','adminadmin','默认','site','/DsEp/html/site/html',NULL,'index.jsp'), ('1','ep100000','单位','site1','/DsEp/html/site1/html',NULL,'index.jsp'), ('2','ep100000','test站点','test','/DsEp/html/test/html',NULL,'index.jsp');
INSERT INTO `ds_common_dict` VALUES ('1','SSXQ','行政区划','1','1'), ('1399866061225','XL','学历','1','0'), ('1399866098941','XW','学位','0','0'), ('1399948990536','ztree','myztree不能删除','1','0');
INSERT INTO `ds_common_dict_data` VALUES ('44',NULL,'SSXQ','广东省','440000','1','5',''), ('4401','44','SSXQ','广州市','440100','1','2',''), ('4402','44','SSXQ','韶关市','440200','1','3',''), ('4403','44','SSXQ','深圳市','440300','1','4',''), ('4404','44','SSXQ','珠海市','440400','1','5',''), ('4405','44','SSXQ','汕头市','440500','1','6',''), ('4406','44','SSXQ','佛山市','440600','1','7',''), ('4407','44','SSXQ','江门市','440700','1','8',''), ('4408','44','SSXQ','湛江市','440800','1','9',''), ('4409','44','SSXQ','茂名市','440900','1','10',''), ('4412','44','SSXQ','肇庆市','441200','1','11',''), ('4413','44','SSXQ','惠州市','441300','1','12',''), ('4414','44','SSXQ','梅州市','441400','1','13',''), ('4415','44','SSXQ','汕尾市','441500','1','14',''), ('4416','44','SSXQ','河源市','441600','1','15',''), ('4417','44','SSXQ','阳江市','441700','1','16',''), ('4418','44','SSXQ','清远市','441800','1','17',''), ('4419','44','SSXQ','东莞市','441900','1','18',''), ('4420','44','SSXQ','中山市','442000','1','19',''), ('4451','44','SSXQ','潮州市','445100','1','20',''), ('4452','44','SSXQ','揭阳市','445200','1','21',''), ('4453','44','SSXQ','云浮市','445300','1','22',''), ('440101','4401','SSXQ','市辖区','440101','1','440101',''), ('440103','4401','SSXQ','荔湾区','440103','1','440103',''), ('440104','4401','SSXQ','越秀区','440104','1','440104',''), ('440105','4401','SSXQ','海珠区','440105','1','440105',''), ('440106','4401','SSXQ','天河区','440106','1','440106',''), ('440111','4401','SSXQ','白云区','440111','1','440111',''), ('440112','4401','SSXQ','黄埔区','440112','1','440112',''), ('440113','4401','SSXQ','番禺区','440113','1','440113',''), ('440114','4401','SSXQ','花都区','440114','1','440114',''), ('440115','4401','SSXQ','南沙区','440115','1','440115',''), ('440116','4401','SSXQ','萝岗区','440116','1','440116',''), ('440183','4401','SSXQ','增城市','440183','1','440183',''), ('440184','4401','SSXQ','从化市','440184','1','440184',''), ('440201','4402','SSXQ','市辖区','440201','1','440201',''), ('440203','4402','SSXQ','武江区','440203','1','440203',''), ('440204','4402','SSXQ','浈江区','440204','1','440204',''), ('440205','4402','SSXQ','曲江区','440205','1','440205',''), ('440222','4402','SSXQ','始兴县','440222','1','440222',''), ('440224','4402','SSXQ','仁化县','440224','1','440224',''), ('440229','4402','SSXQ','翁源县','440229','1','440229',''), ('440232','4402','SSXQ','乳源瑶族自治县','440232','1','440232',''), ('440233','4402','SSXQ','新丰县','440233','1','440233',''), ('440281','4402','SSXQ','乐昌市','440281','1','440281',''), ('440282','4402','SSXQ','南雄市','440282','1','440282',''), ('440301','4403','SSXQ','市辖区','440301','1','440301',''), ('440303','4403','SSXQ','罗湖区','440303','1','440303',''), ('440304','4403','SSXQ','福田区','440304','1','440304',''), ('440305','4403','SSXQ','南山区','440305','1','440305',''), ('440306','4403','SSXQ','宝安区','440306','1','440306',''), ('440307','4403','SSXQ','龙岗区','440307','1','440307',''), ('440308','4403','SSXQ','盐田区','440308','1','440308',''), ('440401','4404','SSXQ','市辖区','440401','1','440401',''), ('440402','4404','SSXQ','香洲区','440402','1','440402',''), ('440403','4404','SSXQ','斗门区','440403','1','440403',''), ('440404','4404','SSXQ','金湾区','440404','1','440404',''), ('440501','4405','SSXQ','市辖区','440501','1','440501',''), ('440507','4405','SSXQ','龙湖区','440507','1','440507',''), ('440511','4405','SSXQ','金平区','440511','1','440511',''), ('440512','4405','SSXQ','濠江区','440512','1','440512',''), ('440513','4405','SSXQ','潮阳区','440513','1','440513',''), ('440514','4405','SSXQ','潮南区','440514','1','440514',''), ('440515','4405','SSXQ','澄海区','440515','1','440515',''), ('440523','4405','SSXQ','南澳县','440523','1','440523',''), ('440601','4406','SSXQ','市辖区','440601','1','440601',''), ('440604','4406','SSXQ','禅城区','440604','1','440604',''), ('440605','4406','SSXQ','南海区','440605','1','440605',''), ('440606','4406','SSXQ','顺德区','440606','1','440606',''), ('440607','4406','SSXQ','三水区','440607','1','440607',''), ('440608','4406','SSXQ','高明区','440608','1','440608',''), ('440701','4407','SSXQ','市辖区','440701','1','440701',''), ('440703','4407','SSXQ','蓬江区','440703','1','440703',''), ('440704','4407','SSXQ','江海区','440704','1','440704',''), ('440705','4407','SSXQ','新会区','440705','1','440705',''), ('440781','4407','SSXQ','台山市','440781','1','440781',''), ('440783','4407','SSXQ','开平市','440783','1','440783',''), ('440784','4407','SSXQ','鹤山市','440784','1','440784',''), ('440785','4407','SSXQ','恩平市','440785','1','440785',''), ('440801','4408','SSXQ','市辖区','440801','1','440801',''), ('440802','4408','SSXQ','赤坎区','440802','1','440802',''), ('440803','4408','SSXQ','霞山区','440803','1','440803',''), ('440804','4408','SSXQ','坡头区','440804','1','440804',''), ('440811','4408','SSXQ','麻章区','440811','1','440811',''), ('440823','4408','SSXQ','遂溪县','440823','1','440823',''), ('440825','4408','SSXQ','徐闻县','440825','1','440825',''), ('440881','4408','SSXQ','廉江市','440881','1','440881',''), ('440882','4408','SSXQ','雷州市','440882','1','440882',''), ('440883','4408','SSXQ','吴川市','440883','1','440883',''), ('440901','4409','SSXQ','市辖区','440901','1','440901',''), ('440902','4409','SSXQ','茂南区','440902','1','440902',''), ('440903','4409','SSXQ','茂港区','440903','1','440903',''), ('440923','4409','SSXQ','电白县','440923','1','440923',''), ('440981','4409','SSXQ','高州市','440981','1','440981',''), ('440982','4409','SSXQ','化州市','440982','1','440982',''), ('440983','4409','SSXQ','信宜市','440983','1','440983',''), ('441201','4412','SSXQ','市辖区','441201','1','441201',''), ('441202','4412','SSXQ','端州区','441202','1','441202',''), ('441203','4412','SSXQ','鼎湖区','441203','1','441203',''), ('441223','4412','SSXQ','广宁县','441223','1','441223','');
INSERT INTO `ds_common_dict_data` VALUES ('441224','4412','SSXQ','怀集县','441224','1','441224',''), ('441225','4412','SSXQ','封开县','441225','1','441225',''), ('441226','4412','SSXQ','德庆县','441226','1','441226',''), ('441283','4412','SSXQ','高要市','441283','1','441283',''), ('441284','4412','SSXQ','四会市','441284','1','441284',''), ('441301','4413','SSXQ','市辖区','441301','1','441301',''), ('441302','4413','SSXQ','惠城区','441302','1','441302',''), ('441303','4413','SSXQ','惠阳区','441303','1','441303',''), ('441322','4413','SSXQ','博罗县','441322','1','441322',''), ('441323','4413','SSXQ','惠东县','441323','1','441323',''), ('441324','4413','SSXQ','龙门县','441324','1','441324',''), ('441401','4414','SSXQ','市辖区','441401','1','441401',''), ('441402','4414','SSXQ','梅江区','441402','1','441402',''), ('441421','4414','SSXQ','梅县','441421','1','441421',''), ('441422','4414','SSXQ','大埔县','441422','1','441422',''), ('441423','4414','SSXQ','丰顺县','441423','1','441423',''), ('441424','4414','SSXQ','五华县','441424','1','441424',''), ('441426','4414','SSXQ','平远县','441426','1','441426',''), ('441427','4414','SSXQ','蕉岭县','441427','1','441427',''), ('441481','4414','SSXQ','兴宁市','441481','1','441481',''), ('441501','4415','SSXQ','市辖区','441501','1','441501',''), ('441502','4415','SSXQ','城区','441502','1','441502',''), ('441521','4415','SSXQ','海丰县','441521','1','441521',''), ('441523','4415','SSXQ','陆河县','441523','1','441523',''), ('441581','4415','SSXQ','陆丰市','441581','1','441581',''), ('441601','4416','SSXQ','市辖区','441601','1','441601',''), ('441602','4416','SSXQ','源城区','441602','1','441602',''), ('441621','4416','SSXQ','紫金县','441621','1','441621',''), ('441622','4416','SSXQ','龙川县','441622','1','441622',''), ('441623','4416','SSXQ','连平县','441623','1','441623',''), ('441624','4416','SSXQ','和平县','441624','1','441624',''), ('441625','4416','SSXQ','东源县','441625','1','441625',''), ('441701','4417','SSXQ','市辖区','441701','1','441701',''), ('441702','4417','SSXQ','江城区','441702','1','441702',''), ('441721','4417','SSXQ','阳西县','441721','1','441721',''), ('441723','4417','SSXQ','阳东县','441723','1','441723',''), ('441781','4417','SSXQ','阳春市','441781','1','441781',''), ('441801','4418','SSXQ','市辖区','441801','1','441801',''), ('441802','4418','SSXQ','清城区','441802','1','441802',''), ('441821','4418','SSXQ','佛冈县','441821','1','441821',''), ('441823','4418','SSXQ','阳山县','441823','1','441823',''), ('441825','4418','SSXQ','连山壮族瑶族自治县','441825','1','441825',''), ('441826','4418','SSXQ','连南瑶族自治县','441826','1','441826',''), ('441827','4418','SSXQ','清新县','441827','1','441827',''), ('441881','4418','SSXQ','英德市','441881','1','441881',''), ('441882','4418','SSXQ','连州市','441882','1','441882',''), ('445101','4451','SSXQ','市辖区','445101','1','445101',''), ('445102','4451','SSXQ','湘桥区','445102','1','445102',''), ('445121','4451','SSXQ','潮安县','445121','1','445121',''), ('445122','4451','SSXQ','饶平县','445122','1','445122',''), ('445201','4452','SSXQ','市辖区','445201','1','445201',''), ('445202','4452','SSXQ','榕城区','445202','1','445202',''), ('445221','4452','SSXQ','揭东县','445221','1','445221',''), ('445222','4452','SSXQ','揭西县','445222','1','445222',''), ('445224','4452','SSXQ','惠来县','445224','1','445224',''), ('445281','4452','SSXQ','普宁市','445281','1','445281',''), ('445301','4453','SSXQ','市辖区','445301','1','445301',''), ('445302','4453','SSXQ','云城区','445302','1','445302',''), ('445321','4453','SSXQ','新兴县','445321','1','445321',''), ('445322','4453','SSXQ','郁南县','445322','1','445322',''), ('445323','4453','SSXQ','云安县','445323','1','445323',''), ('445381','4453','SSXQ','罗定市','445381','1','445381',''), ('1399945251466',NULL,'XW','学士','1','1','0',''), ('1399945251904',NULL,'XW','硕士','2','1','0',''), ('1399953375155',NULL,'ssss','1','1','1','0',''), ('1399957753954',NULL,'bw','vv','vv','1','0',''), ('1399960275681',NULL,'ssssv','we','we','1','0',''), ('1400120829995',NULL,'ztree','树1','tree1','1','2',''), ('1400120830090',NULL,'ztree','树2','tree2','1','3',''), ('1400120830137',NULL,'ztree','树3','tree3','1','4',''), ('1400120830183',NULL,'ztree','树4','tree4','1','5',''), ('1400120866569','1400120829995','ztree','树11','tree11','1','1',''), ('1400120866648','1400120829995','ztree','树12','tree12','1','2',''), ('1400120866695','1400120829995','ztree','树13','tree13','1','3',''), ('1400120894543','1400120830090','ztree','树21','tree21','1','1',''), ('1400120894622','1400120830090','ztree','树22','tree22','1','2',''), ('1400120894762','1400120830090','ztree','树23','tree23','1','3',''), ('1400120894825','1400120830090','ztree','树24','tree24','0','4',''), ('1400120924002','1400120866569','ztree','树111','tree111','1','1',''), ('1400120924081','1400120866569','ztree','树112','tree112','1','2',''), ('1400120924112','1400120866569','ztree','树113','tree113','1','3',''), ('1400120948404','1400120866695','ztree','树131','tree131','1','1',''), ('1400120948468','1400120866695','ztree','树132','tree132','1','2',''), ('1400120973280','1400120830137','ztree','树31','tree31','1','1',''), ('1400120973344','1400120830137','ztree','树32','tree32','1','2',''), ('1400120991521','1400120830183','ztree','tree41','tree41','1','1',''), ('1400120991584','1400120830183','ztree','tree42','tree42','1','2',''), ('1400121023290','1400120991521','ztree','tree411','tree411','1','1',''), ('1400121023415','1400120991521','ztree','tree412','tree412','1','2',''), ('1400121056578','1400121023290','ztree','tree4111','tree4111','1','1',''), ('1400121056657','1400121023290','ztree','tree4112','tree4112','1','2',''), ('1400121056704','1400121023290','ztree','tree4113','tree4113','1','3',''), ('1400405261470',NULL,'ztree','a\\/\"','a','1','1',''), ('1400412300400','1400412317252','ztree','e','e','1','0',''), ('1400412317252','1400405261470','ztree','ggg','ggg','1','0',''), ('1400412330859','1400412317252','ztree','dss','dss','1','0',''), ('1400412346100','1400412330859','ztree','f','f','1','0','');
INSERT INTO `ds_common_func` VALUES ('1',NULL,'1','门户管理','','#','','1','1','',''), ('2',NULL,'1','业务管理','','#','','1','2','',''), ('3',NULL,'1','用户管理','','#','','1','3','',''), ('11','1','1','系统管理','','/common/system/getSystem.htm','','1','0','',''), ('12','1','1','组织管理','','/common/org/getOrgTree.htm','','1','0','',''), ('13','1','1','岗位授权管理','','/common/orgrole/getOrgTree.htm','','1','0','',''), ('21','2','1','字典管理','','/common/dict/getDict.htm','','1','0','',''), ('22','2','1','流程管理','','/common/flow/getFlowCategoryTree.htm','','1','0','',''), ('31','3','1','用户管理','','/common/user/getUser.htm','','1','0','',''), ('32','3','1','用户授权管理','','/common/userorg/getOrgTree.htm','','1','0','',''), ('33',NULL,'10','待办事项','','#','','1','2','','http://192.168.1.55:8080/zhly/manage/flow/waiting.htm|'), ('34','33','10','待办事项','','/manage/flow/waiting.htm','','1','7','',''), ('35',NULL,'10','系统管理','','#','','1','1','',''), ('36','35','10','评分标准','','/common/standard/getStandardList.htm','','1','0','',''), ('37','35','10','流程管理','','/common/flow/getFlowCategoryTree.htm','','1','0','',''), ('38','35','10','字典管理','','/common/dict/getDict.htm','','1','0','',''), ('39','33','10','星级划分评定','','/manage/hotel/info/getHotelInfoXp.htm','','1','6','',''), ('40','33','10','已批复列表','','/manage/hotel/info/getHotelInfoPf.htm','','1','5','',''), ('41','33','10','未通过列表','','/manage/hotel/info/getHotelInfoBg.htm','','1','4','',''), ('42','33','10','星级饭店通报','','/manage/hotel/punish/getHotelPunish.htm','','1','3','',''), ('43','33','10','饭店安全检查','','/manage/hotel/safeRecond/getHotelSafeRecond.htm','','1','2','',''), ('44','33','10','饭店星级变动情况','','/manage/hotel/changeRecond/getHotelChangeRecond.htm','','1','1','',''), ('45',NULL,'11','旅游服务信息','','#','','1','0','',''), ('46','45','11','旅游服务信息','','/getTravelServiceInfo.jsp','','1','0','',''), ('47','45','11','游客信息展示','','/manage/tourists/getTourists.htm','','1','0','',''), ('48',NULL,'12','检查计划','','#','','1','0','',''), ('49',NULL,'12','检查情况登记','','#','','1','0','',''), ('50',NULL,'12','案件线索','','#','','1','0','',''), ('51','48','12','工作方案','','/manage/lyjc/checkplan/getCheckplan.htm','','1','0','',''), ('52','48','12','检查记录单','','/manage/lyjc/checkrecord/getCheckrecord.htm','','1','0','',''), ('53','49','12','未移送交办','','#','','1','0','',''), ('54','49','12','已移送交办','','#','','1','0','',''), ('55','53','12','旅行社检查情况登记','','/manage/lyjc/scenicspot/getScenicspot.htm','','1','0','',''), ('56','53','12','景区（点）检查情况登记','','/manage/lyjc/scenicspot/getScenicspot.htm','','1','0','',''), ('57','53','12','星级饭店检查情况登记','','/manage/lyjc/restaurant/getRestaurant.htm','','1','0','',''), ('58','53','12','导游人员检查情况登记','','/manage/lyjc/tourguide/getTourguide.htm','','1','0','',''), ('59','54','12','旅行社检查情况','','/manage/lyjc/travelagency/getTravelagency1.htm','','1','0','',''), ('60','54','12','景区（点）检查情况','','/manage/lyjc/scenicspot/getScenicspot1.htm','','1','0','',''), ('61','54','12','星级饭店检查情况','','/manage/lyjc/restaurant/getRestaurant1.htm','','1','0','',''), ('62','54','12','导游人员检查情况','','/manage/lyjc/tourguide/getTourguide1.htm','','1','0','',''), ('63','50','12','案件线索','','/manage/xzzf/ajxs/getAjxs.htm','','1','0','',''), ('64','67','12','处理单','','/manage/xzzf/fksqd/getFksqddf.htm','','1','0','',''), ('65','69','12','处理单','','/manage/xzzf/fksqd/getFksqdtf.htm','','1','0','',''), ('66','68','12','处理单','','/manage/xzzf/fksqd/getFksqdhf.htm','','1','0','',''), ('67','50','12','待办处理单','','#','','1','0','',''), ('68','50','12','打回受理单','','#','','1','0','',''), ('69','50','12','已通过受理单','','#','','1','0','',''), ('70','50','12','处理单','','/manage/xzzf/fksqd/getFksqd.htm','','1','0','',''), ('71','67','12','处理单','','/manage/xzzf/fksqd/getFksqddj.htm','','1','0','',''), ('72','68','12','处理单','','/manage/xzzf/fksqd/getFksqdhj.htm','','1','0','',''), ('73','69','12','处理单','','/manage/xzzf/fksqd/getFksqdtj.htm','','1','0','',''), ('74',NULL,'0','企业管理','','#','','1','0','',''), ('75','74','0','企业信息','','/manage/by/qyk/getQyk.htm','','1','0','',''), ('76',NULL,'9','审核内容','','http://127.0.0.1','','1','0','',''), ('77','76','9','成功案例-企业案例','qyal','','','0','0','','qyal|');
INSERT INTO `ds_common_login` VALUES ('1','2015-11-24 11:13:06','0',NULL,NULL,'fa9783a4-1a64-4543-9af2-ade9c62e6db11448334786086','127.0.0.1','admin','超级管理员','1'), ('2','2015-11-24 13:14:09','0',NULL,NULL,'c2c5e84b-315d-42a2-8d36-8499db38736e1448342049878','127.0.0.1','admin','超级管理员','1'), ('3','2015-11-26 14:15:08','0',NULL,NULL,'9b795b8c-8382-4f1f-8862-d6bbee0c7b5c1448518508096','127.0.0.1','admin','超级管理员','1'), ('4','2015-11-26 14:15:54','0',NULL,NULL,'1f220e95-1d59-472d-b4be-501360207db11448518554154','127.0.0.1','admin','超级管理员','1'), ('5','2015-11-26 14:17:38','0',NULL,NULL,'3f004661-edcf-4af1-bcd7-e04e7cc89ce41448518658092','127.0.0.1','admin','超级管理员','1'), ('6','2015-11-26 16:39:14','0',NULL,NULL,'1d30b7b2-fcb1-4962-a1c8-bfb8eedec8b11448527154647','127.0.0.1','admin','超级管理员','1'), ('7','2015-11-26 16:42:56','0',NULL,NULL,'1f586419-c760-4e66-9032-2628dc2718b61448527376480','127.0.0.1','admin','超级管理员','1'), ('8','2015-11-26 17:43:00','0',NULL,NULL,'62f6aecd-9f65-47b6-a07a-3d0388787e781448530980434','127.0.0.1','admin','超级管理员','1');
INSERT INTO `ds_common_org` VALUES ('1',NULL,'系统管理组','2','100000','广东省',''), ('2','1','系统管理部门','1','0','广州市',''), ('3','1','测试管理组','2','0','汕尾市',''), ('4','2','系统管理岗位','0','0','',''), ('5','3','广州部门','1','0','汕尾城区',''), ('6','5','天河分部','1','0','海丰县',''), ('8','5','白云分部','1','0','',''), ('10',NULL,'广州市民政局','2','10','',''), ('11','10','广州市老龄工作委员会办公室','2','0','',''), ('12','10','广州市殡葬管理处','2','0','',''), ('13','10','广州市殡葬服务中心','2','0','',''), ('14','10','广州市社会福利企业管理办公室','2','0','',''), ('15','10','广州市救助管理站','2','0','',''), ('16','10','广州市救助管理站市区分站','2','0','',''), ('17','10','广州市老人院','2','0','',''), ('18','10','广州市社会（儿童）福利院','2','0','',''), ('19','10','广州市民政局精神病院','2','0','',''), ('20','10','广州市东升医院','2','0','',''), ('21','10','广州市社区服务中心','2','0','',''), ('22','10','广州老年人服务中心','2','0','',''), ('23','10','广州市慈善服务中心','2','0','',''), ('24','10','广州市福利彩票发行中心','2','0','',''), ('25','10','广州市银河烈士陵园管理处','2','0','',''), ('26','10','广州十九路军淞沪抗日阵亡将士陵园管理处','2','0','',''), ('27','10','广州市烈军属疗养院','2','0','',''), ('28','10','广州市天河军用供应站','2','0','',''), ('29','10','广州市黄埔军用供应站','2','0','',''), ('30','10','广州市军队离休退休干部休养所','2','0','',''), ('31','10','广州市民政局','2','0','',''), ('32','10','广州市民政局房屋管理所','2','0','',''), ('33','10','广州市民政局幼儿园','2','0','',''), ('34','10','广州市接收社会捐赠工作站','2','0','',''), ('35','10','广州市医疗救助服务中心','2','0','',''), ('36','10','广州市居民家庭经济状况核对中心','2','0','',''), ('38','6','区局岗位','0','0','',''), ('40','8','区局岗位','0','0','',''), ('41','3','汕头测试','1','0','',''), ('42','41','旅游岗位测试','0','0','',''), ('43',NULL,'广州省旅游局','2','0','',''), ('44',NULL,'广州市食品药品监督管理局','2','0','',''), ('45',NULL,'广州市工商行政管理局','2','0','',''), ('46','43','广州市旅游局','2','0','',''), ('47','43','办公室','1','0','',''), ('48','43','质量规范与管理处','1','0','',''), ('49','43','资源与市场开发处','1','0','',''), ('50','43','人事教育处','1','0','',''), ('51','43','广东省旅游发展研究中心','1','0','',''), ('52','43','省旅游质量监督管理所','1','0','',''), ('53','43','省旅游服务中心','1','0','',''), ('54','43','机关后勤保障中心','1','0','',''), ('55','46','办公室','1','0','',''), ('56','46','规划发展处','1','0','',''), ('57','46','法规与统计处','1','0','',''), ('58','46','市场推广处','1','0','',''), ('59','46','资源开发处','1','0','',''), ('60','46','旅游饭店管理处','1','0','',''), ('61','46','旅行社管理处','1','0','',''), ('62','46','行业培训指导处','1','0','',''), ('63','46','组织人事处 （与机关党委办公室合署）','1','0','',''), ('64','46','离退休干部工作处','1','0','',''), ('65',NULL,'广东省版权保护联合会','2','0','',''), ('67',NULL,'广东省气象局','2','0','',''), ('68',NULL,'广东省新闻出版局','2','0','',''), ('69',NULL,'广州市城市管理委员会','2','0','',''), ('70',NULL,'广州市环境保护局','2','0','',''), ('71',NULL,'广州市林业和园林局','2','0','',''), ('72',NULL,'广州市人事人才信息资源中心','2','0','',''), ('73',NULL,'广州市教育局','2','0','',''), ('74','73','广州市白云区教育局','2','0','',''), ('75','46','局长办公室','1','0','',''), ('76','46','执法部门','1','0','',''), ('77','76','科员','0','0','',''), ('78','76','科长','0','0','',''), ('79','75','局长','0','0','',''), ('80','75','副局长','0','0','',''), ('81','57','科长','0','0','',''), ('82','57','科员','0','0','','');
INSERT INTO `ds_common_orgrole` VALUES ('21','8','2'), ('23','8','3'), ('25','4','2'), ('27','40','5'), ('28','40','2'), ('31','38','3'), ('32','38','7'), ('33','38','8'), ('46','42','2'), ('47','42','3'), ('48','42','5'), ('49','42','8'), ('50','42','9'), ('51','81','11'), ('52','77','10'), ('53','79','12'), ('54','78','11');
INSERT INTO `ds_common_role` VALUES ('2',NULL,'1','超级管理员','0',''), ('3',NULL,'1','门户管理','0',''), ('5',NULL,'2','测试菜单','0',''), ('6',NULL,'1','业务管理','0',''), ('7',NULL,'1','用户管理','0',''), ('8',NULL,'10','市旅游管理员','0',''), ('9',NULL,'11','游客服务管理员','0',''), ('10',NULL,'12','一般','0',''), ('11',NULL,'12','法规处','0',''), ('12',NULL,'12','局长办公室','0',''), ('13',NULL,'0','超级管理员','0',''), ('14',NULL,'9','成功案例-企业案例','0','');
INSERT INTO `ds_common_rolefunc` VALUES ('15','1','3','1'), ('16','1','3','11'), ('17','1','3','12'), ('18','1','3','13'), ('19','1','6','2'), ('20','1','6','21'), ('21','1','6','22'), ('22','1','7','3'), ('23','1','7','31'), ('24','1','7','32'), ('25','1','2','1'), ('26','1','2','11'), ('27','1','2','12'), ('28','1','2','13'), ('29','1','2','2'), ('30','1','2','21'), ('31','1','2','22'), ('32','1','2','3'), ('33','1','2','31'), ('34','1','2','32'), ('59','10','8','35'), ('60','10','8','36'), ('61','10','8','37'), ('62','10','8','38'), ('63','10','8','33'), ('64','10','8','34'), ('65','10','8','39'), ('66','10','8','40'), ('67','10','8','41'), ('68','10','8','42'), ('69','10','8','43'), ('70','10','8','44'), ('71','11','9','45'), ('72','11','9','46'), ('73','11','9','47'), ('116','12','10','50'), ('117','12','10','63'), ('118','12','10','70'), ('119','12','11','50'), ('120','12','11','67'), ('121','12','11','64'), ('122','12','11','68'), ('123','12','11','66'), ('124','12','11','69'), ('125','12','11','65'), ('126','12','12','50'), ('127','12','12','67'), ('128','12','12','71'), ('129','12','12','68'), ('130','12','12','72'), ('131','12','12','69'), ('132','12','12','73'), ('133','0','13','74'), ('134','0','13','75'), ('135','9','14','76'), ('136','9','14','77');
INSERT INTO `ds_common_system` VALUES ('0','测试单机系统',NULL,NULL,NULL,NULL,NULL,'1',NULL,NULL), ('1','统一认证平台','DsCommon','1','http://127.0.0.1:8080','/DsCommon','/DsCommon/menu.jsp','1',NULL,''), ('2','门户','portal','1','http://127.0.0.1:8080','/portal','/portal/menu.jsp','1',NULL,''), ('3','cas-client','sso-client','1','http://127.0.0.1:8080','/sso-client','/sso-client/menu.jsp','1',NULL,NULL), ('4','dd','dd',NULL,NULL,NULL,NULL,'1',NULL,NULL), ('5','ee','ee',NULL,NULL,NULL,NULL,'0',NULL,NULL), ('6','ff','ff',NULL,NULL,NULL,NULL,'1',NULL,NULL), ('7','gg','gg',NULL,NULL,NULL,NULL,'1',NULL,NULL), ('8','hh','hh',NULL,NULL,NULL,NULL,'0',NULL,NULL), ('9','ii','ii',NULL,NULL,NULL,NULL,'1',NULL,NULL), ('10','社会服务管理系统','zhly','1','http://192.168.1.55:8080','/zhly','/zhly/menu.jsp','1',NULL,''), ('11','游客服务管理系统','tourists','1','http://192.168.1.55:8080','/zhly','/zhly/menu1.jsp','1',NULL,''), ('12','旅游检查','lyjc','1','http://192.168.1.33:8080','/lyjc','/lyjc/menu.jsp','1',NULL,'');
INSERT INTO `ds_common_user` VALUES ('-1','admin','670B14728AD9902AECBA32E22FA4F6BD','超级管理员',NULL,'1',NULL,NULL,NULL,NULL,NULL,NULL,'1','2'), ('1','user','670B14728AD9902AECBA32E22FA4F6BD','用户','','1','','','','','','2014-06-04 16:13:30','3','6'), ('2','useradmin','670B14728AD9902AECBA32E22FA4F6BD','用户','','1','','','','','','2014-06-04 16:14:06','3','8'), ('3','z1','670B14728AD9902AECBA32E22FA4F6BD','张一','','0','','','','','','2014-06-10 11:18:27','3','6'), ('4','z12','670B14728AD9902AECBA32E22FA4F6BD','张二','','1','','','','','','2014-06-10 11:18:39','3','6'), ('5','z3','670B14728AD9902AECBA32E22FA4F6BD','张三','','0','','','','','','2014-06-10 11:18:50','3','6'), ('6','h1','670B14728AD9902AECBA32E22FA4F6BD','黄一','','1','','','','','','2014-06-10 11:19:25','2','4'), ('7','h2','670B14728AD9902AECBA32E22FA4F6BD','黄二','','1','','','','','','2014-06-10 11:19:33','2','4'), ('8','h3','670B14728AD9902AECBA32E22FA4F6BD','黄三','','1','','','','','','2014-06-10 11:19:44','2','4'), ('9','b1','670B14728AD9902AECBA32E22FA4F6BD','李工','','0','','','','','','2014-06-12 14:14:36','2','5'), ('10','b2','670B14728AD9902AECBA32E22FA4F6BD','李工','','0','','','','','','2014-06-12 14:14:44',NULL,NULL), ('11','b3','670B14728AD9902AECBA32E22FA4F6BD','李工','','0','','','','','','2014-06-12 14:14:50',NULL,NULL), ('12','b4','670B14728AD9902AECBA32E22FA4F6BD','李工','','0','','','','','','2014-06-12 14:15:01',NULL,NULL), ('13','b5','670B14728AD9902AECBA32E22FA4F6BD','李工','','0','','','','','','2014-06-12 14:15:07',NULL,NULL), ('14','b6','670B14728AD9902AECBA32E22FA4F6BD','李工','','0','','','','','','2014-06-12 14:15:13',NULL,NULL), ('15','b7','670B14728AD9902AECBA32E22FA4F6BD','李工','','0','','','','','','2014-06-12 14:15:20',NULL,NULL), ('16','b8','670B14728AD9902AECBA32E22FA4F6BD','李工','','0','','','','','','2014-06-12 14:15:27',NULL,NULL), ('17','b9','670B14728AD9902AECBA32E22FA4F6BD','李工','','0','','','','','','2014-06-12 14:15:36',NULL,NULL), ('18','b10','670B14728AD9902AECBA32E22FA4F6BD','李工','','0','','','','','','2014-06-12 14:15:44',NULL,NULL), ('19','b11','670B14728AD9902AECBA32E22FA4F6BD','李工','','0','','','','','','2014-06-12 14:15:50',NULL,NULL), ('20','b12','670B14728AD9902AECBA32E22FA4F6BD','李工','','0','','','','','','2014-06-12 14:15:56',NULL,NULL), ('21','sille','670B14728AD9902AECBA32E22FA4F6BD','曹良','','1','','','','','','2015-01-16 13:07:39','3','41'), ('22','jzbgs','C4CA4238A0B923820DCC509A6F75849B','赵瑟','','1','','','','','','2015-01-22 11:13:26','46','75'), ('23','fgc','C4CA4238A0B923820DCC509A6F75849B','孙畅','','1','','','','','','2015-01-22 11:14:17','46','57'), ('24','wxd','C4CA4238A0B923820DCC509A6F75849B','王晓东','','1','','','','','','2015-01-22 11:15:25','46','76'), ('25','jskey','670B14728AD9902AECBA32E22FA4F6BD','中文名','','0','','','','','','2015-02-04 16:49:56','3','5');
INSERT INTO `ds_common_userorg` VALUES ('22','8','6'), ('24','38','6'), ('27','8','7'), ('29','8','8'), ('31','38','8'), ('33','38','1'), ('35','4','-1'), ('36','42','21'), ('37','77','24'), ('38','79','22'), ('39','81','23');
INSERT INTO `ds_common_userrole` VALUES ('49','13','2'), ('50','13','25'), ('51','13','3'), ('52','13','23'), ('53','13','22'), ('54','13','5'), ('55','13','6');
INSERT INTO `ds_ep_enterprise` VALUES ('1','屌爆天逗比技术特攻队','','100000','1','有限责任公司');
INSERT INTO `ds_ep_user` VALUES ('1','100000','100000','670B14728AD9902AECBA32E22FA4F6BD','屌爆天逗比技术特攻队','','1','1','paseweb@163.com','13800138000','','','','','','',''), ('2','werwer','werwer','000000','werwer','','0','0','','','','','','2014-12-29 13:49:11','','',''), ('3','23234','23234','000000','3r3','','0','0','','','','','','2014-12-29 13:49:25','','',''), ('4','123456789987654321','123456789987654321','670B14728AD9902AECBA32E22FA4F6BD','他妹的傻逼公司','','1','0','13800138000@139.com','13800138000','','','','2016-03-31 15:58:19',NULL,NULL,'');
INSERT INTO `ds_flow` VALUES ('1','1','tech_duty','0','tech_duty-3','技术部请假流程','1'), ('2','1','tech_duty','1','tech_duty-2','技术部请假流程','1'), ('3','1','tech_duty','1','tech_duty-3','技术部请假流程','1');
INSERT INTO `ds_flow_category` VALUES ('1','请假流程',NULL,'0');
INSERT INTO `ds_flow_pi` VALUES ('12','1000',NULL,'tech_duty','2','tech_duty-2','0','1','2014-10-11 11:34:21',NULL,'','','0','admin','管理员',''), ('13','1000',NULL,'tech_duty','2','tech_duty-2','0','1','2014-10-11 11:34:23',NULL,'','','0','admin','管理员',''), ('14','1000',NULL,'tech_duty','2','tech_duty-2','0','1','2014-10-15 16:07:11',NULL,'','','2','admin','管理员','pass'), ('15','1000',NULL,'tech_duty','2','tech_duty-2','0','1','2014-10-15 16:17:20',NULL,'','','2','admin','管理员','task,user'), ('16','1000',NULL,'tech_duty','2','tech_duty-2','0','1','2014-10-15 16:18:22',NULL,'','','1','admin','管理员','start'), ('17','1000',NULL,'tech_duty','3','tech_duty-3','0','1','2014-10-15 16:28:17',NULL,'','','1','admin','管理员','start'), ('18','1000',NULL,'tech_duty','3','tech_duty-3','0','1','2014-10-15 16:28:28',NULL,'','','2','admin','管理员','task,user'), ('19','1000',NULL,'tech_duty','3','tech_duty-3','0','1','2014-10-16 18:10:26',NULL,'','','2','admin','管理员','task,user');
INSERT INTO `ds_flow_pi_data` VALUES ('22','12','start','开始','0','admin','管理员','2014-10-11 11:34:31','1','无'), ('23','12','task','审核','0','admin','管理员','2014-10-11 11:34:42','1','无'), ('24','12','user','批准','0','admin','管理员','2014-10-11 11:34:48','1','无'), ('25','12','user1','批准1','0','admin','管理员','2014-10-11 11:34:53','1','无'), ('26','13','start','开始','0','admin','管理员','2014-10-11 11:35:01','1','无'), ('27','13','user','批准','0','admin','管理员','2014-10-11 11:35:08','1','无'), ('28','13','user2','批准2','0','admin','管理员','2014-10-11 11:35:14','1','无'), ('29','13','task','审核','0','admin','管理员','2014-10-11 11:35:21','1','无'), ('30','13','pass','办结','0','admin','管理员','2014-10-11 11:35:27','1','无'), ('31','12','pass','办结','0','admin','管理员','2014-10-11 11:35:32','1','无'), ('32','14','start','开始','0','admin','管理员','2014-10-15 16:10:10','1','无'), ('33','14','task','审核','0','admin','管理员','2014-10-15 16:12:21','1','无'), ('34','14','user','批准','0','admin','管理员','2014-10-15 16:13:41','1','无'), ('35','14','user1','批准1','0','admin','管理员','2014-10-15 16:13:47','1','无'), ('36','15','start','开始','0','admin','管理员','2014-10-15 16:20:59','1','无'), ('37','18','start','开始','0','admin','管理员','2014-10-15 16:47:32','1','无'), ('38','19','start','开始','0','admin','管理员','2014-10-16 18:10:42','1','无');
INSERT INTO `ds_flow_pi_waiting` VALUES ('4','14','1000',NULL,'2','技术部请假流程','pass','办结','1','end','2014-10-15 16:13:47',',admin,',NULL,'','1'), ('7','16','1000',NULL,'2','技术部请假流程','start','开始','1','task|user','2014-10-15 16:18:22',NULL,',admin,admin1,admin2,','','1'), ('8','15','1000',NULL,'2','技术部请假流程','task','审核','1','pass','2014-10-15 16:20:59',',admin,',NULL,'','1'), ('9','15','1000',NULL,'2','技术部请假流程','user','批准','1','user1,user2','2014-10-15 16:20:59',',admin,',NULL,'','1'), ('10','17','1000',NULL,'3','技术部请假流程','start','开始','1','task|user','2014-10-15 16:28:17',',admin,',NULL,'','1'), ('12','18','1000',NULL,'3','技术部请假流程','task','审核','1','pass','2014-10-15 16:47:32',',admin,',NULL,'','1'), ('13','18','1000',NULL,'3','技术部请假流程','user','批准','1','user1,user2','2014-10-15 16:47:32',',admin,',NULL,'','1'), ('15','19','1000',NULL,'3','技术部请假流程','task','审核','1','pass','2014-10-16 18:10:42',',admin,',NULL,'','1'), ('16','19','1000',NULL,'3','技术部请假流程','user','批准','1','user1,user2','2014-10-16 18:10:42',',admin,',NULL,'','1');
INSERT INTO `ds_flow_task` VALUES ('25','2','tech_duty-2','start','开始','1','task|user','admin,admin1,admin2',''), ('26','2','tech_duty-2','task','审核','1','pass','admin',''), ('27','2','tech_duty-2','user','批准','1','user1,user2','admin',''), ('28','2','tech_duty-2','pass','办结','2','end','admin',''), ('29','2','tech_duty-2','user1','批准1','1','pass','admin',''), ('30','2','tech_duty-2','user2','批准2','1','pass','admin',''), ('31','2','tech_duty-2','end','结束','1','','',''), ('32','1',NULL,'start','开始','1','task|user','admin',''), ('33','1',NULL,'task','审核','1','pass','admin',''), ('34','1',NULL,'user','批准','1','user1,user2','admin',''), ('35','1',NULL,'pass','办结','2','end','admin',''), ('36','1',NULL,'user1','批准1','1','pass','admin',''), ('37','1',NULL,'user2','批准2','1','pass','admin',''), ('38','1',NULL,'end','结束','1','','',''), ('39','3','tech_duty-3','start','开始','1','task|user','admin',''), ('40','3','tech_duty-3','task','审核','1','pass','admin',''), ('41','3','tech_duty-3','user','批准','1','user1,user2','admin',''), ('42','3','tech_duty-3','pass','办结','2','end','admin',''), ('43','3','tech_duty-3','user1','批准1','1','pass','admin',''), ('44','3','tech_duty-3','user2','批准2','1','pass','admin',''), ('45','3','tech_duty-3','end','结束','1','','','');
INSERT INTO `ds_person_user` VALUES ('1','440111180012310004','admin','670B14728AD9902AECBA32E22FA4F6BD','屌爆天逗比技术特攻队','1',NULL,NULL,NULL,NULL,NULL,'1');
