package com.khsh.etl.vo;

import com.khsh.etl.model.SysJobParamModel;

/**
 * Copyright (C), 2016-2018, 武汉康华数海有限公司
 * FileName: SysJobParamKettleVO
 * Author:   Ejet
 * CreateDate:     2018/11/1 10:25
 * Description:
 * History:
 * Version: 1.0
 */
public class SysJobParamKettleVO extends SysJobParamModel {

    /**  kette作业类型: KJBKTR  */
    private String ktlJobType;
    /**  作业名称  */
    private String ktlJobName;
    /**  资源类型: DBFILE  */
    private String repType;
    /**  资源路径  */
    private String repPath;

    public String getKtlJobType() {
        return ktlJobType;
    }

    public void setKtlJobType(String ktlJobType) {
        this.ktlJobType = ktlJobType;
    }

    public String getKtlJobName() {
        return ktlJobName;
    }

    public void setKtlJobName(String ktlJobName) {
        this.ktlJobName = ktlJobName;
    }

    public String getRepType() {
        return repType;
    }

    public void setRepType(String repType) {
        this.repType = repType;
    }

    public String getRepPath() {
        return repPath;
    }

    public void setRepPath(String repPath) {
        this.repPath = repPath;
    }
}
