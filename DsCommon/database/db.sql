/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2014-6-11 11:53:54                           */
/*==============================================================*/


DROP TABLE IF EXISTS DS_COMMON_DICT;

DROP TABLE IF EXISTS DS_COMMON_DICT_DATA;

DROP TABLE IF EXISTS DS_COMMON_FUNC;

DROP TABLE IF EXISTS DS_COMMON_LOGIN;

DROP TABLE IF EXISTS DS_COMMON_ORG;

DROP TABLE IF EXISTS DS_COMMON_ORGROLE;

DROP TABLE IF EXISTS DS_COMMON_PERMISSIBLE;

DROP TABLE IF EXISTS DS_COMMON_ROLE;

DROP TABLE IF EXISTS DS_COMMON_ROLEFUNC;

DROP TABLE IF EXISTS DS_COMMON_SYSTEM;

DROP TABLE IF EXISTS DS_COMMON_USER;

DROP TABLE IF EXISTS DS_COMMON_USERORG;

/*==============================================================*/
/* Table: DS_COMMON_DICT                                        */
/*==============================================================*/
CREATE TABLE DS_COMMON_DICT
(
   ID                   BIGINT(18) NOT NULL COMMENT '主键',
   NAME                 VARCHAR(300) DEFAULT NULL COMMENT '引用名',
   LABEL              VARCHAR(300) DEFAULT NULL COMMENT '名称',
   STATUS               INT(1) DEFAULT NULL COMMENT '状态(1:树形，0:列表)',
   SEQ                  BIGINT(18) DEFAULT NULL COMMENT '排序',
   PRIMARY KEY (ID)
);

ALTER TABLE DS_COMMON_DICT COMMENT '字典分类';

/*==============================================================*/
/* Table: DS_COMMON_DICT_DATA                                   */
/*==============================================================*/
CREATE TABLE DS_COMMON_DICT_DATA
(
   ID                   BIGINT(18) NOT NULL COMMENT '主键',
   PID                  BIGINT(18) COMMENT '上级ID(本表,所属字典项)',
   NAME                 VARCHAR(300) DEFAULT NULL COMMENT '引用名',
   LABEL              VARCHAR(300) DEFAULT NULL COMMENT '名称',
   ALIAS                VARCHAR(128) DEFAULT NULL COMMENT '标识',
   STATUS               INT(1) DEFAULT NULL COMMENT '状态(1:树叉，0:树叶)',
   SEQ                  BIGINT(18) DEFAULT NULL COMMENT '排序',
   MEMO                 VARCHAR(300) DEFAULT NULL COMMENT '备注',
   PRIMARY KEY (ID),
   CONSTRAINT FK_DS_COMMON_DICT_DATA FOREIGN KEY (PID)
      REFERENCES DS_COMMON_DICT_DATA (ID) ON DELETE CASCADE ON UPDATE CASCADE
);

ALTER TABLE DS_COMMON_DICT_DATA COMMENT '字典项';

/*==============================================================*/
/* Table: DS_COMMON_SYSTEM                                      */
/*==============================================================*/
CREATE TABLE DS_COMMON_SYSTEM
(
   ID                   BIGINT(18) NOT NULL AUTO_INCREMENT COMMENT '主键',
   NAME                 VARCHAR(300) COMMENT '名称',
   ALIAS                VARCHAR(128) COMMENT '系统别名',
   PASSWORD             VARCHAR(64) COMMENT '访问密码',
   DOMAINURL            VARCHAR(300) COMMENT '网络地址和端口',
   ROOTURL              VARCHAR(300) COMMENT '应用根路径',
   MENUURL              VARCHAR(300) COMMENT '菜单地址',
   STATUS               INT(1) COMMENT '状态(0,禁止,1,允许)',
   MEMO                 VARCHAR(300) COMMENT '备注',
   PRIMARY KEY (ID)
);

ALTER TABLE DS_COMMON_SYSTEM COMMENT '应用系统';

