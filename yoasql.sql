--部门表
CREATE TABLE `t_department` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL COMMENT '部门名称',
  `parentId` int NOT NULL COMMENT '父部门id',
  `depPath` varchar(255) DEFAULT NULL COMMENT '部门路径',
  `enable` tinyint(1) DEFAULT NULL COMMENT '是否启用',
  `isParent` tinyint(1) DEFAULT NULL COMMENT '是否上级',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci


--员工信息表
CREATE TABLE `t_employee` (
  `id` varchar(64) NOT NULL,
  `name` varchar(10) NOT NULL COMMENT '员工姓名',
  `gender` varchar(5) DEFAULT NULL COMMENT '性别',
  `job_number` varchar(10) NOT NULL COMMENT '工号',
  `birthday` date DEFAULT NULL COMMENT '出生日期',
  `email` varchar(30) DEFAULT NULL COMMENT '邮箱',
  `phoneNum` varchar(12) DEFAULT NULL COMMENT '电话号码',
  `address` varchar(60) DEFAULT NULL COMMENT '地址',
  `nation` varchar(10) DEFAULT NULL COMMENT '民族',
  `university` varchar(20) DEFAULT NULL COMMENT '毕业院校',
  `education` varchar(10) DEFAULT NULL COMMENT '学历',
  `entry_time` date DEFAULT NULL COMMENT '入职时间',
  `department` varchar(64) DEFAULT NULL COMMENT '部门',
  `position` varchar(40) DEFAULT NULL COMMENT '职位',
  `contract_kstime` date DEFAULT NULL COMMENT '合同开始时间',
  `contract_jstime` date DEFAULT NULL COMMENT '合同结束时间',
  `time_limit` int DEFAULT NULL COMMENT '合同期限',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci


--菜单表
CREATE TABLE `t_menu` (
  `obj_id` varchar(64) NOT NULL,
  `path` varchar(64) DEFAULT NULL COMMENT '菜单路径',
  `component` varchar(30) DEFAULT NULL COMMENT '组件名称',
  `name` varchar(30) DEFAULT NULL COMMENT '菜单名称',
  `icon` varchar(64) DEFAULT NULL COMMENT '图标',
  `parentId` varchar(64) DEFAULT NULL COMMENT '父菜单ID',
  `enabled` tinyint(1) DEFAULT NULL COMMENT '是否启用',
  PRIMARY KEY (`obj_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci


--消息记录表
CREATE TABLE `t_msg_info` (
  `obj_id` varchar(64) NOT NULL COMMENT '消息id',
  `from_user_id` varchar(64) NOT NULL COMMENT '消息发送者id',
  `from_user_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '消息发送者名称',
  `to_user_id` varchar(64) NOT NULL COMMENT '消息接收者id',
  `to_user_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '消息接收者名称',
  `content` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '消息内容',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '消息发送时间',
  `un_read_flag` int NOT NULL COMMENT '是否已读（1 已读）',
  `self` varchar(10) DEFAULT NULL COMMENT '是否己方发送',
  PRIMARY KEY (`obj_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='消息表'


--职位表
CREATE TABLE `t_position` (
  `id` varchar(50) NOT NULL,
  `pos_name` varchar(20) NOT NULL COMMENT '职位名称',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `enable` int NOT NULL COMMENT '是否启用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci


--消息队列关系表
CREATE TABLE `t_rabbitmq_log` (
  `obj_id` varchar(64) NOT NULL COMMENT '消息ID',
  `empid` varchar(64) DEFAULT NULL COMMENT '员工ID',
  `status` int DEFAULT NULL COMMENT '状态(0投递中，1投递成功，2投递失败)',
  `routeKey` varchar(20) DEFAULT NULL COMMENT '路由键',
  `exchange` varchar(20) DEFAULT NULL COMMENT '交换机',
  `count` int DEFAULT NULL COMMENT '重试次数',
  `tryTime` datetime DEFAULT NULL COMMENT '重试时间',
  `createTimt` datetime DEFAULT NULL COMMENT '创建时间',
  `updateTime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`obj_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci


--角色表
CREATE TABLE `t_role` (
  `obj_id` varchar(64) NOT NULL,
  `name` varchar(64) DEFAULT NULL COMMENT '角色名称',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `enable` tinyint(1) DEFAULT '1' COMMENT '是否启用',
  PRIMARY KEY (`obj_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci


--角色菜单对应表
CREATE TABLE `t_role_menu` (
  `obj_id` varchar(64) NOT NULL,
  `roleId` varchar(64) DEFAULT NULL COMMENT '角色ID',
  `menuId` varchar(64) DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`obj_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci


--角色用户对应表
CREATE TABLE `t_role_user` (
  `obj_id` varchar(64) NOT NULL,
  `userId` varchar(64) DEFAULT NULL COMMENT '用户ID',
  `roleId` varchar(64) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`obj_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci


--在线聊天会话记录表
CREATE TABLE `t_session_list` (
  `obj_id` varchar(64) NOT NULL COMMENT 'id',
  `user_id` varchar(64) NOT NULL COMMENT '所属用户',
  `to_user_id` varchar(64) NOT NULL COMMENT '到用户',
  `list_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '会话名称',
  `un_read_count` int NOT NULL COMMENT '未读消息数',
  PRIMARY KEY (`obj_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='会话列表'

--系统用户表
CREATE TABLE `t_user` (
  `id` varchar(64) NOT NULL,
  `username` varchar(10) NOT NULL COMMENT '用户名',
  `password` varchar(15) NOT NULL COMMENT '密码',
  `employee` varchar(64) DEFAULT NULL COMMENT '员工部门',
  `picture_url` varchar(150) DEFAULT NULL COMMENT '头像',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci




--部门mysql存储过程
--添加
--创建名称为addDep的存储过程，输入参数:depName、parentId、enable；输出参数：result、result2
CREATE PROCEDURE addDep(in depName varchar(32), in parentId int,in enable boolean,out result int,out result2 int)
begin
	--定义变量
    declare did int;
    declare pDepPath varchar(64);
	--插入数据
    insert into t_department set name=depName,parentId=parentId,enable=enable,isParent=false;
	--返回受影响的行数
    select row_count() into result;
	--查询最后插入数据的id赋值给did
    select last_insert_id() into did;
    set result2 = did;
	--查询插入数据的父部门的路径赋值给pDepPath
    select depPath into pDepPath from t_department where id = parentId;
	--设置插入的数据的部门路径等于父路径加上自己的id
    update t_department set depPath=concat(pDepPath,'/',did) where id=did;
	--设置新插入的数据的isParent字段为1
    update t_department set isParent=true where id=parentId;
end

--部门删除
--删除
--创建名为delDep的存储过程，输入参数：id，输出参数：result
CREATE PROCEDURE delDep(in did int, out result int)
begin
	--定义变量
    declare ecount int;
    declare pid int;
    declare pcount int;
    declare a int;
	--查询id等于id且是否上级等于0的值
    select count(*) into a from t_department where id=did and isParent=false;
	--如果没有，则证明删除的部门下存在子部门，返回result=-2
    if a=0 then set result=-2;
    else
		--如果有，再次查询改部门下有没有员工
        select count(*) into ecount from t_user where department=did;
		--如果有，则证明该部门下存在员工
        if ecount>0 then set result=-1;
        else
			--如果以上条件都不符合，则允许删除部门
            select parentId into pid from t_department where id=did;
            delete from t_department where id=did and isParent=false;
            select row_count() into result;
			--查询父部门下是否还有部门，如果没有，设置父部门的是否上级为0
            select count(*) into pcount from t_department where parentId=did;
            if pcount=0 then update t_department set isParent=false where id=pid;
            end if;
        end if;
    end if;
end



--根据登陆用户id获取菜单
select distinct
    m1.*,
    m2.path as path2,
    m2.component as component2,
    m2.name as name2,
    m2.icon as icon2,
    m2.parentId as parentId,
    m2.enabled as enabled2
from
    t_menu m1,
    t_menu m2,
    t_role_user roleUser,
    t_role_menu roleMenu
where m1.obj_id = m2.parentId
and m2.obj_id = roleMenu.menuId
and roleMenu.roleId = roleUser.roleId
and m1.parentId != ''
and m2.enabled = true
and roleUser.userId = 2

--返回所有菜单
select distinct
m1.name,
    m2.name as name2
from
t_menu m1,
     t_menu m2
where m1.obj_id = m2.parentId
and m1.parentId != ''


--系统用户权限
select user.username,
       emp.job_number,
       emp.entry_time,
       role.name
from
    t_user user,
    t_role_user tru ,
    t_role role,
     t_employee emp
where user.id = tru.userId
  and tru.roleId = role.obj_id
and user.employee = emp.id
