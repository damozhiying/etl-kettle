package com.khsh.etl.vo;

import com.khsh.etl.model.EtlDatabaseBuildLogModel;
public class EtlDatabaseBuildLogVO extends EtlDatabaseBuildLogModel {


    private String buildName;

    public String getBuildName() {
        return buildName;
    }

    public void setBuildName(String buildName) {
        this.buildName = buildName;
    }
}