/*==============================================================*/
/* Table: DS_COMMON_FUNC                                        */
/*==============================================================*/
CREATE TABLE DS_COMMON_FUNC
(
   ID                   BIGINT(18) NOT NULL AUTO_INCREMENT COMMENT '主键',
   PID                  BIGINT(18) COMMENT '上级ID(本表)',
   SYSTEMID             BIGINT(18) COMMENT '所属系统ID',
   NAME                 VARCHAR(300) COMMENT '名称',
   ALIAS                VARCHAR(300) COMMENT '标识',
   URI                  VARCHAR(300) COMMENT '对应的URI',
   IMG                  VARCHAR(100) COMMENT '显示图标',
   STATUS               INT(1) COMMENT '状态(0不是菜单,1菜单,不是菜单不能添加下级)',
   SEQ                  BIGINT(18) COMMENT '排序',
   MEMO                 VARCHAR(3000) COMMENT '扩展信息',
   RESOURCES            VARCHAR(4000) COMMENT '资源集合',
   PRIMARY KEY (ID),
   CONSTRAINT FK_DS_COMMON_FUNC_SYSTEM FOREIGN KEY (SYSTEMID)
      REFERENCES DS_COMMON_SYSTEM (ID) ON DELETE CASCADE ON UPDATE CASCADE,
   CONSTRAINT FK_DS_COMMON_FUNC FOREIGN KEY (PID)
      REFERENCES DS_COMMON_FUNC (ID) ON DELETE CASCADE ON UPDATE CASCADE
);

ALTER TABLE DS_COMMON_FUNC COMMENT '功能';

/*==============================================================*/
/* Table: DS_COMMON_LOGIN                                       */
/*==============================================================*/
CREATE TABLE DS_COMMON_LOGIN
(
   ID                   BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
   LOGINTIME            VARCHAR(19) COMMENT '登录时间',
   LOGOUTTIME           VARCHAR(19) COMMENT '登出时间',
   TIMEOUTTIME          VARCHAR(19) COMMENT '超时时间',
   PWDTIME              VARCHAR(19) COMMENT '密码修改时间，没修改则为空，修改成功后直接登出',
   TICKET               VARCHAR(64) COMMENT '登录标识',
   IP                   VARCHAR(128) COMMENT 'IP地址',
   ACCOUNT              VARCHAR(64) COMMENT '操作人账号',
   NAME                 VARCHAR(30) COMMENT '操作人名称',
   STATUS               INT COMMENT '状态(0失败,1成功)',
   PRIMARY KEY (ID)
);

ALTER TABLE DS_COMMON_LOGIN COMMENT '系统登录日志';

/*==============================================================*/
/* Table: DS_COMMON_ORG                                         */
/*==============================================================*/
CREATE TABLE DS_COMMON_ORG
(
   ID                   BIGINT(18) NOT NULL AUTO_INCREMENT COMMENT '部门ID',
   PID                  BIGINT(18) COMMENT '上级ID(本表,所属组织机构)',
   NAME                 VARCHAR(300) COMMENT '名称',
   STATUS               INT(1) COMMENT '类型(2单位,1部门,0岗位)',
   SEQ                  BIGINT(18) COMMENT '排序',
   DUTYSCOPE            VARCHAR(3000) COMMENT '职责范围',
   MEMO                 VARCHAR(3000) COMMENT '备注',
   PRIMARY KEY (ID),
   CONSTRAINT FK_DS_COMMON_ORG FOREIGN KEY (PID)
      REFERENCES DS_COMMON_ORG (ID) ON DELETE CASCADE ON UPDATE CASCADE
);

ALTER TABLE DS_COMMON_ORG COMMENT '组织机构';

/*==============================================================*/
/* Table: DS_COMMON_ROLE                                        */
/*==============================================================*/
CREATE TABLE DS_COMMON_ROLE
(
   ID                   BIGINT(18) NOT NULL AUTO_INCREMENT COMMENT '主键',
   PID                  BIGINT(18) COMMENT '上级ID(本表)',
   SYSTEMID             BIGINT(18) NOT NULL COMMENT '所属系统ID',
   NAME                 VARCHAR(300) COMMENT '名称',
   MEMO                 VARCHAR(300) COMMENT '备注',
   SEQ                  BIGINT(18) COMMENT '排序',
   PRIMARY KEY (ID),
   CONSTRAINT FK_DS_COMMON_ROLE_SYSTEM FOREIGN KEY (SYSTEMID)
      REFERENCES DS_COMMON_SYSTEM (ID) ON DELETE CASCADE ON UPDATE CASCADE,
   CONSTRAINT FK_DS_COMMON_ROLE FOREIGN KEY (PID)
      REFERENCES DS_COMMON_ROLE (ID) ON DELETE CASCADE ON UPDATE CASCADE
);

ALTER TABLE DS_COMMON_ROLE COMMENT '角色';

/*==============================================================*/
/* Table: DS_COMMON_ORGROLE                                     */
/*==============================================================*/
CREATE TABLE DS_COMMON_ORGROLE
(
   ID                   BIGINT(18) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
   ORGID                BIGINT(18) NOT NULL COMMENT '岗位ID',
   ROLEID               BIGINT(18) COMMENT '角色ID',
   PRIMARY KEY (ID),
   CONSTRAINT FK_DS_COMMON_ORGROLE_ORG FOREIGN KEY (ORGID)
      REFERENCES DS_COMMON_ORG (ID) ON DELETE CASCADE ON UPDATE CASCADE,
   CONSTRAINT FK_DS_COMMON_ORGROLE_ROLE FOREIGN KEY (ROLEID)
      REFERENCES DS_COMMON_ROLE (ID) ON DELETE CASCADE ON UPDATE CASCADE
);

