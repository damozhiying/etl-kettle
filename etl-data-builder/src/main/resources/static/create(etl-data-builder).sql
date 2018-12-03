
-- -----------------------------------------------
-- 数据库配置表 `etl_database`
-- -----------------------------------------------
DROP TABLE IF EXISTS `etl_database`;
CREATE TABLE `etl_database` (
       `id`          		  int(11) 			  NOT NULL 					        AUTO_INCREMENT ,
       `uuid`  			      varchar(64)			NOT NULL  					      COMMENT '库ID' ,
       `name`  			      varchar(200)		NOT NULL  					      COMMENT '库名称' ,
       `db_type`  			  varchar(64)		  NOT NULL  					      COMMENT '数据库类型:MySQL/Oracle/SqlServer' ,
       `db_driver`  			varchar(64)			NOT NULL                  COMMENT '驱动名称',
       `db_url`  	        varchar(400) 		NULL DEFAULT NULL     		COMMENT '数据库url',
       `db_username`  	  varchar(64) 		NULL DEFAULT NULL     		COMMENT '用户名',
       `db_password`  	  varchar(64) 		NULL DEFAULT NULL     		COMMENT '密码',
       `from_to`  	      tinyint(1) 			NOT NULL  DEFAULT '1'   	COMMENT '库类型标识, 1: 目标库，0：源库',
       `status`  				  tinyint(1) 			NOT NULL  DEFAULT '1'   	COMMENT '状态, 1: 正常，0：禁用',
       `remark`  				   varchar(100) 	NULL DEFAULT NULL  			  COMMENT '备注,描述' ,
       `modify_time`  		 varchar(32) 		NULL DEFAULT NULL  			  COMMENT '修改时间',
       `modify_user`  		 varchar(32) 		NULL DEFAULT NULL  			  COMMENT '修改人' ,
       `ext`  					   varchar(100)		NULL DEFAULT NULL     		COMMENT '预留字段',
       `ext1`  				     varchar(100)		NULL DEFAULT NULL     		COMMENT '预留字段',
       `ext2`  				     varchar(100)		NULL DEFAULT NULL     		COMMENT '预留字段',
       PRIMARY KEY (`id`),
       UNIQUE INDEX `uuid` (`uuid`) USING BTREE,
       INDEX `name` (`name`) USING BTREE
)  comment='数据库信息表'
 ENGINE=InnoDB
 DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci;

-- ----------------------------
-- 同步信息表 `etl_database_build`
-- ----------------------------
DROP TABLE IF EXISTS `etl_database_build`;
CREATE TABLE `etl_database_build` (
       `id`          		  int(11) 			  NOT NULL 					        AUTO_INCREMENT ,
       `uuid`  			      varchar(64)			NOT NULL  					      COMMENT '标识ID' ,
        `name`  			    varchar(200)		NOT NULL  					      COMMENT '名称' ,
       `database_uuid_from` varchar(64)			NOT NULL  					      COMMENT '源库ID，引用etl_database' ,
       `database_uuid_to`  	varchar(64) 		NOT NULL  					      COMMENT '目标源库ID，引用etl_database',
       `table_prefix`  	  varchar(32) 		NULL  					          COMMENT '目标表前缀',
       `ignore_tables`  	text 		        NULL  					          COMMENT '忽略表，中间用逗号(,)隔开',
       `is_drop`  				  tinyint(1) 			NOT NULL  DEFAULT '1'   	COMMENT '表存在时是否删除表, 1: 删除，0：保留',
       `status`  				  tinyint(1) 			NOT NULL  DEFAULT '1'   	COMMENT '状态, 1: 正常，0：禁用',
       `remark`  				   varchar(100) 	NULL DEFAULT NULL  			  COMMENT '备注,描述' ,
       `modify_time`  		 varchar(32) 		NULL DEFAULT NULL  			  COMMENT '修改时间',
       `modify_user`  		 varchar(32) 		NULL DEFAULT NULL  			  COMMENT '修改人' ,
       `ext`  					   varchar(100)		NULL DEFAULT NULL     		COMMENT '预留字段',
       `ext1`  				     varchar(100)		NULL DEFAULT NULL     		COMMENT '预留字段',
       `ext2`  				     varchar(100)		NULL DEFAULT NULL     		COMMENT '预留字段',
       PRIMARY KEY (`id`),
       UNIQUE INDEX `database_uuid_from_to` (`database_uuid_from`, `database_uuid_to`, `status`) USING BTREE
)  comment='同步信息表'
 ENGINE=InnoDB
 DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci;


