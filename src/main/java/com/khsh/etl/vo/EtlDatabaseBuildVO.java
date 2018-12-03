package com.khsh.etl.vo;

import com.khsh.etl.model.EtlDatabaseBuildExtModel;
import com.khsh.etl.model.EtlDatabaseBuildModel;

import java.util.List;

public class EtlDatabaseBuildVO extends EtlDatabaseBuildModel {
    /**  源库ID，引用etl_database  */
    private String databaseFromName;
    /**  目标源库ID，引用etl_database  */
    private String databaseToName;
    /**
     * 扩展的字段信息表
     */
    private List<EtlDatabaseBuildExtModel>  dbextlist;

    public List<EtlDatabaseBuildExtModel> getDbextlist() {
        return dbextlist;
    }

    public void setDbextlist(List<EtlDatabaseBuildExtModel> dbextlist) {
        this.dbextlist = dbextlist;
    }

    public String getDatabaseFromName() {
        return databaseFromName;
    }

    public void setDatabaseFromName(String databaseFromName) {
        this.databaseFromName = databaseFromName;
    }

    public String getDatabaseToName() {
        return databaseToName;
    }

    public void setDatabaseToName(String databaseToName) {
        this.databaseToName = databaseToName;
    }
}