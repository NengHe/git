/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2015/5/28 18:47:15                           */
/*==============================================================*/


drop table if exists t_activity;

drop table if exists t_activity_relate;

drop table if exists t_comment;

drop table if exists t_company;

drop table if exists t_contact;

drop table if exists t_friend;

drop table if exists t_img;

drop table if exists t_img_relate;

drop table if exists t_msg;

drop table if exists t_news;

drop table if exists t_news_relate;

drop table if exists t_orgnization;

drop table if exists t_orgnization_request;

drop table if exists t_permission;

drop table if exists t_person;

drop table if exists t_reply;

drop table if exists t_request;

drop table if exists t_resource;

drop table if exists t_role;

drop table if exists t_title;

drop table if exists t_user;

drop table if exists t_user_activity;

drop table if exists t_user_role;

drop table if exists t_user_title;



--db　changes report:
--07-03
alter table `icp`.`t_activity` 
   change `col_activity_issue_org_type` `col_activity_issue_org_type` varchar(1) character set gb2312 collate gb2312_chinese_ci NULL  comment '1-个人，2-组织,3-系统'
--07-06
alter table `icp`.`t_activity` 
   add column `col_activity_content_detail_url` varchar(100) NULL COMMENT '活动内容详情h5页面地址' after `col_activity_content`,
   change `col_activity_breif` `col_activity_brief` varchar(300) character set gb2312 collate gb2312_chinese_ci NULL  comment '活动简讯', 
   change `col_activity_issue_org_id` `col_activity_issue_org_id` int(11) NULL  comment '发布方id,对应t_org表中的org_id字段'
ALTER TABLE `icp`.`t_activity`     
	CHANGE `col_activity_start_time` `col_activity_start_time` DATETIME NULL  COMMENT '活动开始时间',     
	CHANGE `col_activity_end_time` `col_activity_end_time` DATETIME NULL  COMMENT '活动结束时间';
alter table `icp`.`t_org` 
   change `col_org_intro` `col_org_intro` varchar(1000) character set gb2312 collate gb2312_chinese_ci NULL  comment '组织简介'
--07-07
UPDATE `icp`.`t_resource` SET `col_resource_parent_id`='0',`col_resouce_label`='机构管理' WHERE `resource_id`='13';
UPDATE `icp`.`t_resource` SET `col_resource_parent_id`='13' WHERE `resource_id`='5';
UPDATE `icp`.`t_resource` SET `col_resource_parent_id`='13' WHERE `resource_id`='7';
INSERT INTO `icp`.`t_permission`(`role_res_id`,`role_id`,`resource_id`,`permission`,`create_time`) VALUES ( NULL,'1','13',NULL,NULL);
UPDATE `icp`.`t_resource` SET `col_del_flag`='0' WHERE `resource_id`='13';
UPDATE `icp`.`t_resource` SET `col_resource_sort`='00100' WHERE `resource_id`='13';
UPDATE `icp`.`t_resource` SET `col_resource_sort`='00101' WHERE `resource_id`='5';
UPDATE `icp`.`t_resource` SET `col_resource_sort`='00102' WHERE `resource_id`='7';
INSERT INTO `icp`.`t_permission`(`role_res_id`,`role_id`,`resource_id`,`permission`,`create_time`) VALUES ( NULL,'1','14',NULL,NULL);
UPDATE `icp`.`t_permission` SET `permission`='auditPass,auditReject' WHERE `role_res_id`='17';
UPDATE `icp`.`t_resource` SET `col_resource_parent_id`='13',`col_resource_name`='orgReq',`col_resouce_label`='入会申请',`col_resource_sort`='00103' WHERE `resource_id`='14';

INSERT INTO `icp`.`t_org_join_req`(`org_join_req_id`,`col_org_id`,`col_req_user_id`,`col_request_memo`,`col_req_auth_status`,`col_create_time`) VALUES ( NULL,'1','1','快放我进去','0',NULL);

--07-08
UPDATE `icp`.`t_permission` SET `permission`='queryOrgReqs,auditPass,auditReject,removeOrgReq' WHERE `role_res_id`='17';
UPDATE `icp`.`t_permission` SET `permission`='createActivity,removeActivity,updateActivity,queryActivities,showActParticipants' WHERE `role_res_id`='12';
alter table `icp`.`t_user_act` 
   change `col_auth_status` `col_auth_status` varchar(1) character set gb2312 collate gb2312_chinese_ci default '0' NOT NULL comment '状态(0-待确认，1-已通过,2-已拒绝)'
