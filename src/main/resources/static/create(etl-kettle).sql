-- ----------------------------
-- kettle资源库地址 `etl_kettle_database`
-- ----------------------------
DROP TABLE IF EXISTS `etl_kettle_database`;
CREATE TABLE `etl_kettle_database` (
  `id`          int(11)         NOT NULL AUTO_INCREMENT,
  `uuid`        varchar(64)     NOT NULL COMMENT '库ID',
  `name`        varchar(200)    NOT NULL COMMENT '库名称',
  `db_type`     varchar(64)     NOT NULL COMMENT '数据库类型',
  `db_access`   varchar(64)     NOT NULL COMMENT '数据库访问类型',
  `db_host`     varchar(200)    NOT NULL  COMMENT '数据库地址',
  `db_port`     int(11)         NOT NULL  COMMENT '数据库端口',
  `db_database` varchar(200)    DEFAULT NULL COMMENT '数据库名',
  `db_username` varchar(64)     DEFAULT NULL COMMENT '用户名',
  `db_password` varchar(64)     DEFAULT NULL COMMENT '密码',
  `db_repository_id` varchar(255) DEFAULT '' COMMENT 'kettle资源库id',
  `db_description`   varchar(255) DEFAULT '' COMMENT 'kettle资源库描述',
  `db_param`         varchar(512) DEFAULT NULL COMMENT '扩展参数',
  `status` tinyint(1) DEFAULT '1' COMMENT '状态, 1: 正常，0：禁用',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注,描述',
  `modify_time` varchar(32) DEFAULT NULL COMMENT '修改时间',
  `modify_user` varchar(32) DEFAULT NULL COMMENT '修改人',
  `ext` varchar(100) DEFAULT NULL COMMENT '预留字段',
  `ext1` varchar(100) DEFAULT NULL COMMENT '预留字段',
  `ext2` varchar(100) DEFAULT NULL COMMENT '预留字段',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uuid` (`uuid`) USING BTREE,
  KEY `name` (`name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COMMENT='kettle资源库地址';



-- ----------------------------
-- kettle作业配置表 `etl_kettle_repository`
-- param_value字段：采用json，key：value键值对方式，可直接转map进行处理
-- 比如：{"tableName":"sys_user", "fetchNum": 1000}
-- ----------------------------
DROP TABLE IF EXISTS `etl_kettle_repository`;
CREATE TABLE `etl_kettle_repository` (
       `id`          		  int(11) 			  NOT NULL 					        AUTO_INCREMENT ,
       `uuid`  	          varchar(64)			NOT NULL  					      COMMENT '标识uuid' ,
       `database_uuid`    varchar(64)     DEFAULT NULL,
       `ktl_job_name`  		varchar(64)			NOT NULL  					      COMMENT '作业名称',
       `ktl_job_type`  		varchar(32)			NOT NULL  					      COMMENT 'kette作业类型: KJB\KTR' ,
       `rep_type`  		    varchar(32)			NOT NULL  					      COMMENT '资源类型: DB\FILE',
       `rep_path`  	      varchar(400)		NOT NULL  					      COMMENT '资源路径',
       `rep_directory`  	varchar(400)		NOT NULL  					      COMMENT '资源目录',
       `rep_filename`  	  varchar(400)		NOT NULL  					      COMMENT '资源文件名',

       `rep_id`  	        bigint(20)		NOT NULL  					      COMMENT 'kettle资源id',
       `rep_id_directory` int(11)		    NOT NULL  					      COMMENT 'kettle资源目录id',

       `ktl_param_value` varchar(2000)		NULL DEFAULT NULL     	COMMENT 'kettle参数，采用json，key：value键值对' ,

       `status`  				  tinyint(1) 			NOT NULL  DEFAULT '1'   	COMMENT '状态, 1: 正常，0：禁用',
       `remark`  				  varchar(100) 	  NULL DEFAULT NULL  			  COMMENT '备注,描述' ,
       `modify_time`  		varchar(32) 		NULL DEFAULT NULL  			  COMMENT '修改时间',
       `modify_user`  		varchar(32) 		NULL DEFAULT NULL  			  COMMENT '修改人' ,
       `ext`  					  varchar(100)		NULL DEFAULT NULL     		COMMENT '预留字段',
       `ext1`  				    varchar(100)		NULL DEFAULT NULL     		COMMENT '预留字段',
       `ext2`  				    varchar(100)		NULL DEFAULT NULL     		COMMENT '预留字段',
       PRIMARY KEY (`id`),
       UNIQUE INDEX `uuid` (`uuid`) USING BTREE,
       UNIQUE INDEX `rep_id_rep_id_directory_rep_type_status` (`rep_id`, `rep_id_directory`, `rep_type`, `status`) USING BTREE
)  comment='kettle作业配置表'
 ENGINE=InnoDB
 DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci;


-- ------------------------------------
-- kettle作业运行日志表 `kettle_message_log`
-- ------------------------------------
DROP TABLE IF EXISTS `kettle_message_log`;
CREATE TABLE `kettle_message_log` (
    `id`          		    int(11) 			NOT NULL 		AUTO_INCREMENT ,
    `uuid`  	            varchar(64)		NOT NULL  	            COMMENT '标识uuid',
    `job_id`  				    varchar(64)		NOT NULL  					    COMMENT '任务ID' ,
    `job_name`  			    varchar(64)		NOT NULL DEFAULT ''   	COMMENT '任务名称',
    `ktl_job_uuid`        varchar(64)   DEFAULT NULL            COMMENT 'kettle作业uuid',
    `ktl_job_name`        varchar(64)   DEFAULT NULL            COMMENT 'kettle作业名称',
    `ktl_job_type`        varchar(32)   DEFAULT NULL            COMMENT 'kette作业类型: KJB、KTR',

    `log_channel_id`      varchar(128)  DEFAULT NULL COMMENT '作业通道id',
    `log_batch_id`        int(11)       DEFAULT NULL COMMENT 'ID_BATCH',
    `seq_nr`              int(11)       DEFAULT NULL,
    `trans_name`          varchar(255)  DEFAULT NULL,
    `errors`              bigint(20)    DEFAULT NULL,
    `input_buffer_rows`   bigint(20)    DEFAULT NULL,
    `output_buffer_rows`  bigint(20)    DEFAULT NULL,
    `nr_lines_deleted`    bigint(20)    DEFAULT NULL,
    `nr_lines_input`      bigint(20)    DEFAULT NULL,
    `nr_lines_output`     bigint(20)    DEFAULT NULL,
    `nr_lines_read`       bigint(20)    DEFAULT NULL,
    `nr_lines_rejected`   bigint(20)    DEFAULT NULL,
    `nr_lines_updated`    bigint(20)    DEFAULT NULL,
    `nr_lines_written`    bigint(20)    DEFAULT NULL,
    `startdate`           varchar(32)   DEFAULT NULL,
    `enddate`             varchar(32)   DEFAULT NULL,
    `logdate`             varchar(32)   DEFAULT NULL,
    `depdate`             varchar(32)   DEFAULT NULL,
    `replaydate`          varchar(32)   DEFAULT NULL,
    `log_field`           mediumtext,
    `executing_server`    varchar(255)  DEFAULT NULL,
    `executing_user`      varchar(255)  DEFAULT NULL,
    `client`              varchar(255)  DEFAULT NULL,
    `result_files_list`   longtext,
    `log_text`            longtext,
    `create_day`          varchar(32)     DEFAULT NULL COMMENT '运行日期',
    `modify_time`  		    varchar(32) 		NULL DEFAULT NULL  	COMMENT '修改时间',
     PRIMARY KEY (`id`),
     UNIQUE INDEX `uuid` (`uuid`) USING BTREE
) comment='kettle作业运行日志表'
 ENGINE=InnoDB
 DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci;


v1.0.1版本修改
ALTER TABLE etl_kettle_repository ADD COLUMN rep_id bigint(20) DEFAULT NULL COMMENT 'kettle资源id' AFTER rep_filename;
ALTER TABLE etl_kettle_repository ADD COLUMN rep_id_directory int(11) DEFAULT NULL COMMENT 'kettle资源目录id' AFTER rep_id;
ALTER TABLE etl_kettle_repository ADD CONSTRAINT
--ALTER TABLE etl_kettle_repository drop CONSTRAINT 约束名
ALTER TABLE `etl_kettle_repository` ADD INDEX UNIQUE INDEX `rep_id_rep_id_directory_ktl_job_type` (`rep_id`, `rep_id_directory`, `ktl_job_type`, `status`) USING BTREE;