-- ----------------------------
-- 同步信息扩展数据表 `etl_database_build_ext`
-- ----------------------------
DROP TABLE IF EXISTS `etl_database_build_ext`;
CREATE TABLE `etl_database_build_ext` (
       `id`          		  int(11) 			  NOT NULL 					        AUTO_INCREMENT ,
       `build_uuid`  			  varchar(64)			NOT NULL  					      COMMENT '表etl_database_build标识ID' ,
       `column_name`  		varchar(32)			NOT NULL  					      COMMENT '新增表字段名',
       `column_type`  		varchar(32)			NOT NULL  					      COMMENT '字段类型',
       `column_length`  	int(32)			    NULL  					          COMMENT '字段长度',
       `column_scale`  	  int(32)			    NULL  					          COMMENT '字段小数点位数',
       `status`  				  tinyint(1) 			NOT NULL  DEFAULT '1'   	COMMENT '状态, 1: 正常，0：禁用',
       `remark`  				  varchar(100) 	  NULL DEFAULT NULL  			  COMMENT '备注,描述' ,
       `modify_time`  		varchar(32) 		NULL DEFAULT NULL  			  COMMENT '修改时间',
       `modify_user`  		varchar(32) 		NULL DEFAULT NULL  			  COMMENT '修改人' ,
       `ext`  					  varchar(100)		NULL DEFAULT NULL     		COMMENT '预留字段',
       `ext1`  				    varchar(100)		NULL DEFAULT NULL     		COMMENT '预留字段',
       `ext2`  				    varchar(100)		NULL DEFAULT NULL     		COMMENT '预留字段',
       PRIMARY KEY (`id`),
       UNIQUE INDEX `build_uuid_column_name` (`build_uuid`, `column_name`) USING BTREE
)  comment='同步信息扩展数据表'
 ENGINE=InnoDB
 DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci;


-- ----------------------------
-- 同步库表结构，日志输出
-- ----------------------------
DROP TABLE IF EXISTS `etl_database_build_log`;
CREATE TABLE `etl_database_build_log` (
      `id`          		  int(11) 			  NOT NULL 					      AUTO_INCREMENT ,
      `build_uuid`  			varchar(64)			NOT NULL  					    COMMENT '表etl_database_build标识ID' ,
      `log_level`         int(11)         NOT NULL DEFAULT '0'    COMMENT '日志级别, 1：debug，2：info 3：warn 4：error',
      `log_subject`       varchar(100)    DEFAULT NULL            COMMENT '日志简述',
      `log_detail`        text            DEFAULT NULL            COMMENT '日志详情',
      `log_result`        int(2)          DEFAULT '-1'            COMMENT '结果：1：成功 0：失败',
      `status`            int(2)          DEFAULT '1'             COMMENT '状态',
      `remark`            varchar(200)    DEFAULT NULL            COMMENT '备注',
      `create_day`        varchar(32)     DEFAULT NULL            COMMENT '日志产生日期',
      `modify_time`       varchar(32)     DEFAULT NULL            COMMENT '修改时间',
      `modify_user`       varchar(32)     DEFAULT NULL            COMMENT '修改人',
      `ext`               varchar(100)    DEFAULT NULL            COMMENT '扩展',
      `ext1`              varchar(100)    DEFAULT NULL            COMMENT '扩展',
      `ext2`              varchar(100)    DEFAULT NULL            COMMENT '扩展',
       PRIMARY KEY (`id`),
       INDEX `log_level` (`log_level`) USING BTREE,
       INDEX `build_uuid` (`build_uuid`) USING BTREE
)  comment='同步库表结构，日志表'
 ENGINE=InnoDB
 DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci;