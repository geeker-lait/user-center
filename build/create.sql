create table t_account
(
  id            bigint auto_increment
  comment 'id主键'
    primary key,
  accountName      varchar(50) default '' not null
  comment '用户名',
  accountName       varchar(20) default '' not null
  comment '登陆账号（手机号或邮箱）',
  manager_type  int default '0'        not null
  comment '管理员类别(0普通管理员 1超级管理员)',
  master        int default '0'        not null
  comment '是否被授权管理员(0否，1是)',
  register_type int default '0'        not null
  comment '注册类型（0前台注册 1后台创建 2用户中心创建）',
  lock_time     int default '0'        not null
  comment '锁定次数',
  is_lock       tinyint(1) default '0' not null
  comment '是否被锁定',
  state         int default '0'        not null
  comment '状态(0正常 1禁用)',
  create_time   datetime               null
  comment '创建时间',
  constraint accountName
  unique (accountName),
  constraint accountName
  unique (accountName)
)
  comment '账号表'
  engine = InnoDB;

create table t_account_password
(
  id         bigint auto_increment
  comment '主键ID'
    primary key,
  account_id bigint default '0'     not null
  comment '账号id',
  accountName   varchar(20) default '' not null
  comment '用户名',
  password   varchar(20) default '' not null
  comment '账号密码',
  constraint account_id_username
  unique (account_id, accountName)
)
  comment '账号密码表'
  engine = InnoDB;

create table t_account_role
(
  id         bigint auto_increment
  comment '主键ID'
    primary key,
  account_id bigint default '0' not null
  comment '账号id',
  role_id    bigint default '0' not null
  comment '角色ID',
  constraint account_id_role_id
  unique (account_id, role_id)
)
  comment '账号角色表'
  engine = InnoDB;

create table t_data_resources
(
  id          bigint auto_increment
  comment 'id主键'
    primary key,
  icon        varchar(255) default '' null
  comment '图标',
  name        varchar(255) default '' not null
  comment '资源名称',
  uri         varchar(255) default '' not null
  comment '资源定位',
  code        varchar(20) default ''  not null
  comment '编码',
  parent_code varchar(20) default ''  not null
  comment '父级编码',
  description varchar(50) default ''  null
  comment '描述',
  state       int(1) default '0'      not null
  comment '状态(0正常 1禁用)',
  create_time bigint                  not null
  comment '创建时间'
)
  comment '数据资源表'
  engine = InnoDB;

create table t_group
(
  id                bigint auto_increment
  comment '主键ID'
    primary key,
  pid               bigint default '0'                 not null
  comment '父部门ID',
  group_id          bigint default '0'                 not null
  comment '公司ID',
  code              varchar(20) default ''             not null
  comment '部门编码(公司类型可为空)',
  code_path         varchar(50) default ''             not null
  comment '当前级+父级编码组合（|01|0101|）',
  organization_code varchar(20) default ''             null
  comment '组织机构代码',
  name              varchar(50) default ''             not null
  comment '名称(公司或部门名称)',
  state             int(1) default '0'                 not null
  comment '状态(0正常 1禁用)',
  typ               int(1) default '0'                 not null
  comment '组类型（0公司 1部门）',
  create_time       datetime default CURRENT_TIMESTAMP null
  comment '创建时间'
)
  comment '组'
  engine = InnoDB;

create table t_group_img
(
  id       bigint auto_increment
  comment '主键ID'
    primary key,
  group_id bigint                  not null
  comment '组ID',
  type     int                     not null
  comment '证件类型(0三证合一 1营业执照 2经营许可证 3税务登记证 4管理员授权书 5法人身份证）',
  img_url  varchar(100) default '' not null
  comment '证件图片地址'
)
  comment '组图片表'
  engine = InnoDB;

create table t_group_info
(
  id             bigint auto_increment
  comment 'id主键'
    primary key,
  group_id       bigint default '0'      not null
  comment '组织ID',
  legal_person   varchar(20) default ''  not null
  comment '拥有者/法人',
  type           int default '0'         not null
  comment '公司类型（0个人独资 1有限责任公司 2股份制公司 3集团公司 4联营企业 5外商投资企业 6中外合资经营企业 7国有 8私营企业 9全民所有制 10集体所有制...）',
  validity       varchar(25) default ''  not null
  comment '经营期限',
  business_scope varchar(100) default '' not null
  comment '经营范围',
  address        varchar(100) default '' not null
  comment '注册地',
  capital        varchar(10) default ''  not null
  comment '注册资金',
  phone          varchar(30) default ''  not null
  comment '公司电话',
  state          int(1) default '0'      not null
  comment '状态(0正常 1禁用)',
  create_time    bigint default '0'      not null
  comment '创建时间'
)
  comment '组信息表'
  engine = InnoDB;

create table t_group_member
(
  id        bigint not null
    primary key,
  group_id  bigint null,
  member_id bigint null
)
  engine = InnoDB;

create table t_group_resources
(
  id    bigint       not null
    primary key,
  icon  varchar(255) null,
  name  varchar(255) null,
  state int          null,
  uri   varchar(255) null
)
  engine = InnoDB;

