-- auto-generated definition
create table if not exists tb_user
(
    id           bigint auto_increment comment 'id'
        primary key,
    userName     varchar(256)                       not null comment '用户名',
    userAccount  varchar(256)                       not null comment '账号',
    userPassword varchar(256)                       not null comment '密码',
    avatarUrl    varchar(1024)                      null comment '头像',
    gender       tinyint  default 0                 not null comment '性别（0-男 1-女）',
    phone        varchar(128)                       null comment '电话',
    email        varchar(512)                       null comment '邮箱',
    userRole     int      default 0                 not null comment '用户角色 (0-学生 1-老师 2-管理员)',
    createTime   datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    remarks      varchar(128)                       null comment '备注',
    constraint user_userAccount_uindex
        unique (userAccount)
)
    comment '用户表';

-- auto-generated definition
create table if not exists tb_stu_info
(
    id         bigint auto_increment comment 'id'
        primary key,
    name       varchar(100)                           null comment '姓名',
    gender     tinyint      default 0                 not null comment '性别（0-男 1-女）',
    dateBirth  date                                   null comment '出生年月',
    phone      varchar(128)                           null comment '联系电话',
    email      varchar(512)                           null comment '邮箱',
    stuId      varchar(512)                           null comment '学号（学生的登录账号）',
    school     varchar(128) default '湖南工业职业技术学院'      not null comment '学校',
    faculty    varchar(128)                           null comment '院系',
    major      varchar(128)                           null comment '专业',
    grade      varchar(128)                           null comment '年级',
    classes      varchar(128)                           null comment '班级',
    schTeaId   int                                    null comment '校内指导老师id',
    entTeaId   int                                    null comment '企业指导老师id',
    userRole   int          default 0                 not null comment '用户角色 (0-学生 1-老师 2-管理员)',
    createTime datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    remarks    varchar(128)                           null comment '备注',
    constraint stu_info_stuId_uindex
        unique (stuId)
)
    comment '学生基本信息表';

-- auto-generated definition
create table if not exists tb_inter_info
(
    id         bigint auto_increment comment 'id'
        primary key,
    address       varchar(128)                        null comment '实习地址',
    entName       varchar(128)                        null comment '实习企业名称',
    leader        varchar(128)                        null comment '负责人',
    post          varchar(128)                        null comment '实习岗位',
    stuId       varchar(512)                        null comment '学号',
    state       int                                 not null    comment '实习状态 (0-未实习 1-实习中)',
    createTime datetime     default CURRENT_TIMESTAMP not null comment '开始时间',
    updateTime datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '结束时间',
    remarks    varchar(128)                           null comment '备注'
)
    comment '学生实习信息表';

-- auto-generated definition
create table tb_mentor
(
    id         int auto_increment comment 'id'
        primary key,
    teaRole    int      default 0                 not null comment '老师类别（0-学校指导老师 1-企业指导老师）',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null comment '更新时间',
    remarks    varchar(128)                       null comment '备注'
)
    comment '实习指导老师表';

-- auto-generated definition
create table tb_sch_tea
(
    id         int auto_increment comment 'id'
        primary key,
    name       varchar(128)                           not null comment '姓名',
    gender     tinyint      default 0                 not null comment '性别（0-男 1-女）',
    phone      varchar(128)                           null comment '联系电话',
    email      varchar(128)                           null comment '邮箱',
    workId     varchar(512)                           not null comment '工号',
    school     varchar(128) default '湖南工业职业技术学院'      not null comment '学校',
    faculty    varchar(128)                           null comment '院系',
    depId      varchar(50)                            null comment '系部id',
    proId      varchar(50)                            null comment '专业id',
    userRole   int          default 1                 not null comment '用户角色（0-学生 1-老师 2-管理员）',
    teaRole    int          default 0                 not null comment '老师类别（0-学校指导老师 1-企业指导老师）',
    createTime datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    remarks    varchar(128)                           null comment '备注',
    constraint tb_sch_tea_workId_uindex
        unique (workId)
)
    comment '校内指导老师信息表';

-- auto-generated definition
create table tb_ent_tea
(
    id         int auto_increment comment 'id'
        primary key,
    entId      int                                not null comment '企业id',
    entName    varchar(128)                       not null comment '企业名称',
    entLoginId varchar(512)                                not null comment '企业工号id',
    name       varchar(40)                        not null comment '姓名',
    gender     tinyint  default 0                 not null comment '性别（0-男 1-女）',
    depId      varchar(20)                        null comment '部门id',
    department varchar(20)                        null comment '部门',
    phone      varchar(128)                       null comment '联系电话',
    email      varchar(512)                       null comment '邮箱',
    userRole   int      default 1                 not null comment '用户角色（0-学生 1-老师 2-管理员）',
    teaRole    int      default 1                 not null comment '老师类别（0-学校指导老师 1-企业指导老师）',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    remarks    varchar(128)                       null comment '备注',
    constraint tb_ent_tea_entLoginId_uindex
        unique (entLoginId)
)
    comment '企业指导老师信息表';

