/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2014-8-21 14:46:08                           */
/*==============================================================*/


drop table if exists DS_CMS_CATEGORY;

drop table if exists DS_CMS_PAGE;

drop table if exists DS_EP_ENTERPRISE;

drop table if exists DS_EP_TYPE;

drop table if exists DS_EP_USER;

/*==============================================================*/
/* Table: DS_CMS_CATEGORY                                       */
/*==============================================================*/
create table DS_CMS_CATEGORY
(
   ID                   bigint not null auto_increment comment '主键',
   PID                  bigint comment '父ID',
   QYBM                 varchar(300) comment '企业编码',
   NAME                 varchar(300) comment '栏目名称',
   FOLDER               varchar(300) comment '目录名称',
   STATUS               int comment '状态(0列表，1单页，2外链)',
   URL                  varchar(300) comment '链接',
   IMG                  varchar(300) comment '图片',
   VIEWSITE             varchar(300) comment '网站模板',
   VIEWAPP              varchar(300) comment 'APP模板',
   SEQ                  varchar(300) comment '排序',
   primary key (ID)
);

alter table DS_CMS_CATEGORY comment '栏目';

/*==============================================================*/
/* Table: DS_CMS_PAGE                                           */
/*==============================================================*/
create table DS_CMS_PAGE
(
   ID                   bigint not null auto_increment comment '主键',
   QYBM                 varchar(300) comment '企业编码',
   CATEGORYID           bigint comment '栏目ID',
   TITLE                VARCHAR(300) comment '标题',
   KEYWORDS             VARCHAR(300) comment '关键词',
   SUMMARY              VARCHAR(300) comment '摘要',
   CONTENT              text comment '内容',
   CREATETIME           VARCHAR(19) comment '创建时间',
   IMG                  VARCHAR(300) comment '图片',
   IMGTOP               int comment '焦点图(0否，1是)',
   PAGETOP              int comment '首页推荐(0否，1是)',
   VIEWSITE             varchar(300) comment '网站模板',
   VIEWAPP              varchar(300) comment 'APP模板',
   primary key (ID)
);

alter table DS_CMS_PAGE comment '网页文章';

/*==============================================================*/
/* Table: DS_EP_ENTERPRISE                                      */
/*==============================================================*/
create table DS_EP_ENTERPRISE
(
   ID                   bigint not null comment 'ID',
   NAME                 varchar(300) comment '企业名称',
   SSXQ                 varchar(100) comment '所属辖区',
   QYBM                 varchar(64) comment '企业编码',
   STATUS               int comment '企业状态',
   TYPEID               bigint comment '类型ID',
   primary key (ID)
);

alter table DS_EP_ENTERPRISE comment '企业';

/*==============================================================*/
/* Table: DS_EP_TYPE                                            */
/*==============================================================*/
create table DS_EP_TYPE
(
   ID                   bigint not null auto_increment comment 'ID',
   NAME                 varchar(300) comment '类型名',
   MEMO                 varchar(3000) comment '备注',
   primary key (ID)
);

alter table DS_EP_TYPE comment '企业类型';

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

alter table DS_EP_ENTERPRISE add constraint FK_DS_EP_ENTERPRISE_T foreign key (TYPEID)
      references DS_EP_TYPE (ID) on delete restrict on update restrict;

