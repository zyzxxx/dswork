SET FOREIGN_KEY_CHECKS=0;


-- DS_COMMON_USER表下面有两张表(DS_COMMON_USERORG、DS_COMMON_USERROLE)依赖，删除时要注意
DROP TABLE IF EXISTS DS_COMMON_USER;
DROP TABLE IF EXISTS DS_COMMON_USERTYPE;
CREATE TABLE DS_COMMON_USERTYPE
(
   ID                   BIGINT NOT NULL COMMENT '主键',
   NAME                 VARCHAR(300) COMMENT '名称',
   ALIAS                VARCHAR(300) COMMENT '标识',
   STATUS               INT(1) COMMENT '状态(1)',
   SEQ                  BIGINT(18) COMMENT '排序',
   MEMO                 VARCHAR(3000) COMMENT '扩展信息',
   RESOURCES            VARCHAR(4000) COMMENT '资源集合',
   PRIMARY KEY (ID)
) COMMENT '用户类型';
CREATE TABLE DS_COMMON_USER
(
   ID                   BIGINT(18) NOT NULL COMMENT '主键',
   ACCOUNT              VARCHAR(64) NOT NULL COMMENT '帐号',
   PASSWORD             VARCHAR(256) COMMENT '密码',
   NAME                 VARCHAR(30) COMMENT '姓名',
   IDCARD               VARCHAR(64) COMMENT '身份证号',
   STATUS               INT(1) COMMENT '状态(1启用,0禁用)',
   EMAIL                VARCHAR(300) COMMENT '电子邮件',
   MOBILE               VARCHAR(30) COMMENT '手机',
   PHONE                VARCHAR(30) COMMENT '电话',
   WORKCARD             VARCHAR(64) COMMENT '工作证号',
   CAKEY                VARCHAR(1024) COMMENT 'CA证书的KEY',
   CREATETIME           VARCHAR(19) COMMENT '创建时间',
   ORGPID               BIGINT(18) COMMENT '所属单位',
   ORGID                BIGINT(18) COMMENT '所属部门',
   PRIMARY KEY (ID)
) COMMENT '系统用户';



DROP TABLE IF EXISTS DS_BBS_FORUM;
DROP TABLE IF EXISTS DS_BBS_PAGE;
DROP TABLE IF EXISTS DS_BBS_SITE;
CREATE TABLE DS_BBS_SITE
(
   ID                   BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
   OWN                  VARCHAR(300) COMMENT '拥有者',
   NAME                 VARCHAR(300) COMMENT '名称',
   FOLDER               VARCHAR(300) COMMENT '目录名称',
   URL                  VARCHAR(300) COMMENT '链接',
   IMG                  VARCHAR(300) COMMENT '图片',
   VIEWSITE             VARCHAR(300) COMMENT '网站模板',
   PRIMARY KEY (ID)
) COMMENT '论坛站点';
CREATE TABLE DS_BBS_FORUM
(
   ID                   BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
   PID                  BIGINT COMMENT '父ID',
   SITEID               BIGINT COMMENT '站点ID',
   NAME                 VARCHAR(300) COMMENT '名称',
   SUMMARY              VARCHAR(300) COMMENT '摘要',
   STATUS               INT(1) COMMENT '状态(1启用,0禁用)',
   SEQ                  VARCHAR(300) COMMENT '排序',
   VIEWSITE             VARCHAR(300) COMMENT '模板',
   PRIMARY KEY (ID),
   CONSTRAINT FK_DS_BBS_FORUM FOREIGN KEY (PID)
      REFERENCES DS_BBS_FORUM (ID) ON DELETE CASCADE ON UPDATE CASCADE,
   CONSTRAINT FK_DS_BBS_FORUM_SITE FOREIGN KEY (SITEID)
      REFERENCES DS_BBS_SITE (ID) ON DELETE CASCADE ON UPDATE CASCADE
) COMMENT '版块';
CREATE TABLE DS_BBS_PAGE
(
   ID                   BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
   SITEID               BIGINT COMMENT '站点ID',
   FORUMID              BIGINT COMMENT '版块ID',
   USERID               VARCHAR(300) COMMENT '发表人ID',
   TITLE                VARCHAR(300) COMMENT '标题',
   URL                  VARCHAR(300) COMMENT '链接',
   SUMMARY              VARCHAR(300) COMMENT '摘要',
   ISESSENCE            INT COMMENT '精华(0否,1是)',
   ISTOP                INT COMMENT '置顶(0否,1是)',
   METAKEYWORDS         VARCHAR(300) COMMENT 'meta关键词',
   METADESCRIPTION      VARCHAR(300) COMMENT 'meta描述',
   RELEASEUSER          VARCHAR(30) COMMENT '发表人',
   RELEASETIME          VARCHAR(19) COMMENT '发表时间',
   OVERTIME             VARCHAR(19) COMMENT '结贴时间',
   LASTUSER             VARCHAR(300) COMMENT '最后回复人',
   LASTTIME             VARCHAR(300) COMMENT '最后回复时间',
   NUMPV                INT COMMENT '点击量',
   NUMHT                INT COMMENT '回贴数',
   CONTENT              MEDIUMTEXT COMMENT '内容',
   PRIMARY KEY (ID),
   CONSTRAINT FK_DS_BBS_PAGE_SITE FOREIGN KEY (SITEID)
      REFERENCES DS_BBS_SITE (ID) ON DELETE CASCADE ON UPDATE CASCADE
) COMMENT '主题';