INSERT INTO `icp`.`t_resource`(`resource_id`,`col_resource_parent_id`,`col_resource_name`,`col_resouce_label`,`col_resource_describe`,`col_resource_attr`,`col_resource_attr_desc`,`col_resource_sort`,`col_create_time`,`col_create_by`,`col_last_update_time`,`col_last_update_by`,`col_del_flag`) VALUES ( NULL,'13','myOrgMember','本机构成员管理',NULL,NULL,NULL,'00104',NULL,NULL,NULL,NULL,'0');
INSERT INTO `icp`.`t_permission`(`role_res_id`,`role_id`,`resource_id`,`permission`,`create_time`) VALUES ( NULL,'1','15','queryMyOrgMembers,createMyOrgMember,updateMyOrgMember,',NULL);
UPDATE `icp`.`t_resource` SET `col_resource_sort`='00104' WHERE `resource_id`='14';
UPDATE `icp`.`t_resource` SET `col_resource_sort`='00103' WHERE `resource_id`='15';

--07-09
INSERT INTO `icp`.`t_resource`(`resource_id`,`col_resource_parent_id`,`col_resource_name`,`col_resouce_label`,`col_resource_describe`,`col_resource_attr`,`col_resource_attr_desc`,`col_resource_sort`,`col_create_time`,`col_create_by`,`col_last_update_time`,`col_last_update_by`,`col_del_flag`) VALUES ( NULL,'0','','新闻管理',NULL,NULL,NULL,'00400',NULL,NULL,NULL,NULL,'0');
UPDATE `icp`.`t_resource` SET `col_resource_parent_id`='16' WHERE `resource_id`='9';
UPDATE `icp`.`t_resource` SET `col_resource_parent_id`='16' WHERE `resource_id`='8';
UPDATE `icp`.`t_resource` SET `col_resource_sort`='00401' WHERE `resource_id`='9';
UPDATE `icp`.`t_resource` SET `col_resource_sort`='00402' WHERE `resource_id`='8';
INSERT INTO `icp`.`t_permission`(`role_res_id`,`role_id`,`resource_id`,`permission`,`create_time`) VALUES ( NULL,'1','16',NULL,NULL);
UPDATE `icp`.`t_resource` SET `col_resource_parent_id`='13',`col_resource_sort`='00105' WHERE `resource_id`='6';
UPDATE `icp`.`t_resource` SET `col_resource_sort`='00103' WHERE `resource_id`='15';
UPDATE `icp`.`t_resource` SET `col_resource_sort`='00104' WHERE `resource_id`='14';
INSERT INTO `icp`.`t_resource`(`resource_id`,`col_resource_parent_id`,`col_resource_name`,`col_resouce_label`,`col_resource_describe`,`col_resource_attr`,`col_resource_attr_desc`,`col_resource_sort`,`col_create_time`,`col_create_by`,`col_last_update_time`,`col_last_update_by`,`col_del_flag`) VALUES ( NULL,'0',NULL,'活动管理',NULL,NULL,NULL,'00600',NULL,NULL,NULL,NULL,'0');
UPDATE `icp`.`t_resource` SET `col_resource_parent_id`='17',`col_resource_sort`='00602' WHERE `resource_id`='10';
UPDATE `icp`.`t_resource` SET `col_resource_parent_id`='17',`col_resource_sort`='00601' WHERE `resource_id`='12';
 INSERT INTO `icp`.`t_permission`(`role_res_id`,`role_id`,`resource_id`,`permission`,`create_time`) VALUES ( NULL,'1','17',NULL,NULL);
 INSERT INTO `icp`.`t_position`(`position_id`,`col_pos_org_id`,`col_pos_name`,`col_pos_level`,`create_time`,`col_del_flag`) VALUES ( NULL,'2','神经病','1',NULL,'0');
UPDATE `icp`.`t_permission` SET `permission`='queryNewsTypes,createNewsType,updateNewsType,removeNewsType' WHERE `role_res_id`='8';
alter table `icp`.`t_act_comment` 
   change `col_create_time` `col_comment_create_time` datetime NULL  comment '创建时间'
alter table `icp`.`t_comment_reply` 
   change `col_create_time` `col_reply_create_time` datetime NULL  comment '创建时间'
