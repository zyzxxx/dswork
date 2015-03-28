/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2014/9/9 10:27:43                            */
/*==============================================================*/


drop table if exists DS_CMS_CATEGORY;

drop table if exists DS_CMS_PAGE;

drop table if exists DS_CMS_SITE;

drop table if exists DS_EP_ENTERPRISE;

drop table if exists DS_EP_USER;

/*==============================================================*/
/* Table: DS_CMS_CATEGORY                                       */
/*==============================================================*/
create table DS_CMS_CATEGORY
(
   ID                   bigint not null auto_increment comment '主键',
   PID                  bigint comment '父ID',
   SITEID               bigint comment '站点ID',
   NAME                 varchar(300) comment '栏目名称',
   FOLDER               varchar(300) comment '目录名称',
   STATUS               int comment '类型(0列表，1单页，2外链)',
   URL                  varchar(300) comment '链接',
   IMG                  varchar(300) comment '图片',
   VIEWSITE             varchar(300) comment '网站模板',
   SEQ                  varchar(300) comment '排序',
   PAGEVIEWSITE         varchar(300) comment '内容网站模板',
   primary key (ID)
);

alter table DS_CMS_CATEGORY comment '栏目';

/*==============================================================*/
/* Table: DS_CMS_PAGE                                           */
/*==============================================================*/
create table DS_CMS_PAGE
(
   ID                   bigint not null auto_increment comment '主键',
   SITEID               bigint comment '站点ID',
   CATEGORYID           bigint comment '栏目ID',
   FOLDER               varchar(300),
   TITLE                varchar(300) comment '标题',
   METAKEYWORDS         varchar(300) comment 'meta关键词',
   METADESCRIPTION      varchar(300) comment 'meta描述',
   SUMMARY              varchar(300) comment '摘要',
   RELEASETIME          varchar(19) comment '发布时间',
   IMG                  varchar(300) comment '图片',
   IMGTOP               int comment '焦点图(0否，1是)',
   PAGETOP              int comment '首页推荐(0否，1是)',
   CONTENT              text comment '内容',
   primary key (ID)
);

alter table DS_CMS_PAGE comment '内容';

/*==============================================================*/
/* Table: DS_CMS_SITE                                           */
/*==============================================================*/
create table DS_CMS_SITE
(
   ID                   bigint not null auto_increment comment '主键',
   QYBM                 varchar(300) comment '企业编码',
   NAME                 varchar(300) comment '站点名称',
   FOLDER               varchar(300) comment '目录名称',
   URL                  varchar(300) comment '链接',
   IMG                  varchar(300) comment '图片',
   VIEWSITE             varchar(300) comment '网站模板',
   METAKEYWORDS         varchar(300) comment 'meta关键词',
   METADESCRIPTION      varchar(300) comment 'meta描述',
   CONTENT              text comment '内容',
   primary key (ID)
);

alter table DS_CMS_SITE comment '站点';

/*==============================================================*/
/* Table: DS_EP_ENTERPRISE                                      */
/*==============================================================*/
create table DS_EP_ENTERPRISE
(
   ID                   bigint not null comment 'ID',
   NAME                 varchar(300) comment '企业名称',
   SSXQ                 varchar(30) comment '所属辖区',
   QYBM                 varchar(64) comment '编码',
   STATUS               int comment '状态',
   TYPE                 varchar(300) comment '类型',
   primary key (ID)
);

alter table DS_EP_ENTERPRISE comment '企业';

/*==============================================================*/
/* Table: DS_EP_USER                                            */
/*==============================================================*/
create table DS_EP_USER
(
   ID                   bigint not null comment '主键',
   QYBM                 varchar(64) comment '企业编码',
   ACCOUNT              varchar(64) comment '账号',
   PASSWORD             varchar(256) comment '密码',
   NAME                 varchar(30) comment '姓名',
   IDCARD               varchar(64) comment '身份证号',
   STATUS               int comment '状态',
   EMAIL                varchar(300) comment '电子邮件',
   MOBILE               varchar(30) comment '手机',
   PHONE                varchar(30) comment '电话',
   WORKCARD             varchar(64) comment '工作证号',
   CAKEY                varchar(1024) comment 'CA证书的KEY',
   CREATETIME           varchar(19) comment '创建时间',
   SSDW                 varchar(300) comment '所属单位',
   SSBM                 varchar(300) comment '所属部门',
   FAX                  varchar(30) comment '传真',
   primary key (ID)
);

alter table DS_EP_USER comment '企业用户';

alter table DS_CMS_CATEGORY add constraint FK_DS_CMS_CATEGORY foreign key (PID)
      references DS_CMS_CATEGORY (ID) on delete cascade on update cascade;

alter table DS_CMS_CATEGORY add constraint FK_DS_CMS_CATEGORY_SITE foreign key (SITEID)
      references DS_CMS_SITE (ID) on delete cascade on update cascade;

alter table DS_CMS_PAGE add constraint FK_DS_CMS_PAGE_SITE foreign key (SITEID)
      references DS_CMS_SITE (ID) on delete cascade on update cascade;