DROP TABLE IF EXISTS DS_CMS_PERMISSION;
DROP TABLE IF EXISTS DS_CMS_PAGE_EDIT;
DROP TABLE IF EXISTS DS_CMS_CATEGORY_EDIT;
DROP TABLE IF EXISTS DS_CMS_LOG;
DROP TABLE IF EXISTS DS_CMS_PAGE;
DROP TABLE IF EXISTS DS_CMS_CATEGORY;
DROP TABLE IF EXISTS DS_CMS_SITE;
CREATE TABLE DS_CMS_SITE
(
   ID                   BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
   OWN                  VARCHAR(300) COMMENT '拥有者',
   NAME                 VARCHAR(300) COMMENT '站点名称',
   FOLDER               VARCHAR(300) COMMENT '目录名称',
   URL                  VARCHAR(300) COMMENT '链接',
   IMG                  VARCHAR(300) COMMENT '图片',
   VIEWSITE             VARCHAR(300) COMMENT '网站模板',
   ENABLELOG            INT(1) COMMENT '开启日志(0否,1是)',
   METAKEYWORDS         VARCHAR(300) COMMENT 'meta关键词',
   METADESCRIPTION      VARCHAR(300) COMMENT 'meta描述',
   PRIMARY KEY (ID)
) COMMENT '网站站点';
CREATE TABLE DS_CMS_CATEGORY
(
   ID                   BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
   PID                  BIGINT COMMENT '父ID',
   SITEID               BIGINT COMMENT '站点ID',
   STATUS               INT COMMENT '状态(-1删除,0新增,1修改,8已发)',
   NAME                 VARCHAR(300) COMMENT '栏目名称',
   FOLDER               VARCHAR(300) COMMENT '目录名称',
   SCOPE                INT COMMENT '类型(0列表,1单页,2外链)',
   URL                  VARCHAR(300) COMMENT '链接',
   SEQ                  VARCHAR(300) COMMENT '排序',
   VIEWSITE             VARCHAR(300) COMMENT '栏目模板',
   PAGEVIEWSITE         VARCHAR(300) COMMENT '内容模板',
   METAKEYWORDS         VARCHAR(300) COMMENT 'meta关键词',
   METADESCRIPTION      VARCHAR(300) COMMENT 'meta描述',
   SUMMARY              VARCHAR(300) COMMENT '摘要',
   RELEASETIME          VARCHAR(19) COMMENT '发布时间',
   RELEASESOURCE        VARCHAR(300) COMMENT '来源',
   RELEASEUSER          VARCHAR(300) COMMENT '作者',
   IMG                  VARCHAR(300) COMMENT '图片',
   CONTENT              MEDIUMTEXT COMMENT '内容',
   PRIMARY KEY (ID),
   CONSTRAINT FK_DS_CMS_CATEGORY_SITE FOREIGN KEY (SITEID)
      REFERENCES DS_CMS_SITE (ID) ON DELETE CASCADE ON UPDATE CASCADE,
   CONSTRAINT FK_DS_CMS_CATEGORY FOREIGN KEY (PID)
      REFERENCES DS_CMS_CATEGORY (ID) ON DELETE CASCADE ON UPDATE CASCADE
) COMMENT '栏目';
CREATE TABLE DS_CMS_PAGE
(
   ID                   BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
   SITEID               BIGINT COMMENT '站点ID',
   CATEGORYID           BIGINT COMMENT '栏目ID',
   STATUS               INT COMMENT '状态(-1删除,0新增,1修改,8已发)',
   TITLE                VARCHAR(300) COMMENT '标题',
   SCOPE                INT COMMENT '类型(1单页,2外链)',
   URL                  VARCHAR(300) COMMENT '链接',
   METAKEYWORDS         VARCHAR(300) COMMENT 'meta关键词',
   METADESCRIPTION      VARCHAR(300) COMMENT 'meta描述',
   SUMMARY              VARCHAR(300) COMMENT '摘要',
   RELEASETIME          VARCHAR(19) COMMENT '发布时间',
   RELEASESOURCE        VARCHAR(300) COMMENT '来源',
   RELEASEUSER          VARCHAR(300) COMMENT '作者',
   IMG                  VARCHAR(300) COMMENT '图片',
   IMGTOP               INT COMMENT '焦点图(0否,1是)',
   PAGETOP              INT COMMENT '首页推荐(0否,1是)',
   CONTENT              MEDIUMTEXT COMMENT '内容',
   PRIMARY KEY (ID),
   CONSTRAINT FK_DS_CMS_PAGE_SITE FOREIGN KEY (SITEID)
      REFERENCES DS_CMS_SITE (ID) ON DELETE CASCADE ON UPDATE CASCADE
) COMMENT '内容';
CREATE TABLE DS_CMS_LOG
(
   ID                   BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
   SITEID               BIGINT COMMENT '站点ID',
   CATEGORYID           BIGINT COMMENT '栏目ID',
   PAGEID               BIGINT COMMENT '内容ID',
   EDITID               VARCHAR(64) COMMENT '采编员ID',
   EDITNAME             VARCHAR(300) COMMENT '采编员姓名',
   EDITTIME             VARCHAR(19) COMMENT '采编时间',
   AUDITID              VARCHAR(64) COMMENT '审核员ID',
   AUDITMSG             VARCHAR(300) COMMENT '审核意见',
   AUDITNAME            VARCHAR(300) COMMENT '审核员姓名',
   AUDITTIME            VARCHAR(19) COMMENT '审核时间',
   PUBLISHID            VARCHAR(64) COMMENT '发布员ID',
   PUBLISHNAME          VARCHAR(300) COMMENT '发布员姓名',
   PUBLISHTIME          VARCHAR(19) COMMENT '发布时间',
   STATUS               INT COMMENT '状态(-1删除,0新增,1修改,4撤销,8已发)',
   AUDITSTATUS          INT COMMENT '审核状态(0草稿,1未审核,2不通过,4通过)',
   TITLE                VARCHAR(300) COMMENT '标题(栏目:NAME,内容:TITLE)',
   SCOPE                INT COMMENT '类型(1单页,2外链)',
   URL                  VARCHAR(300) COMMENT '链接',
   METAKEYWORDS         VARCHAR(300) COMMENT 'meta关键词',
   METADESCRIPTION      VARCHAR(300) COMMENT 'meta描述',
   SUMMARY              VARCHAR(300) COMMENT '摘要',
   RELEASETIME          VARCHAR(19) COMMENT '发布时间',
   RELEASESOURCE        VARCHAR(300) COMMENT '信息来源',
   RELEASEUSER          VARCHAR(300) COMMENT '作者',
   IMG                  VARCHAR(300) COMMENT '图片',
   CONTENT              MEDIUMTEXT COMMENT '内容',
   IMGTOP               INT COMMENT '焦点图(0否,1是)',
   PAGETOP              INT COMMENT '首页推荐(0否,1是)',
   PRIMARY KEY (ID)
) COMMENT '网站日志';
CREATE TABLE DS_CMS_CATEGORY_EDIT
(
   ID                   BIGINT NOT NULL COMMENT '主键',
   SITEID               BIGINT COMMENT '站点ID',
   STATUS               INT COMMENT '状态(-1删除,0新增,1更新)',
   MSG                  VARCHAR(300) COMMENT '审核意见',
   METAKEYWORDS         VARCHAR(300) COMMENT 'meta关键词',
   METADESCRIPTION      VARCHAR(300) COMMENT 'meta描述',
   SUMMARY              VARCHAR(300) COMMENT '摘要',
   RELEASETIME          VARCHAR(19) COMMENT '发布时间',
   RELEASESOURCE        VARCHAR(300) COMMENT '来源',
   RELEASEUSER          VARCHAR(300) COMMENT '作者',
   IMG                  VARCHAR(300) COMMENT '图片',
   URL                  VARCHAR(255) COMMENT '外链URL',
   EDITID               VARCHAR(64) COMMENT '编辑人员ID',
   EDITNAME             VARCHAR(30) COMMENT '编辑人员名称',
   EDITTIME             VARCHAR(19) COMMENT '编辑时间',
   AUDITSTATUS          INT COMMENT '状态(0草稿,1未审核,2不通过,4通过)',
   AUDITID              VARCHAR(64) COMMENT '审核人员ID',
   AUDITNAME            VARCHAR(30) COMMENT '审核人员名称',
   AUDITTIME            VARCHAR(19) COMMENT '审核时间',
   CONTENT              MEDIUMTEXT COMMENT '内容',
   PRIMARY KEY (ID),
   CONSTRAINT FK_DS_CMS_CATEGORY_EDIT_S FOREIGN KEY (SITEID)
      REFERENCES DS_CMS_SITE (ID) ON DELETE CASCADE ON UPDATE CASCADE,
   CONSTRAINT FK_DS_CMS_CATEGORY_EDIT_C FOREIGN KEY (ID)
      REFERENCES DS_CMS_CATEGORY (ID) ON DELETE CASCADE ON UPDATE CASCADE
) COMMENT '审核栏目';
CREATE TABLE DS_CMS_PAGE_EDIT
(
   ID                   BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
   SITEID               BIGINT COMMENT '站点ID',
   CATEGORYID           BIGINT COMMENT '栏目ID',
   STATUS               INT COMMENT '状态(-1删除,0新增,1更新)',
   MSG                  VARCHAR(300) COMMENT '审核意见',
   TITLE                VARCHAR(300) COMMENT '标题',
   SCOPE                INT COMMENT '类型(1单页,2外链)',
   URL                  VARCHAR(300) COMMENT '链接',
   METAKEYWORDS         VARCHAR(300) COMMENT 'meta关键词',
   METADESCRIPTION      VARCHAR(300) COMMENT 'meta描述',
   SUMMARY              VARCHAR(300) COMMENT '摘要',
   RELEASETIME          VARCHAR(19) COMMENT '发布时间',
   RELEASESOURCE        VARCHAR(300) COMMENT '信息来源',
   RELEASEUSER          VARCHAR(300) COMMENT '作者',
   IMG                  VARCHAR(300) COMMENT '图片',
   IMGTOP               INT COMMENT '焦点图(0否,1是)',
   PAGETOP              INT COMMENT '首页推荐(0否,1是)',
   EDITID               VARCHAR(64) COMMENT '编辑人员ID',
   EDITNAME             VARCHAR(30) COMMENT '编辑人员名称',
   EDITTIME             VARCHAR(19) COMMENT '编辑时间',
   AUDITSTATUS          INT COMMENT '状态(0草稿,1未审核,2不通过,4通过)',
   AUDITID              VARCHAR(64) COMMENT '审核人员ID',
   AUDITNAME            VARCHAR(30) COMMENT '审核人员名称',
   AUDITTIME            VARCHAR(19) COMMENT '审核时间',
   CONTENT              MEDIUMTEXT COMMENT '内容',
   PRIMARY KEY (ID),
   CONSTRAINT FK_DS_CMS_PAGE_EDIT_S FOREIGN KEY (SITEID)
      REFERENCES DS_CMS_SITE (ID) ON DELETE CASCADE ON UPDATE CASCADE,
   CONSTRAINT FK_DS_CMS_PAGE_EDIT_C FOREIGN KEY (CATEGORYID)
      REFERENCES DS_CMS_CATEGORY (ID) ON DELETE CASCADE ON UPDATE CASCADE
) COMMENT '审核内容';
CREATE TABLE DS_CMS_PERMISSION
(
   ID                   BIGINT NOT NULL COMMENT '主键',
   SITEID               BIGINT COMMENT '站点ID',
   ACCOUNT              VARCHAR(300) COMMENT '用户账号',
   EDITOWN              VARCHAR(3000) COMMENT '可采编栏目（限个人）',
   EDITALL              VARCHAR(3000) COMMENT '可采编栏目',
   AUDIT                VARCHAR(3000) COMMENT '可审核栏目',
   PUBLISH              VARCHAR(3000) COMMENT '可发布栏目',
   PRIMARY KEY (ID),
   CONSTRAINT FK_DS_CMS_PERMISSION FOREIGN KEY (SITEID)
      REFERENCES DS_CMS_SITE (ID) ON DELETE CASCADE ON UPDATE CASCADE
) COMMENT '网站权限';

