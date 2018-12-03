package com.khsh.etl.databuilder.db.meta;
/**
 * Copyright (C), 2016-2018, 武汉康华数海有限公司
 * FileName: DbType
 * Author:   Ejet
 * CreateDate:     2018-08-23 13:58
 * Description: 数据库类型
 * History:
 * Version: 1.0
 */
public enum DbTypeEnum {

    SQLSERVER("sqlserver", "SqlServer数据库"),
    ORACLE("oracle", "oracle数据库"),
    MYSQL("mysql", "MySQL数据库"),
	SYBASE("sybase", "sybase数据库");

    private String name;
    private String desc;

    private DbTypeEnum(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }


    public static DbTypeEnum getTypeName(String name) {
        if(name == null || "".equals(name.trim())){
            return null;
        }
        for (DbTypeEnum c : DbTypeEnum.values()) {
            if(c.getName().equals(name.trim().toLowerCase())){
                return c;
            }
        }
        return null;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


}