UPDATE `icp`.`t_permission` SET `permission`='createActivity,removeActivity,updateActivity,queryActivities,showActParticipants,showActComments' WHERE `role_res_id`='12';
alter table `icp`.`t_act_comment` 
   add column `col_comment_show_status` int(1) DEFAULT '1' NULL COMMENT '1-显示，2-屏蔽' after `col_comment_content`

--07-10
UPDATE `icp`.`t_permission` SET `permission`='queryActTypes,createActType,updateActType,removeActType' WHERE `role_res_id`='10';  
INSERT INTO `icp`.`t_resource`(`resource_id`,`col_resource_parent_id`,`col_resource_name`,`col_resouce_label`,`col_resource_describe`,`col_resource_attr`,`col_resource_attr_desc`,`col_resource_sort`,`col_create_time`,`col_create_by`,`col_last_update_time`,`col_last_update_by`,`col_del_flag`) VALUES ( NULL,'0',NULL,'系统管理',NULL,NULL,NULL,'00700',NULL,NULL,NULL,NULL,'0');
UPDATE `icp`.`t_resource` SET `col_resource_name`='adminUser',`col_resouce_label`='管理员管理',`col_resource_sort`='00702' WHERE `resource_id`='19';
UPDATE `icp`.`t_resource` SET `col_resource_parent_id`='18',`col_resource_sort`='00701' WHERE `resource_id`='3';
UPDATE `icp`.`t_resource` SET `col_resource_parent_id`='18',`col_resource_sort`='00702',col_resouce_label`='管理员管理' WHERE `resource_id`='2';
INSERT INTO `icp`.`t_permission`(`role_res_id`,`role_id`,`resource_id`,`permission`,`create_time`) VALUES ( NULL,'1','18',NULL,NULL);
UPDATE `icp`.`t_permission` SET `permission`='createAdminUser,removeAdminUser,updateAdminUser,queryAdminUsers' WHERE `role_res_id`='2';
   
--07-13
UPDATE t_role SET parent_id=0,role_name='机构管理员',role_desc='',del_flag='0' WHERE role_id=1;
UPDATE t_role SET parent_id=0,role_name='系统管理员',role_desc='',del_flag='0' WHERE role_id=2;
DELETE FROM `icp`.`t_resource` WHERE `resource_id`='1';
DELETE FROM `icp`.`t_resource` WHERE `resource_id`='4';
DELETE FROM `icp`.`t_permission` WHERE `role_res_id`='1';
DELETE FROM `icp`.`t_permission` WHERE `role_res_id`='4';
DELETE FROM `icp`.`t_permission` WHERE `role_res_id`='13';
DELETE FROM `icp`.`t_permission` WHERE `role_res_id`='14';
DELETE FROM `icp`.`t_permission` WHERE `role_res_id`='15';
UPDATE t_permission SET role_id=2 WHERE role_id=1;
UPDATE `icp`.`t_resource` SET `col_resource_attr`='createAdminUser,removeAdminUser,updateAdminUser,queryAdminUsers' WHERE `resource_id`='2';
UPDATE `icp`.`t_resource` SET `col_resource_attr`='createRole,removeRole,updateRole,queryRole,allocatePermission' WHERE `resource_id`='3';
UPDATE `icp`.`t_resource` SET `col_resource_attr`='queryOrg,createOrg,updateOrg' WHERE `resource_id`='5';
UPDATE `icp`.`t_resource` SET `col_resource_attr`='queryPositions' WHERE `resource_id`='6';
UPDATE `icp`.`t_resource` SET `col_resource_attr`='createOrgMember,updateOrgMember,queryOrgMember' WHERE `resource_id`='7';
UPDATE `icp`.`t_resource` SET `col_resource_attr`='queryNewsTypes,createNewsType,updateNewsType,removeNewsType' WHERE `resource_id`='8';
UPDATE `icp`.`t_resource` SET `col_resource_attr`='createNews,removeNews,updateNews,queryNews' WHERE `resource_id`='9';
UPDATE `icp`.`t_resource` SET `col_resource_attr`='queryActTypes,createActType,updateActType,removeActType' WHERE `resource_id`='10';
UPDATE `icp`.`t_resource` SET `col_resource_attr`='createActivity,removeActivity,updateActivity,queryActivities,showActParticipants,showActComments' WHERE `resource_id`='12';
UPDATE `icp`.`t_resource` SET `col_resource_attr`='queryOrgReqs,auditPass,auditReject,removeOrgReq' WHERE `resource_id`='14';
UPDATE `icp`.`t_resource` SET `col_resource_attr`='queryMyOrgMembers,createMyOrgMember,updateMyOrgMember' WHERE `resource_id`='15';

--07-14
UPDATE `icp`.`t_resource` SET `col_resource_attr`='createOrgMember,updateOrgMember,queryOrgMember,addOrgMember' WHERE `resource_id`='7';
UPDATE `icp`.`t_resource` SET `col_resource_attr`='queryMyOrgMembers,createMyOrgMember,updateMyOrgMember,addMyOrgMember' WHERE `resource_id`='15';
UPDATE `icp`.`t_permission` SET `permission`='queryMyOrgMembers,createMyOrgMember,updateMyOrgMember,addMyOrgMember' WHERE `role_res_id`='18';
UPDATE `icp`.`t_permission` SET `permission`='createOrgMember,updateOrgMember,queryOrgMember,addOrgMember' WHERE `role_res_id`='7';
UPDATE `icp`.`t_permission` SET `permission`='createPosition,removePosition,updatePosition,queryPositions' WHERE `role_res_id`='6';

alter table `icp`.`t_position` 
   add column `col_pos_show_detail_inner` varchar(1) DEFAULT '1' NULL COMMENT '对内显示详情（1-显示，2-隐藏）' after `col_pos_level`, 
   add column `col_pos_show_detail_outter` varchar(1) DEFAULT '1' NULL COMMENT '对外显示详情（1-显示，2-隐藏）' after `col_pos_show_detail_inner`
-------------------------------------------------------

--07-15
alter table `icp`.`t_role` 
   add column `role_type` varchar(1) NULL COMMENT '角色类型（1-机构，2-系统）' after `role_desc`
UPDATE t_role SET role_type=1 WHERE role_id=1;
UPDATE t_role SET role_type=2 WHERE role_id=2;

--07-16
alter table `icp`.`t_org` 
   change `col_org_type` `col_org_type` varchar(1) character set gb2312 collate gb2312_chinese_ci NULL  comment '组织类型（1-总部，2-分部）'
alter table `icp`.`t_org` 
   change `col_org_type` `col_org_type` varchar(1) character set gb2312 collate gb2312_chinese_ci NULL  comment '组织类型（1-券商，2-银行）'
   UPDATE t_org SET col_org_type=1;
--------------------------------------------以上已同步到测试环境

--07-21
ALTER TABLE t_act_comment DROP col_user_name;
ALTER TABLE t_comment_reply  DROP  col_cur_reply_user_name;

--07-23
UPDATE `icp`.`t_permission` SET `permission`='queryOrgs,createOrg,updateOrg' WHERE `role_res_id`='5';
UPDATE `icp`.`t_permission` SET `permission`='createRole,removeRole,updateRole,queryRoles,allocatePermission' WHERE `role_res_id`='3';

--07-27
ALTER TABLE `ICP`.`t_news` ADD COLUMN `col_news_oriented_type` VARCHAR(1) NOT NULL COMMENT '1-组织成员，2-所有人（参考值来自t_dict）' AFTER `news_id`; 

   alter table `icp`.`t_dict` 
   change `col_dict_class` `col_dict_class` varchar(2) character set utf8 collate utf8_general_ci default '' NOT NULL comment '1-机构类型，2-管理员类型，3-角色类型，4-活动面向类型,5-IM配置,6-新闻面向类型'
   
   INSERT INTO `icp`.`t_dict`(`dict_id`,`col_dict_class`,`col_dict_key`,`col_dict_val`,`col_dict_text`,`col_dict_create_time`,`col_dict_create_by`) VALUES ( NULL,'6','newsOrientedType','1','组织成员',NULL,NULL);
   INSERT INTO `icp`.`t_dict`(`dict_id`,`col_dict_class`,`col_dict_key`,`col_dict_val`,`col_dict_text`,`col_dict_create_time`,`col_dict_create_by`) VALUES ( NULL,'6','newsOrientedType','2','所有人',NULL,NULL);

 DELETE FROM `icp`.`t_app_lable_index` WHERE col_label_class=1 and `col_label_text`='组织';

---------------------------------------------忘记有无更新了
 
 
--07-31
alter table `t_admin_user` 
   change `col_admin_user_password` `col_admin_user_password` varchar(64) character set gb2312 collate gb2312_chinese_ci default '' NULL  comment '密码';
UPDATE `t_admin_user` SET `col_admin_user_password`='MZmo6Ae2pVRrQ0qRVviEygLpH+bBUcJ8anzlsY3Wn1cj0PvSxVTC7xh36g+cRdCl' WHERE `admin_user_id`='1';
---------------------------------------------已经更新到测试服务器



--08-05
alter table `icp`.`t_activity` 
   add column `col_activity_enroll_deadline` timestamp NULL COMMENT '活动报名截止时间' after `col_activity_end_time`, 
   add column `col_activity_auditable_flag` varchar(1) DEFAULT '1' NULL COMMENT '1-需要审核，2-不需要审核' after `col_activity_sharable_flag`, 
   add column `col_activity_need_pay_flag` varchar(1) DEFAULT '1' NULL COMMENT '1-需要付款，2-不需要付款' after `col_activity_auditable_flag`
    
--08-06
ALTER DATABASE ICP CHARACTER SET = utf8;

--08-11
alter table `icp`.`t_app_user` 
   change `col_friend_invite` `col_friend_invite` int(1) default '0' NULL  comment '是否接受好友申请，0 for no,1 for yes'

   
--08-13
alter table `t_resource_operation` 
   change `col_resource_operation_id` `col_resource_operation_id` int(11) NOT NULL comment '主键', 
   change `col_resource_id` `col_resource_id` int(11) NULL  comment 't_resource外键', 
   change `col_operation_id` `col_operation_id` int(11) NULL  comment 't_operation外键', 
   change `col_operation_name` `col_operation_name` varchar(150) character set utf8 collate utf8_general_ci NULL  comment '操作名称，便于页面显示', 
   change `col_del_flag` `col_del_flag` varchar(3) character set utf8 collate utf8_general_ci NULL  comment '1-软删除',
   add primary key(`col_resource_operation_id`);
alter table `t_resource_operation` 
   change `col_resource_operation_id` `col_resource_operation_id` int(11) NOT NULL AUTO_INCREMENT comment '主键', 
   change `col_del_flag` `col_del_flag` varchar(3) character set utf8 collate utf8_general_ci default '0' NULL  comment '1-软删除';

alter table `t_news` 
   add column `col_news_title_img_url` varchar(150) NULL COMMENT '新闻标题图片url' after `col_news_title`
alter table `t_user_act` 
   change `col_auth_status` `col_auth_status` varchar(1) character set gb2312 collate gb2312_chinese_ci default '0' NOT NULL comment '状态(0-申请待审，1-审核通过,2-报名取消，3-取消待审)'
   
---------------------------------------------已经更新到测试服务器
------------------------
--08-17
alter table `t_activity` 
   add column `col_activity_title_img_url` varchar(150) NULL COMMENT '活动标题图片url' after `col_activity_title`, 
   add column `col_activity_brief_img_url` varchar(150) NULL COMMENT '活动简讯图片url' after `col_activity_brief`
alter table `t_user_act` 
   change `col_payment_status` `col_payment_status` varchar(1) character set gb2312 collate gb2312_chinese_ci default '0' NOT NULL comment '支付状态（0-未付款，1-已付款，2-已退款）'

alter table `icp`.`t_pay_history` 
   change `col_act_id` `col_user_act_id` int(11) NOT NULL comment '活动参与ID，t_user_act外键', 
   change `col_member_id` `col_app_user_id` int(11) NOT NULL comment '前端应用用户ID，t_app_user外键'



















alter table `icp`.`t_user_position` 
   add column `state` varchar(1) DEFAULT '1' NULL COMMENT '状态（1-在职，2-离职）' after `position_id`
alter table `icp`.`t_user` 
   change `show_detail_inner` `show_detail_inner` varchar(1) character set gb2312 collate gb2312_chinese_ci default '0' NULL  comment '是否内部显示详细信息(0-不显示，1-显示)', 
   change `show_detail_outter` `show_detail_outter` varchar(1) character set gb2312 collate gb2312_chinese_ci default '0' NULL  comment '是否外部显示详细信息(0-不显示，1-显示)'
alter table `icp`.`t_user_position` 
   change `person_position_id` `user_position_id` int(11) NOT NULL AUTO_INCREMENT comment '主键'
alter table `icp`.`t_person` 
   change `person_mobile` `person_mobile` varchar(11) NULL  comment '手机'
alter table `icp`.`t_user` 
   add column `type` varchar(1) NOT NULL COMMENT '1-app用户，2-后台机构管理用户，3-后台系统管理用户' after `password`,
   change `password` `password` varchar(50) character set gb2312 collate gb2312_chinese_ci default '' NULL  comment '密码'

   
/*==============================================================*/
/* Table: t_activity                                            */
/*==============================================================*/
create table t_activity
(
   activity_id          int not null auto_increment comment '主键',
   type                 varchar(1) comment '0-会议，1-项目，2-交流，3-合作',
   orgnizer             varchar(50) comment '主办方',
   title                varchar(50) comment '活动名称',
   start_time           varchar(50) comment '活动开始时间',
   end_time             varchar(50) comment '活动结束时间',
   place                varchar(50) comment '活动地点',
   background           varchar(1000) comment '活动背景',
   agenda               varchar(1000) comment '活动议程',
   guest                varchar(200) comment '活动嘉宾',
   content              varchar(1000) comment '活动内容',
   create_time          datetime comment '创建时间',
   create_by            int comment '创建人用户id',
   last_update_time     datetime comment '最近更新时间',
   last_update_by       int comment '更新人用户id',
   del_flag             varchar(1) default '0' comment '删除标识',
   primary key (activity_id)
);

alter table t_activity comment '活动';

/*==============================================================*/
/* Table: t_activity_relate                                     */
/*==============================================================*/
create table t_activity_relate
(
   id                   int not null auto_increment comment '主键',
   activity_id          int comment '活动id',
   issuer_id            int comment '发布方id',
   issuer_type          varchar(1) comment '0-组织,1-公司',
   create_time          datetime comment '创建时间',
   primary key (id)
);

alter table t_activity_relate comment '活动-发布方关联';

/*==============================================================*/
/* Table: t_comment                                             */
/*==============================================================*/
create table t_comment
(
   comment_id           int not null auto_increment comment '主键',
   user_id              int comment '用户id',
   activity_id          int comment '活动id',
   content              varchar(1000) comment '评价内容',
   create_time          datetime comment '创建时间',
   primary key (comment_id)
);

alter table t_comment comment '活动评论';

/*==============================================================*/
/* Table: t_company                                             */
/*==============================================================*/
create table t_company
(
   company_id           int not null auto_increment comment '主键',
   company_name         varchar(50) comment '公司名称',
   company_intro        varchar(200) comment '公司简介',
   create_time          datetime comment '创建时间',
   del_flag             varchar(1) default '0' comment '1-软删除',
   primary key (company_id)
);

alter table t_company comment '公司';

/*==============================================================*/
/* Table: t_contact                                             */
/*==============================================================*/
create table t_contact
(
   relate_id            int comment '关联个人、公司或组织id',
   contact_type         int comment '0-个人联系方式，1-公司联系方式，2-组织联系方式',
   contact_name         varchar(50) comment '联系方式名称',
   contact_value        varchar(200) comment '联系方式内容',
   contact_desc         varchar(100) comment '联系类型说明',
   create_time          datetime comment '创建时间'
);

alter table t_contact comment '联系方式表';

/*==============================================================*/
/* Table: t_friend                                              */
/*==============================================================*/
create table t_friend
(
   id                   int not null auto_increment comment '主键',
   source_id            int comment 'source_id',
   target_id            int comment 'target_id',
   note_name            varchar(50) comment '备注名称',
   create_time          datetime comment '创建时间',
   del_flag             varchar(1) default '0' comment '1-软删除',
   primary key (id)
);

alter table t_friend comment '用户-用户关联';

/*==============================================================*/
/* Table: t_img                                                 */
/*==============================================================*/
create table t_img
(
   img_id               varchar(32) not null comment '主键',
   path                 varchar(100) comment '存储路径',
   compress_path        varchar(100) comment '压缩存储路径',
   width                double comment '宽',
   height               double comment '高',
   creat_time           datetime comment '创建时间',
   primary key (img_id)
);

alter table t_img comment '图片';

/*==============================================================*/
/* Table: t_img_relate                                          */
/*==============================================================*/
create table t_img_relate
(
   id                   int not null auto_increment comment '主键',
   img_id               int comment '图片id',
   relate_id            int comment '关联id',
   relate_type          varchar(2) comment '0-用户头像，1-组织头像，2-新闻首图，3-新闻图片，4-活动概况，5-活动背景，6-活动议程，7-嘉宾，8-具体内容',
   create_time          datetime comment '创建时间',
   primary key (id)
);

alter table t_img_relate comment '用户、新闻、活动等图片关联';

/*==============================================================*/
/* Table: t_msg                                                 */
/*==============================================================*/
create table t_msg
(
   id                   int not null auto_increment comment '主键',
   type                 varchar(1) comment '消息类型（0-好友申请，1-入会申请，2-系统消息）',
   source_type          varchar(1) comment '消息来源类型(0-个人，1-组织，2-公司)',
   source_id            int comment '用户id',
   target_id            int comment '用户或组织id',
   create_time          datetime comment '创建时间',
   primary key (id)
);

alter table t_msg comment '消息';

/*==============================================================*/
/* Table: t_news                                                */
/*==============================================================*/
create table t_news
(
   news_id              int not null auto_increment comment '主键',
   source               varchar(50) comment '新闻来源',
   title                varchar(50) comment '新闻标题',
   content              varchar(1000) comment '活动内容',
   create_time          datetime comment '创建时间',
   create_by            int comment '创建人用户id',
   last_update_time     datetime comment '最近更新时间',
   last_update_by       int comment '更新人用户id',
   del_flag             varchar(1) default '0' comment '删除标识',
   primary key (news_id)
);

alter table t_news comment '新闻';

/*==============================================================*/
/* Table: t_news_relate                                         */
/*==============================================================*/
create table t_news_relate
(
   id                   int not null auto_increment comment '主键',
   news_id              int comment '新闻id',
   issuer_id            int comment '发布方id',
   issuer_type          varchar(1) comment '0-组织,1-公司',
   create_time          datetime comment '创建时间',
   primary key (id)
);

alter table t_news_relate comment '新闻-发布方关联';

/*==============================================================*/
/* Table: t_orgnization                                         */
/*==============================================================*/
create table t_orgnization
(
   orgnization_id       int not null auto_increment comment '主键',
   parent_id            int comment '父组织id',
   orgnization_type     varchar(1) comment '0-支部，1-分部',
   orgnization_name     varchar(50) comment '组织名称',
   orgnization_note     varchar(200) comment '会长寄语',
   orgnization_intro    varchar(200) comment '组织简介',
   create_time          datetime comment '创建时间',
   del_flag             varchar(1) default '0' comment '1-软删除',
   primary key (orgnization_id)
);

alter table t_orgnization comment '组织';

/*==============================================================*/
/* Table: t_orgnization_request                                 */
/*==============================================================*/
create table t_orgnization_request
(
   request_id           int not null auto_increment comment '主键',
   orgnization_id       int comment '组织id',
   user_id              int comment '请求人用户id',
   application          varchar(500) comment '入会申请',
   create_time          datetime comment '创建时间',
   status               varchar(1) default '0' comment '0-待审核，1-已通过，2-已拒绝',
   primary key (request_id)
);

alter table t_orgnization_request comment '会员申请';

/*==============================================================*/
/* Table: t_permission                                          */
/*==============================================================*/
create table t_permission
(
   id                   int not null auto_increment comment '主键',
   role_id              int comment '角色id',
   resource_id          int comment '资源id',
   create_time          datetime comment '创建时间',
   primary key (id)
);

alter table t_permission comment '权限';

/*==============================================================*/
/* Table: t_person                                              */
/*==============================================================*/
create table t_person
(
   person_id            int not null auto_increment comment '主键',
   user_id              varchar(32) comment '关联登录用户id',
   labels               varchar(100) comment '个人标签',
   name                 varchar(50) comment '姓名',
   show_request         varchar(1) default '1' comment '显示好友申请消息（0-不显示，1-显示）',
   show_detail_inner    varchar(1) comment '是否内部显示详细信息(0-不显示，1-显示)',
   show_detail_outter   varchar(1) comment '是否外部显示详细信息(0-不显示，1-显示)',
   create_time          datetime comment '创建时间',
   del_flag             varchar(1) default '0' comment '1-软删除',
   primary key (person_id)
);

alter table t_person comment '个人信息';

/*==============================================================*/
/* Table: t_reply                                               */
/*==============================================================*/
create table t_reply
(
   reply_id             int not null auto_increment comment '主键',
   comment_id           int comment '用户id',
   pre_user_id          int comment '上级回复人id',
   cur_user_id          int comment '当前回复人id',
   content              varchar(1000) comment '回复内容',
   create_time          datetime comment '创建时间',
   primary key (reply_id)
);

alter table t_reply comment '评论回复';

/*==============================================================*/
/* Table: t_request                                             */
/*==============================================================*/
create table t_request
(
   request_id           int not null auto_increment comment '主键',
   source_id            int comment '申请人用户id',
   target_id            int comment '接收人用户id',
   remark               varchar(100) comment '备注信息',
   create_time          datetime comment '创建时间',
   status               varchar(1) default '0' comment '0-待审核，1-已通过，2-已拒绝',
   primary key (request_id)
);

alter table t_request comment '好友申请';

/*==============================================================*/
/* Table: t_resource                                            */
/*==============================================================*/
create table t_resource
(
   resource_id          int not null auto_increment comment '主键',
   parent_id            int comment '父资源id',
   resource_name        varchar(50) comment '资源名',
   resource_desc        varchar(100) comment '资源描述',
   resource_mark        varchar(50) comment '资源符号',
   type                 varchar(1) comment '0-菜单，1-功能',
   create_time          datetime comment '创建时间',
   create_by            int comment '创建人用户id',
   last_update_time     datetime comment '最近更新时间',
   last_update_by       int comment '更新人用户id',
   del_flag             varchar(1) default '0' comment '1-软删除',
   primary key (resource_id)
);

alter table t_resource comment '资源';

/*==============================================================*/
/* Table: t_role                                                */
/*==============================================================*/
create table t_role
(
   role_id              int not null auto_increment comment '主键',
   role_name            varchar(50) comment '角色名',
   role_desc            varchar(50) comment '角色说明',
   create_time          datetime comment '创建时间',
   del_flag             varchar(1) default '0' comment '1-软删除',
   primary key (role_id)
);

alter table t_role comment '角色';

/*==============================================================*/
/* Table: t_title                                               */
/*==============================================================*/
create table t_title
(
   title_id             int not null auto_increment comment '主键',
   orgnize_id           int comment '机构id',
   orgnize_type         varchar(1) comment '机构类型(0-公司，1-组织)',
   position_name        varchar(50) comment '职位名称',
   show_detail_inner    varchar(1) comment '是否内部显示详细信息(0-不显示，1-显示)',
   show_detail_outter   varchar(1) comment '是否外部显示详细信息(0-不显示，1-显示)',
   create_time          datetime comment '创建时间',
   del_flag             varchar(1) default '0' comment '删除标识',
   primary key (title_id)
);

alter table t_title comment '职务';

/*==============================================================*/
/* Table: t_user                                                */
/*==============================================================*/
create table t_user
(
   user_id              int not null auto_increment comment '主键',
   username             varchar(50) comment '用户名',
   password             varchar(50) comment '密码',
   create_time          datetime comment '创建时间',
   del_flag             varchar(1) default '0' comment '1-软删除',
   primary key (user_id)
);

alter table t_user comment '用户登录账号表';

/*==============================================================*/
/* Table: t_user_activity                                       */
/*==============================================================*/
create table t_user_activity
(
   id                   int not null auto_increment comment '主键',
   user_id              int comment '用户id',
   activity_id          int comment '活动id',
   status               varchar(1) default '0' comment '状态(0-待确认，1-已通过)',
   create_time          datetime comment '创建时间',
   primary key (id)
);

alter table t_user_activity comment '用户-活动关联';

/*==============================================================*/
/* Table: t_user_role                                           */
/*==============================================================*/
create table t_user_role
(
   id                   int not null auto_increment comment '主键',
   user_id              int comment '用户id',
   role_id              int comment '角色id',
   create_time          datetime comment '创建时间',
   primary key (id)
);

alter table t_user_role comment '用户角色关联';

/*==============================================================*/
/* Table: t_user_title                                          */
/*==============================================================*/
create table t_user_title
(
   id                   int not null auto_increment comment '主键',
   user_id              int comment '用户id',
   title_id             int comment '职务id',
   create_time          datetime comment '创建时间',
   del_flag             varchar(1) default '0' comment '1-软删除',
   primary key (id)
);

alter table t_user_title comment '用户-职务关联';