CREATE VIEW DS_CMS_USER AS 
SELECT ID, ACCOUNT, NAME, concat('admin', ACCOUNT) AS OWN FROM DS_COMMON_USER;




DROP TABLE IF EXISTS DS_COMMON_DICT_DATA;
DROP TABLE IF EXISTS DS_COMMON_DICT;
CREATE TABLE DS_COMMON_DICT
(
   ID                   BIGINT(18) NOT NULL COMMENT '主键',
   NAME                 VARCHAR(300) DEFAULT NULL COMMENT '引用名',
   LABEL              VARCHAR(300) DEFAULT NULL COMMENT '名称',
   STATUS               INT(1) DEFAULT NULL COMMENT '状态(1树形,0列表)',
   SEQ                  BIGINT(18) DEFAULT NULL COMMENT '排序',
   PRIMARY KEY (ID)
) COMMENT '字典分类';
CREATE TABLE DS_COMMON_DICT_DATA
(
   ID                   BIGINT(18) NOT NULL COMMENT '主键',
   PID                  BIGINT(18) COMMENT '上级ID(本表,所属字典项)',
   NAME                 VARCHAR(300) DEFAULT NULL COMMENT '引用名',
   LABEL              VARCHAR(300) DEFAULT NULL COMMENT '名称',
   ALIAS                VARCHAR(128) DEFAULT NULL COMMENT '标识',
   STATUS               INT(1) DEFAULT NULL COMMENT '状态(1树叉,0树叶)',
   SEQ                  BIGINT(18) DEFAULT NULL COMMENT '排序',
   MEMO                 VARCHAR(300) DEFAULT NULL COMMENT '备注',
   PRIMARY KEY (ID),
   CONSTRAINT FK_DS_COMMON_DICT_DATA FOREIGN KEY (PID)
      REFERENCES DS_COMMON_DICT_DATA (ID) ON DELETE CASCADE ON UPDATE CASCADE
) COMMENT '字典项';