create table t_group_role
(
  id       bigint auto_increment
  comment '主键ID'
    primary key,
  group_id bigint not null
  comment '组ID',
  role_id  bigint not null
  comment '角色ID',
  constraint group_id_role_id
  unique (group_id, role_id)
)
  comment '组角色关联表'
  engine = InnoDB;

create table t_group_user
(
  id          bigint auto_increment
  comment '主键ID'
    primary key,
  group_id    bigint default '0'     not null
  comment '公司ID或当前部门ID',
  user_id     bigint default '0'     not null
  comment '用户ID',
  code_path   varchar(50) default '' not null
  comment '父级所有code，英文|分割',
  create_time datetime               null
  comment '创建时间',
  constraint group_id_user_id
  unique (group_id, user_id)
)
  comment '组用户关联表'
  engine = InnoDB;

create table t_member
(
  id    bigint       not null
    primary key,
  icon  varchar(255) null,
  level int          null,
  name  varchar(255) null
)
  engine = InnoDB;

create table t_permission
(
  id          bigint auto_increment
  comment '主键ID'
    primary key,
  icon        varchar(255)       null
  comment '图片地址',
  name        varchar(20)        not null
  comment '名称',
  state       int(1) default '0' not null
  comment '状态（0正常，1禁用）',
  code        varchar(50)        not null
  comment '编码',
  parent_code varchar(50)        not null
  comment '父级编码',
  create_time bigint             not null
  comment '创建时间'
)
  comment '权限表'
  engine = InnoDB;

create table t_permission_resources
(
  id            bigint not null
    primary key,
  permission_id bigint null,
  resources_id  bigint null,
  constraint permission_id_resources_id
  unique (permission_id, resources_id)
)
  engine = InnoDB;

create table t_resources
(
  id           bigint auto_increment
  comment '主键ID'
    primary key,
  resources_id bigint      null
  comment '资源id',
  name         varchar(20) null
  comment '资源名称',
  typ          varchar(1)  null
  comment '资源类型（0URL资源 1图片资源 2数据资源）',
  state        int(1)      null
  comment '状态(0正常 1禁用)',
  create_time  bigint      null
  comment '创建时间'
)
  comment '资源表'
  engine = InnoDB;

create table t_role
(
  id          bigint auto_increment
  comment 'id'
    primary key,
  icon        varchar(100) null
  comment '角色对应的图标地址',
  name        varchar(20)  not null
  comment '角色名称',
  state       int(1)       not null
  comment '状态(0正常 1禁用)',
  create_time datetime     null
  comment '创建时间'
)
  comment '角色表'
  engine = InnoDB;

create table t_accredit
(
  id            bigint auto_increment
  comment '主键ID'
    primary key,
  permission_id bigint not null
  comment '权限ID',
  role_id       bigint not null
  comment '角色ID',
  constraint permission_id_role_id
  unique (permission_id, role_id)
)
  comment '角色权限关联表'
  engine = InnoDB;

create table t_url_resources
(
  id          bigint       not null
  comment 'id主键'
    primary key,
  icon        varchar(100) null
  comment '图标',
  name        varchar(20)  not null
  comment '资源名称',
  uri         varchar(100) not null
  comment '资源路径',
  code        varchar(20)  not null
  comment '编码',
  parent_code varchar(20)  not null
  comment '父级编码',
  description varchar(50)  null
  comment '描述',
  sort        int          null
  comment '顺序',
  state       int(1)       not null
  comment '状态(0正常 1禁用)',
  create_time datetime     not null
  comment '创建时间'
)
  comment 'URL资源表'
  engine = InnoDB;

create table t_user
(
  id            bigint auto_increment
    primary key,
  name          varchar(20) default '' not null
  comment '姓名',
  age           int                    null
  comment '年龄',
  domicile      varchar(200)           null
  comment '户籍地',
  id_card       varchar(30)            null
  comment '身份证号',
  email         varchar(50)            null
  comment '邮箱',
  qq            varchar(20)            null
  comment 'QQ',
  sex           int                    null
  comment '性别(0男 1女)',
  phone         varchar(30)            null
  comment '座机',
  is_supplement tinyint(1)             null
  comment '是否已经补全信息',
  state         int(1) default '0'     not null
  comment '状态(0正常 1禁用)',
  typ           int(1) default '0'     not null
  comment '用户类型（0非内部用户 1内部用户 2内部用户兼外部用户）',
  create_time   datetime               null
  comment '创建时间'
)
  comment '用户表'
  engine = InnoDB;

create table t_user_account
(
  id          bigint auto_increment
  comment '主键ID'
    primary key,
  account_id  bigint       not null
  comment '账号ID',
  user_id     varchar(255) not null
  comment '用户ID',
  create_time datetime     not null
  comment '创建时间',
  constraint account_id_user_id
  unique (account_id, user_id)
)
  comment '用户账号关联表'
  engine = InnoDB;

create table t_user_address
(
  id          bigint auto_increment
    primary key,
  user_id     bigint       not null
  comment '用户ID',
  address     varchar(100) null,
  phone       varchar(30)  null
  comment '电话',
  name        varchar(20)  null
  comment '姓名',
  sort        int          null
  comment '排序优先级',
  state       int(1)       null
  comment '状态(0正常 1禁用)',
  create_time bigint       null
  comment '创建时间'
)
  comment '用户地址信息表'
  engine = InnoDB;