ALTER TABLE DS_COMMON_ORGROLE COMMENT '岗位角色关系';

/*==============================================================*/
/* Table: DS_COMMON_PERMISSIBLE                                 */
/*==============================================================*/
CREATE TABLE DS_COMMON_PERMISSIBLE
(
   ID                   BIGINT NOT NULL COMMENT '主键',
   POSTID               BIGINT COMMENT '岗位ID',
   SYSTEMID             BIGINT COMMENT '系统ID',
   ROLEID               BIGINT COMMENT '角色ID',
   FUNCID               BIGINT COMMENT '功能ID',
   USERID               VARCHAR(64) COMMENT '用户ID'
);

ALTER TABLE DS_COMMON_PERMISSIBLE COMMENT '功能许可';

/*==============================================================*/
/* Table: DS_COMMON_ROLEFUNC                                    */
/*==============================================================*/
CREATE TABLE DS_COMMON_ROLEFUNC
(
   ID                   BIGINT(18) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
   SYSTEMID             BIGINT(18) COMMENT '系统ID',
   ROLEID               BIGINT(18) COMMENT '角色ID',
   FUNCID               BIGINT(18) COMMENT '功能ID',
   PRIMARY KEY (ID),
   CONSTRAINT FK_DS_COMMON_ROLEFUNC_SYSTEM FOREIGN KEY (SYSTEMID)
      REFERENCES DS_COMMON_SYSTEM (ID) ON DELETE CASCADE ON UPDATE CASCADE,
   CONSTRAINT FK_DS_COMMON_ROLEFUNC_FUNC FOREIGN KEY (FUNCID)
      REFERENCES DS_COMMON_FUNC (ID) ON DELETE CASCADE ON UPDATE CASCADE,
   CONSTRAINT FK_DS_COMMON_ROLEFUNC_ROLE FOREIGN KEY (ROLEID)
      REFERENCES DS_COMMON_ROLE (ID) ON DELETE CASCADE ON UPDATE CASCADE
);

ALTER TABLE DS_COMMON_ROLEFUNC COMMENT '角色功能关系';

/*==============================================================*/
/* Table: DS_COMMON_USER                                        */
/*==============================================================*/
CREATE TABLE DS_COMMON_USER
(
   ID                   BIGINT(18) NOT NULL AUTO_INCREMENT COMMENT '主键',
   ACCOUNT              VARCHAR(64) NOT NULL COMMENT '帐号',
   PASSWORD             VARCHAR(256) COMMENT '密码',
   NAME                 VARCHAR(30) COMMENT '姓名',
   IDCARD               VARCHAR(64) COMMENT '身份证号',
   STATUS               INT(1) COMMENT '状态(0,禁止,1,允许)',
   EMAIL                VARCHAR(300) COMMENT '电子邮件',
   MOBILE               VARCHAR(30) COMMENT '手机',
   PHONE                VARCHAR(30) COMMENT '电话',
   WORKCARD             VARCHAR(64) COMMENT '工作证号',
   CAKEY                VARCHAR(1024) COMMENT 'CA证书的KEY',
   CREATETIME           VARCHAR(19) COMMENT '创建时间',
   ORGPID               BIGINT(18) COMMENT '所属单位',
   ORGID                BIGINT(18) COMMENT '所属部门',
   PRIMARY KEY (ID)
);

ALTER TABLE DS_COMMON_USER COMMENT '用户信息';

/*==============================================================*/
/* Table: DS_COMMON_USERORG                                     */
/*==============================================================*/
CREATE TABLE DS_COMMON_USERORG
(
   ID                   BIGINT(18) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
   ORGID                BIGINT(18) NOT NULL COMMENT '岗位ID',
   USERID               BIGINT(18) COMMENT '用户ID',
   PRIMARY KEY (ID),
   CONSTRAINT FK_DS_COMMON_USERORG_ORG FOREIGN KEY (ORGID)
      REFERENCES DS_COMMON_ORG (ID) ON DELETE CASCADE ON UPDATE CASCADE,
   CONSTRAINT FK_DS_COMMON_USERORG_USER FOREIGN KEY (USERID)
      REFERENCES DS_COMMON_USER (ID) ON DELETE CASCADE ON UPDATE CASCADE
);

ALTER TABLE DS_COMMON_USERORG COMMENT '用户岗位关系';