DROP TABLE IF EXISTS DS_COMMON_USERORG;
DROP TABLE IF EXISTS DS_COMMON_USERROLE;
DROP TABLE IF EXISTS DS_COMMON_FUNC;
DROP TABLE IF EXISTS DS_COMMON_ORG;
DROP TABLE IF EXISTS DS_COMMON_ORGROLE;
DROP TABLE IF EXISTS DS_COMMON_ROLE;
DROP TABLE IF EXISTS DS_COMMON_ROLEFUNC;
DROP TABLE IF EXISTS DS_COMMON_SYSTEM;
CREATE TABLE DS_COMMON_SYSTEM
(
   ID                   BIGINT(18) NOT NULL COMMENT '主键',
   NAME                 VARCHAR(300) COMMENT '名称',
   ALIAS                VARCHAR(128) COMMENT '系统别名',
   PASSWORD             VARCHAR(64) COMMENT '访问密码',
   DOMAINURL            VARCHAR(300) COMMENT '网络地址和端口',
   ROOTURL              VARCHAR(300) COMMENT '应用根路径',
   MENUURL              VARCHAR(300) COMMENT '菜单地址',
   STATUS               INT(1) COMMENT '状态(1启用,0禁用)',
   SEQ                  BIGINT(18) COMMENT '排序',
   MEMO                 VARCHAR(300) COMMENT '备注',
   PRIMARY KEY (ID)
) COMMENT '应用系统';
CREATE TABLE DS_COMMON_FUNC
(
   ID                   BIGINT(18) NOT NULL COMMENT '主键',
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
) COMMENT '功能';
CREATE TABLE DS_COMMON_ORG
(
   ID                   BIGINT(18) NOT NULL COMMENT '部门ID',
   PID                  BIGINT(18) COMMENT '上级ID(本表)',
   NAME                 VARCHAR(300) COMMENT '名称',
   STATUS               INT(1) COMMENT '类型(2单位,1部门,0岗位)',
   SEQ                  BIGINT(18) COMMENT '排序',
   DUTYSCOPE            VARCHAR(3000) COMMENT '职责范围',
   MEMO                 VARCHAR(3000) COMMENT '备注',
   PRIMARY KEY (ID),
   CONSTRAINT FK_DS_COMMON_ORG FOREIGN KEY (PID)
      REFERENCES DS_COMMON_ORG (ID) ON DELETE CASCADE ON UPDATE CASCADE
) COMMENT '组织机构';
CREATE TABLE DS_COMMON_ROLE
(
   ID                   BIGINT(18) NOT NULL COMMENT '主键',
   PID                  BIGINT(18) COMMENT '上级ID(本表)',
   SYSTEMID             BIGINT(18) NOT NULL COMMENT '所属系统ID',
   NAME                 VARCHAR(300) COMMENT '名称',
   SEQ                  BIGINT(18) COMMENT '排序',
   MEMO                 VARCHAR(300) COMMENT '备注',
   PRIMARY KEY (ID),
   CONSTRAINT FK_DS_COMMON_ROLE_SYSTEM FOREIGN KEY (SYSTEMID)
      REFERENCES DS_COMMON_SYSTEM (ID) ON DELETE CASCADE ON UPDATE CASCADE,
   CONSTRAINT FK_DS_COMMON_ROLE FOREIGN KEY (PID)
      REFERENCES DS_COMMON_ROLE (ID) ON DELETE CASCADE ON UPDATE CASCADE
) COMMENT '角色';
CREATE TABLE DS_COMMON_ORGROLE
(
   ID                   BIGINT(18) NOT NULL COMMENT '主键ID',
   ORGID                BIGINT(18) NOT NULL COMMENT '岗位ID',
   ROLEID               BIGINT(18) COMMENT '角色ID',
   PRIMARY KEY (ID),
   CONSTRAINT FK_DS_COMMON_ORGROLE_ORG FOREIGN KEY (ORGID)
      REFERENCES DS_COMMON_ORG (ID) ON DELETE CASCADE ON UPDATE CASCADE,
   CONSTRAINT FK_DS_COMMON_ORGROLE_ROLE FOREIGN KEY (ROLEID)
      REFERENCES DS_COMMON_ROLE (ID) ON DELETE CASCADE ON UPDATE CASCADE
) COMMENT '岗位角色关系';
CREATE TABLE DS_COMMON_ROLEFUNC
(
   ID                   BIGINT(18) NOT NULL COMMENT '主键ID',
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
) COMMENT '角色功能关系';
CREATE TABLE DS_COMMON_USERORG
(
   ID                   BIGINT(18) NOT NULL COMMENT '主键ID',
   ORGID                BIGINT(18) NOT NULL COMMENT '岗位ID',
   USERID               BIGINT(18) COMMENT '用户ID',
   PRIMARY KEY (ID),
   CONSTRAINT FK_DS_COMMON_USERORG_ORG FOREIGN KEY (ORGID)
      REFERENCES DS_COMMON_ORG (ID) ON DELETE CASCADE ON UPDATE CASCADE,
   CONSTRAINT FK_DS_COMMON_USERORG_USER FOREIGN KEY (USERID)
      REFERENCES DS_COMMON_USER (ID) ON DELETE CASCADE ON UPDATE CASCADE
) COMMENT '用户岗位关系';
CREATE TABLE DS_COMMON_USERROLE
(
   ID                   BIGINT(18) NOT NULL COMMENT '主键ID',
   ROLEID               BIGINT(18) NOT NULL COMMENT '角色ID',
   USERID               BIGINT(18) COMMENT '用户ID',
   PRIMARY KEY (ID),
   CONSTRAINT FK_DS_COMMON_USERROLE_ROLE FOREIGN KEY (ROLEID)
      REFERENCES DS_COMMON_ROLE (ID) ON DELETE CASCADE ON UPDATE CASCADE,
   CONSTRAINT FK_DS_COMMON_USERROLE_USER FOREIGN KEY (USERID)
      REFERENCES DS_COMMON_USER (ID) ON DELETE CASCADE ON UPDATE CASCADE
) COMMENT '用户角色关系';




