package com.khsh.etl.vo;

import com.ejet.comm.db.CoDbConfig;

/**
 * Copyright (C), 2016-2018, 武汉康华数海有限公司
 * FileName: DbMonitorVO
 * Author:   ShenYijie
 * CreateDate:     2018-11-23 10:04
 * Description: db信息
 * History:
 * Version: 1.0
 */
public class DbMonitorVO extends CoDbConfig {

    /**  数据库类型  */
    private String dbType;
    /**  数据库访问类型  */
    private String dbAccess;
    /**  数据库地址  */
    private String dbHost;
    /**  数据库端口  */
    private Integer dbPort;
    /**  数据库名  */
    private String dbDatabase;
    /**  用户名  */
    private String dbUsername;
    /**  密码  */
    private String dbPassword;
    /**  kettle资源库id  */
    private String dbRepositoryId;

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public String getDbAccess() {
        return dbAccess;
    }

    public void setDbAccess(String dbAccess) {
        this.dbAccess = dbAccess;
    }

    public String getDbHost() {
        return dbHost;
    }

    public void setDbHost(String dbHost) {
        this.dbHost = dbHost;
    }

    public Integer getDbPort() {
        return dbPort;
    }

    public void setDbPort(Integer dbPort) {
        this.dbPort = dbPort;
    }

    public String getDbDatabase() {
        return dbDatabase;
    }

    public void setDbDatabase(String dbDatabase) {
        this.dbDatabase = dbDatabase;
    }

    public String getDbUsername() {
        return dbUsername;
    }

    public void setDbUsername(String dbUsername) {
        this.dbUsername = dbUsername;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    public String getDbRepositoryId() {
        return dbRepositoryId;
    }

    public void setDbRepositoryId(String dbRepositoryId) {
        this.dbRepositoryId = dbRepositoryId;
    }
}