-- auto-generated definition
create table tb_guestbook
(
    id         bigint auto_increment comment 'id'
        primary key,
    userId     bigint                             not null comment '用户id',
    content    varchar(512)                      not null comment '留言详情',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    remarks    varchar(128)                       null comment '备注'
)
    comment '留言表';

-- auto-generated definition
create table tb_like_num
(
    id         bigint auto_increment comment 'id'
        primary key,
    guestId    bigint                             not null comment '留言id',
    userId     bigint                             not null comment '创建用户id',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    remarks    varchar(128)                       null comment '备注'
)
    comment '留言点赞记录表';

-- auto-generated definition
create table tb_area
(
    id         int auto_increment comment 'id'
        primary key,
    picture    varchar(512)                       null comment '实习基地展示图片',
    address    varchar(512)                       not null comment '实习基地地址',
    userId     bigint                             not null comment '实习基地联系人',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    remarks    varchar(128)                       null comment '备注'
)
    comment '实习基地表';

-- auto-generated definition
create table tb_news
(
    id         bigint auto_increment comment 'id'
        primary key,
    postName   varchar(100)                       not null comment '岗位名称',
    message    varchar(1024)                      not null comment '实习信息',
    areaId     int                                not null comment '实习基地id',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    remarks    varchar(128)                       null comment '备注'
)
    comment '新闻表';

-- auto-generated definition
create table if not exists tb_inter_doc
(
    id         bigint auto_increment comment 'id'
        primary key,
    title       varchar(100)                        not null comment '文档标题',
    content       varchar(512)                      not null comment '文档内容',
    stuId       varchar(512)                        not null comment '学号',
    state       int                                 not null    comment '文档状态 (0-未提交 1-已提交)',
    createTime datetime     default CURRENT_TIMESTAMP not null comment '提交时间',
    remarks    varchar(128)                           null comment '备注'
)
    comment '实习文档表';

-- auto-generated definition
create table tb_inter_report
(
    id            bigint auto_increment comment 'id'
        primary key,
    docId         int                                not null comment '实习文档id',
    entTeaName    varchar(100)                       null comment '企业老师签名区',
    stuTeaName    varchar(100)                       null comment '校内老师签名区',
    state         int      default 0                 not null comment '报告状态 (0-未通过 1-已通过)',
    result        varchar(128)                       null comment '实习报告成绩',
    oneTextArea   varchar(1024)                      not null comment '实习单位基本情况',
    twoTextArea   varchar(1024)                      not null comment '实习岗位与实习任务',
    threeTextArea varchar(1024)                      not null comment '实习体会（总结）',
    createTime    datetime default CURRENT_TIMESTAMP not null comment '提交时间',
    remarks       varchar(1024)                      null comment '备注'
)
    comment '实习报告表';

-- auto-generated definition
create table tb_document
(
    id          int auto_increment comment 'id'
        primary key,
    fileUrl     varchar(256)                       not null comment '文件路径',
    fileName    varchar(256)                       not null comment '文件名',
    downloadNum int      default 0                 not null comment '下载次数',
    isVisible   tinyint  default 0                 not null comment '是否可见',
    createTime  datetime default CURRENT_TIMESTAMP not null comment '上传时间',
    remarks     varchar(128)                       null comment '备注'
)
    comment '日常文档（实习计划表）';


# 废案

-- auto-generated definition
# create table if not exists tb_plan
# (
#     id         bigint auto_increment comment 'id'
#         primary key,
#     creator    varchar(512)                       not null comment '创建者',
#     userId     bigint                             not null comment '用户id',
#     planName   varchar(512)                       not null comment '计划名称',
#     content    varchar(1024)                      not null comment '计划详情',
#     createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
#     updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
#     remarks    varchar(128)                       null comment '备注'
# )
#     comment '实习计划表';

-- auto-generated definition
# create table tb_report
# (
#     id         bigint auto_increment comment 'id'
#         primary key,
#     repState   tinyint  default 0                 not null comment '报告状态（0-待审核 1-未通过 2-通过）',
#     userId     bigint                             not null comment 'id',
#     title      varchar(50)                        not null comment '报告主题',
#     content    varchar(1024)                      not null comment '报告内容',
#     createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
#     updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
#     remarks    varchar(128)                       null comment '备注'
# )
#     comment '实习报告表';

-- auto-generated definition
# create table tb_result
# (
#     id         bigint auto_increment comment 'id'
#         primary key,
#     reportId   bigint                             not null comment '报告id',
#     score      varchar(20)                        not null comment '实习成绩',
#     appraise   varchar(512)                       not null comment '评价',
#     createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
#     updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
#     remarks    varchar(128)                       null comment '备注'
# )
#     comment '实习成绩与评价表';