DROP TABLE IF EXISTS DS_COMMON_LOGIN;
CREATE TABLE DS_COMMON_LOGIN
(
   ID                   BIGINT NOT NULL COMMENT '主键',
   LOGINTIME            VARCHAR(19) COMMENT '登录时间',
   LOGOUTTIME           VARCHAR(19) COMMENT '登出时间',
   TIMEOUTTIME          VARCHAR(19) COMMENT '超时时间',
   PWDTIME              VARCHAR(19) COMMENT '密码修改时间，没修改则为空，修改成功后直接登出',
   TICKET               VARCHAR(128) COMMENT '登录标识',
   IP                   VARCHAR(128) COMMENT 'IP地址',
   ACCOUNT              VARCHAR(64) COMMENT '操作人账号',
   NAME                 VARCHAR(30) COMMENT '操作人名称',
   STATUS               INT COMMENT '状态(0失败,1成功)',
   PRIMARY KEY (ID)
) COMMENT '系统登录日志';




DROP TABLE IF EXISTS DS_FLOW;
DROP TABLE IF EXISTS DS_FLOW_CATEGORY;
DROP TABLE IF EXISTS DS_FLOW_PI;
DROP TABLE IF EXISTS DS_FLOW_PI_DATA;
DROP TABLE IF EXISTS DS_FLOW_PI_WAITING;
DROP TABLE IF EXISTS DS_FLOW_TASK;SET FOREIGN_KEY_CHECKS=0;

-- DROP TABLE IF EXISTS DS_FLOW;
-- DROP TABLE IF EXISTS DS_FLOW_CATEGORY;
-- DROP TABLE IF EXISTS DS_FLOW_PI;
-- DROP TABLE IF EXISTS DS_FLOW_PI_DATA;
-- DROP TABLE IF EXISTS DS_FLOW_PI_WAITING;
-- DROP TABLE IF EXISTS DS_FLOW_TASK;

CREATE TABLE IF NOT EXISTS DS_FLOW_CATEGORY
(
   ID                   BIGINT(18) NOT NULL COMMENT '分类ID',
   NAME                 VARCHAR(300) COMMENT '分类名称',
   PID                  BIGINT(18) COMMENT '父类',
   SEQ                  BIGINT(18) COMMENT '排序',
   PRIMARY KEY (ID)
) COMMENT '流程分类';
CREATE TABLE IF NOT EXISTS DS_FLOW
(
   ID                   BIGINT(18) NOT NULL COMMENT '主键',
   CATEGORYID           BIGINT(18) COMMENT '分类',
   ALIAS                VARCHAR(300) COMMENT '流程标识',
   VNUM                 INT COMMENT '内部版本0为编辑版本',
   DEPLOYID             VARCHAR(300) COMMENT '流程发布ID，VNUM为0的放最新版本',
   NAME                 VARCHAR(300) COMMENT '名字',
   STATUS               INT(1) COMMENT '状态(1启用,0禁用)',
   FLOWXML              VARCHAR(4000)  COMMENT '流程图配置XML',
   PRIMARY KEY (ID),
   CONSTRAINT FK_DS_FLOW FOREIGN KEY (CATEGORYID)
      REFERENCES DS_FLOW_CATEGORY (ID) ON DELETE CASCADE ON UPDATE CASCADE
) COMMENT '流程';
CREATE TABLE IF NOT EXISTS DS_FLOW_PI
(
   ID                   BIGINT(18) NOT NULL COMMENT '主键ID(流程实例ID)',
   YWLSH                VARCHAR(300) COMMENT '业务流水号',
   SBLSH                VARCHAR(300) COMMENT '申办流水号',
   ALIAS                VARCHAR(300) COMMENT '流程标识',
   FLOWID               BIGINT(18) COMMENT '流程ID(对应deployid)',
   DEPLOYID             VARCHAR(300) COMMENT '流程发布ID',
   PIDAY                INT COMMENT '时限天数',
   PIDAYTYPE            INT(1) COMMENT '时限天数类型(0日历日,1工作日)',
   PISTART              VARCHAR(19) COMMENT '开始时间',
   PIEND                VARCHAR(19) COMMENT '结束时间',
   PIUPSTART            VARCHAR(19) COMMENT '挂起开始时间',
   PIUPEND              VARCHAR(19) COMMENT '挂起结束时间',
   STATUS               INT(1) COMMENT '流程状态(1申请,2运行,3挂起,0结束)',
   CACCOUNT             VARCHAR(300) COMMENT '承办人账号',
   CNAME                VARCHAR(300) COMMENT '承办人',
   PIALIAS              VARCHAR(4000) COMMENT '当前任务标识，以逗号分隔',
   PRIMARY KEY (ID)
) COMMENT '流程实例';
CREATE TABLE IF NOT EXISTS DS_FLOW_PI_DATA
(
   ID                   BIGINT(18) NOT NULL COMMENT '主键ID',
   PIID                 BIGINT(18) COMMENT '流程实例ID',
   TPREV                VARCHAR(300) COMMENT '上级任务(从哪过来的)',
   TALIAS               VARCHAR(300) COMMENT '任务标识',
   TNAME                VARCHAR(300) COMMENT '任务名称',
   STATUS               INT(1) COMMENT '状态(0已处理,1代办,2挂起,3取消挂起)',
   PACCOUNT             VARCHAR(300) COMMENT '经办人ID',
   PNAME                VARCHAR(300) COMMENT '经办人姓名',
   PTIME                VARCHAR(19) COMMENT '经办时间',
   PTYPE                VARCHAR(300) COMMENT '经办类型(0拒绝,1同意等等)',
   MEMO                 VARCHAR(4000) COMMENT '意见',
   PRIMARY KEY (ID),
   CONSTRAINT FK_DS_FLOW_PI_DATA FOREIGN KEY (PIID)
      REFERENCES DS_FLOW_PI (ID) ON DELETE CASCADE ON UPDATE CASCADE
) COMMENT '流程执行明细';
CREATE TABLE IF NOT EXISTS DS_FLOW_PI_WAITING
(
   ID                   BIGINT(18) NOT NULL COMMENT '主键ID',
   PIID                 BIGINT(18) COMMENT '实例ID',
   YWLSH                VARCHAR(300) COMMENT '业务流水号',
   SBLSH                VARCHAR(300) BINARY COMMENT '申办流水号',
   FLOWID               BIGINT(18) COMMENT '流程ID',
   FLOWNAME             VARCHAR(300) COMMENT '流程名称',
   TALIAS               VARCHAR(300) COMMENT '任务标识',
   TNAME                VARCHAR(300) COMMENT '任务名称',
   TCOUNT               INT COMMENT '合并任务个数(只有一个任务时等于1，其余大于1)',
   TPREV                VARCHAR(300) COMMENT '上级任务(从哪过来的)',
   TNEXT                VARCHAR(3000) COMMENT '下级任务(以逗号分隔节点标识，以|线分隔分支任务)',
   TSTART               VARCHAR(19) COMMENT '任务开始时间',
   TUSER                VARCHAR(3000) COMMENT '经办人',
   TUSERS               VARCHAR(3000) COMMENT '候选经办人',
   TMEMO                VARCHAR(3000) COMMENT '参数',
   TINTERFACE           VARCHAR(300) COMMENT '处理接口类',
   PRIMARY KEY (ID),
   CONSTRAINT FK_DS_FLOW_PI_DOING FOREIGN KEY (PIID)
      REFERENCES DS_FLOW_PI (ID) ON DELETE CASCADE ON UPDATE CASCADE
) COMMENT '流程待办事项';
CREATE TABLE IF NOT EXISTS DS_FLOW_TASK
(
   ID                   BIGINT(18) NOT NULL COMMENT '主键',
   FLOWID               BIGINT(18) COMMENT '流程ID',
   DEPLOYID             VARCHAR(300) COMMENT '流程发布ID，当前版本此值为空',
   TALIAS               VARCHAR(300) COMMENT '节点标识(start开始，end结束)',
   TNAME                VARCHAR(300) COMMENT '节点名称',
   TCOUNT               INT COMMENT '合并任务个数(只有一个任务时等于1，其余大于1)',
   TNEXT                VARCHAR(3000) COMMENT '下级任务(以逗号分隔节点标识，以|线分隔分支任务)',
   TUSERS               VARCHAR(3000) COMMENT '当前任务的用户ID(以逗号分隔节点标识)',
   TMEMO                VARCHAR(3000) COMMENT '参数',
   PRIMARY KEY (ID),
   CONSTRAINT FK_DS_FLOW_TASK FOREIGN KEY (FLOWID)
      REFERENCES DS_FLOW (ID) ON DELETE CASCADE ON UPDATE CASCADE
) COMMENT '流程任务';





DROP TABLE IF EXISTS DS_EP_ENTERPRISE;
DROP TABLE IF EXISTS DS_EP_USER;
DROP TABLE IF EXISTS DS_PERSON_USER;
CREATE TABLE DS_EP_ENTERPRISE
(
   ID                   BIGINT(18) NOT NULL COMMENT 'ID',
   NAME                 VARCHAR(300) COMMENT '企业名称',
   SSXQ                 VARCHAR(30) COMMENT '所属辖区',
   QYBM                 VARCHAR(64) COMMENT '编码',
   STATUS               INT COMMENT '状态(0禁用,1正常运营,待扩展)',
   TYPE                 VARCHAR(300) COMMENT '类型',
   PRIMARY KEY (ID)
) COMMENT '企业';
CREATE TABLE DS_EP_USER
(
   ID                   BIGINT(18) NOT NULL COMMENT '主键',
   QYBM                 VARCHAR(64) COMMENT '企业编码',
   ACCOUNT              VARCHAR(64) COMMENT '账号',
   PASSWORD             VARCHAR(256) COMMENT '密码',
   NAME                 VARCHAR(30) COMMENT '姓名',
   IDCARD               VARCHAR(64) COMMENT '身份证号',
   STATUS               INT COMMENT '状态(1启用,0禁用)',
   USERTYPE             INT COMMENT '用户类型(1企业管理员,0普通用户)',
   EMAIL                VARCHAR(300) COMMENT '电子邮件',
   MOBILE               VARCHAR(30) COMMENT '手机',
   PHONE                VARCHAR(30) COMMENT '电话',
   WORKCARD             VARCHAR(64) COMMENT '工作证号',
   CAKEY                VARCHAR(1024) COMMENT 'CA证书的KEY',
   CREATETIME           VARCHAR(19) COMMENT '创建时间',
   SSDW                 VARCHAR(300) COMMENT '所属单位',
   SSBM                 VARCHAR(300) COMMENT '所属部门',
   FAX                  VARCHAR(30) COMMENT '传真',
   PRIMARY KEY (ID)
) COMMENT '企业用户';
CREATE TABLE DS_PERSON_USER
(
   ID                   BIGINT(18) NOT NULL COMMENT '主键',
   IDCARD               VARCHAR(64) COMMENT '身份证号',
   ACCOUNT              VARCHAR(64) COMMENT '账号',
   PASSWORD             VARCHAR(256) COMMENT '密码',
   NAME                 VARCHAR(30) COMMENT '姓名',
   STATUS               INT COMMENT '状态(1启用,0禁用)',
   EMAIL                VARCHAR(300) COMMENT '电子邮件',
   MOBILE               VARCHAR(30) COMMENT '手机',
   PHONE                VARCHAR(30) COMMENT '电话',
   CAKEY                VARCHAR(1024) COMMENT 'CA证书的KEY',
   CREATETIME           VARCHAR(19) COMMENT '创建时间',
   USERTYPE             VARCHAR(30) COMMENT '用户类型',
   PRIMARY KEY (ID)
) COMMENT '个人用户';




DROP TABLE IF EXISTS DS_XZSP;
CREATE TABLE DS_XZSP
(
   ID                   BIGINT(18) NOT NULL COMMENT '主键',
   SBLSH                VARCHAR(19) COMMENT '申办流水号',
   SPTYPE               INT(11) COMMENT '对象类型0ShenBan，1YuShouLi，2ShouLi，3ShenPi，4BanJie，5TeBieChengXuQiDong，6TeBieChengXuBanJie，7BuJiaoGaoZhi，8BuJiaoShouLi，9LingQuDengJi',
   FSZT                 INT(11) COMMENT '发送状态(0待发,1已发,3信息格式不正确)',
   FSCS                 INT(11) COMMENT '发送次数',
   FSSJ                 VARCHAR(19) COMMENT '发送时间',
   SPOBJECT             VARCHAR(4000) COMMENT '序列化对象',
   MEMO                 VARCHAR(4000) COMMENT '备注',
   PRIMARY KEY (ID)
) COMMENT '电子政务行政审批';





DROP TABLE IF EXISTS GX_USER;
CREATE TABLE GX_USER (
  ID bigint(20) NOT NULL AUTO_INCREMENT,
  ALIAS varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '标识编码(企业编码、身份证...)',
  NAME varchar(300) COLLATE utf8_bin DEFAULT NULL COMMENT '名称',
  PASSWORD varchar(256) DEFAULT NULL COMMENT '密码',
  TYPE varchar(1) DEFAULT NULL COMMENT '类型(0个人,1旅行社,2饭店,3景区...)',
  STATE int(1) DEFAULT NULL COMMENT '状态(0未知,1正常,2已注销...)',
  MEMO varchar(3000) DEFAULT NULL COMMENT '预留字段',
  VJSON varchar(3000) DEFAULT NULL COMMENT '其他数据',
  PRIMARY KEY (ID)
) COMMENT='企业类型';

DROP TABLE IF EXISTS SSPT_JYFW;
CREATE TABLE SSPT_JYFW (
  ID bigint(18) NOT NULL,
  JYFW varchar(30) DEFAULT NULL,
  JYFWMSG varchar(90) DEFAULT NULL,
  PRIMARY KEY (ID)
);

DROP TABLE IF EXISTS SSPT_PWXK;
CREATE TABLE SSPT_PWXK (
  ID bigint(20) NOT NULL,
  QYBM varchar(18) DEFAULT NULL,
  ZCH varchar(64) DEFAULT NULL COMMENT '注册号',
  MC varchar(300) DEFAULT NULL COMMENT '商事主体名称',
  EWMC varchar(90) DEFAULT NULL COMMENT '英文名称',
  JYFW varchar(300) DEFAULT NULL COMMENT '经营范围',
  JYFWMSG varchar(900) DEFAULT NULL COMMENT '经营范围信息',
  STATE int(1) DEFAULT NULL COMMENT '状态(0未处理,1已分发,2已批复,3已许可)',
  REGETTIME varchar(19) DEFAULT NULL COMMENT '接收时间',
  ITEMTIME varchar(60) DEFAULT NULL COMMENT '推送时间',
  RESENDCODE varchar(64) DEFAULT NULL COMMENT '分发部门编码',
  RESENDTIME varchar(19) DEFAULT NULL COMMENT '分发时间',
  XKZMC varchar(60) DEFAULT NULL COMMENT '许可证名称',
  XKZH varchar(64) DEFAULT NULL COMMENT '许可证号',
  XKJG varchar(60) DEFAULT NULL COMMENT '许可机关',
  XKNR varchar(300) DEFAULT NULL COMMENT '许可内容',
  XKXXBM varchar(300) DEFAULT NULL COMMENT '许可信息编码',
  XKKSRQ varchar(19) DEFAULT NULL COMMENT '许可起始期限',
  XKJSRQ varchar(19) DEFAULT NULL COMMENT '许可结束期限',
  PWH varchar(90) DEFAULT NULL COMMENT '批文号',
  CZR varchar(90) DEFAULT NULL COMMENT '出资人',
  FRDB varchar(60) DEFAULT NULL COMMENT '法定代表人',
  JYCS varchar(300) DEFAULT NULL COMMENT '经营场所',
  HZSJ varchar(19) DEFAULT NULL COMMENT '核准时间',
  LRLX varchar(30) DEFAULT NULL COMMENT '录入类型',
  LSXXXKZ varchar(3000) DEFAULT NULL COMMENT '历史信息许可证',
  LSXXPW varchar(3000) DEFAULT NULL COMMENT '历史信息批文',
  SSPTTSQK varchar(3000) DEFAULT NULL COMMENT '商事平台推送情况',
  PRIMARY KEY (ID)
) COMMENT='商事平台_批文许可';

DROP TABLE IF EXISTS SSPT_SSZT;
CREATE TABLE SSPT_SSZT (
  ID bigint(18) NOT NULL AUTO_INCREMENT COMMENT '主键',
  ZCH varchar(64) DEFAULT NULL COMMENT '注册号',
  MC varchar(300) DEFAULT NULL COMMENT '商事主体名称',
  ITEMID varchar(64) DEFAULT NULL COMMENT '推送ID',
  ITEMTIME varchar(30) DEFAULT NULL COMMENT '推送时间',
  NUMBERTYPE varchar(32) DEFAULT NULL,
  BMBM varchar(64) DEFAULT NULL COMMENT '部门编码',
  BMMC varchar(60) DEFAULT NULL COMMENT '部门名称',
  JYFW varchar(300) DEFAULT NULL COMMENT '经营范围码',
  JYFWMSG varchar(900) DEFAULT NULL COMMENT '经营范围内容',
  ADDRESS varchar(300) DEFAULT NULL COMMENT '经营地址',
  STATE int(1) DEFAULT NULL COMMENT '状态(0待获取,1已获取,2已接收,3已退回)',
  REGETTIME varchar(60) DEFAULT NULL COMMENT '接收时间',
  REBACKTIME varchar(19) DEFAULT NULL COMMENT '退回时间',
  REBACKMSG varchar(900) DEFAULT NULL COMMENT '退回原因',
  SIGNDATA varchar(256) DEFAULT NULL COMMENT '签名值字段',
  SIGNCERT varchar(256) DEFAULT NULL COMMENT '证书序列号字段',
  PRIMARY KEY (ID)
);

DROP TABLE IF EXISTS SSYY;
CREATE TABLE SSYY (
  ID bigint(20) NOT NULL DEFAULT '0',
  TNAME varchar(300) DEFAULT NULL COMMENT '大类',
  NAME varchar(300) DEFAULT NULL COMMENT '小类',
  MSG longtext,
  C1 varchar(300) DEFAULT NULL COMMENT '热量(千卡)',
  C2 varchar(300) DEFAULT NULL COMMENT '硫胺素(毫克)',
  C3 varchar(300) DEFAULT NULL COMMENT '钙(毫克)',
  C4 varchar(300) DEFAULT NULL COMMENT '蛋白质(克)',
  C5 varchar(300) DEFAULT NULL COMMENT '核黄素(毫克)',
  C6 varchar(300) DEFAULT NULL COMMENT '镁(毫克)',
  C7 varchar(300) DEFAULT NULL COMMENT '脂肪(克)',
  C8 varchar(300) DEFAULT NULL COMMENT '烟酸(毫克)',
  C9 varchar(300) DEFAULT NULL COMMENT '铁(毫克)',
  C10 varchar(300) DEFAULT NULL COMMENT '碳水化合物(克)',
  C11 varchar(300) DEFAULT NULL COMMENT '维生素C(毫克)',
  C12 varchar(300) DEFAULT NULL COMMENT '锰(毫克)',
  C13 varchar(300) DEFAULT NULL COMMENT '膳食纤维(克)',
  C14 varchar(300) DEFAULT NULL COMMENT '维生素E(毫克)',
  C15 varchar(300) DEFAULT NULL COMMENT '锌(毫克)',
  C16 varchar(300) DEFAULT NULL COMMENT '维生素A(微克)',
  C17 varchar(300) DEFAULT NULL COMMENT '胆固醇(毫克)',
  C18 varchar(300) DEFAULT NULL COMMENT '铜(毫克)',
  C19 varchar(300) DEFAULT NULL COMMENT '胡罗卜素(微克)',
  C20 varchar(300) DEFAULT NULL COMMENT '钾(毫克)',
  C21 varchar(300) DEFAULT NULL COMMENT '磷(毫克)',
  C22 varchar(300) DEFAULT NULL COMMENT '视黄醇当量(微克)',
  C23 varchar(300) DEFAULT NULL COMMENT '钠(毫克)',
  C24 varchar(300) DEFAULT NULL COMMENT '硒(微克)',
  PRIMARY KEY (ID)
) COMMENT='膳食营养';

DROP TABLE IF EXISTS ZW_GKYJX;
CREATE TABLE ZW_GKYJX (
  ID bigint(20) NOT NULL,
  QUERYID varchar(300) DEFAULT NULL COMMENT '查询码',
  NAME varchar(30) DEFAULT NULL COMMENT '姓名',
  UNITNAME varchar(300) DEFAULT NULL COMMENT '单位',
  CARDNO varchar(64) DEFAULT NULL COMMENT '身份证号码',
  VTELEPHONE varchar(30) DEFAULT NULL COMMENT '联系电话',
  VEMAIL varchar(300) DEFAULT NULL COMMENT '电子邮箱',
  VADDRESS varchar(300) DEFAULT NULL COMMENT '联系地址',
  TITLE varchar(300) DEFAULT NULL COMMENT '标题',
  MSG varchar(3000) DEFAULT NULL COMMENT '内容',
  STATUS int(2) DEFAULT NULL COMMENT '状态',
  CREATETIME varchar(19) DEFAULT NULL COMMENT '申请时间',
  REPLYTIME varchar(19) DEFAULT NULL COMMENT '处理时间',
  REPLYRESULT varchar(3000) DEFAULT NULL COMMENT '处理结果',
  REPLYUSER varchar(30) DEFAULT NULL COMMENT '处理人',
  PRIMARY KEY (ID)
) COMMENT='信息公开意见箱';

DROP TABLE IF EXISTS ZW_JZXX;
CREATE TABLE ZW_JZXX (
  ID bigint(20) DEFAULT NULL,
  NAME varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '用户姓名',
  CARDTYPE varchar(30) DEFAULT NULL COMMENT '证件类型',
  CARDNO varchar(64) DEFAULT NULL COMMENT '证件号码',
  PHONE varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '联系电话',
  EMAIL varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '电子邮件',
  TITLE varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '信件主题',
  MSG varchar(3000) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '信件内容',
  STATUS int(10) DEFAULT NULL COMMENT '状态(0待受理,8已办结)',
  CREATETIME varchar(19) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建时间',
  REPLYTIME varchar(19) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '回复时间',
  REPLYRESULT varchar(3000) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '回复意见',
  REPLYUSER varchar(30) DEFAULT NULL COMMENT '回复人'
) COMMENT='局长信箱';

DROP TABLE IF EXISTS ZW_YSQGK;
CREATE TABLE ZW_YSQGK (
  ID bigint(20) NOT NULL,
  QUERYID varchar(300) DEFAULT NULL COMMENT '查询码',
  NAME varchar(30) DEFAULT NULL COMMENT '姓名/法人代表',
  UNITNAME varchar(100) DEFAULT NULL COMMENT '单位名称',
  DWJGDM varchar(30) DEFAULT NULL COMMENT '单位机构代码',
  DWYYZZ varchar(30) DEFAULT NULL COMMENT '单位营业执照注册号',
  CARDTYPE varchar(30) DEFAULT NULL COMMENT '证件类型',
  CARDNO varchar(64) DEFAULT NULL COMMENT '证件号码',
  VNAME varchar(30) DEFAULT NULL COMMENT '联系人/联系人电话',
  VPOST varchar(30) DEFAULT NULL COMMENT '邮政编码',
  VTELEPHONE varchar(30) DEFAULT NULL COMMENT '联系电话',
  VFAX varchar(30) DEFAULT NULL COMMENT '传真',
  VEMAIL varchar(100) DEFAULT NULL COMMENT '电子邮箱',
  VADDRESS varchar(300) DEFAULT NULL COMMENT '联系地址',
  CONTENTNAME varchar(300) DEFAULT NULL COMMENT '所需政府信息文件名称',
  CONTENTNO varchar(100) DEFAULT NULL COMMENT '所需政府信息文号',
  CONTENT varchar(3000) DEFAULT NULL COMMENT '所需政府信息描述',
  PURPOSE varchar(3000) DEFAULT NULL COMMENT '所需政府信息用途',
  PURPOSEFILE varchar(30) DEFAULT NULL COMMENT '证明材料',
  USERFREE varchar(30) DEFAULT NULL COMMENT '是否申请减免费用',
  USERFREEFILE varchar(30) DEFAULT NULL COMMENT '申请材料',
  INFOGET varchar(100) DEFAULT NULL COMMENT '提供政府信息的指定方式',
  INFOTO varchar(100) DEFAULT NULL COMMENT '获取政府信息的方式',
  OTHER varchar(2000) DEFAULT NULL COMMENT '若本机关无法按照指定方式提供所需信息，也可接受其他方式',
  MEMO varchar(2000) DEFAULT NULL COMMENT '备注',
  TYPE int(2) DEFAULT NULL COMMENT '类型(1公民,2机构,3企业法人,4社团组织,5其他)',
  STATUS int(2) DEFAULT NULL COMMENT '状态(0待受理,-1已作废,1待处理,8已办结)',
  CREATETIME varchar(19) DEFAULT NULL COMMENT '申请时间',
  ACCEPTTIME varchar(19) DEFAULT NULL COMMENT '受理时间',
  ACCEPTRESULT varchar(2000) DEFAULT NULL COMMENT '受理结果',
  ACCEPTUSER varchar(30) DEFAULT NULL COMMENT '受理人',
  REPLYTIME varchar(19) DEFAULT NULL COMMENT '处理时间',
  REPLYRESULT varchar(2000) DEFAULT NULL COMMENT '处理结果',
  REPLYUSER varchar(30) DEFAULT NULL COMMENT '处理人',
  PRIMARY KEY (ID)
) COMMENT='依申请公开';
