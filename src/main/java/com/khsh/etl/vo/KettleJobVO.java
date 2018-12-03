package com.khsh.etl.vo;

import java.io.Serializable;

/**
 * Copyright (C), 2016-2018, 武汉康华数海有限公司
 * FileName: KettleJobVO
 * Author:   ShenYijie
 * CreateDate:     2018-11-12 23:48
 * Description: kettle资源类型
 * History:
 * Version: 1.0
 */
public class KettleJobVO implements Serializable {
    private  Integer id;
    /**  作业名称  */
    private String ktlJobName;
    /**  kette作业类型: KJBKTR  */
    private String ktlJobType;

    private Integer directoryId;
    /**
     * 目录父id
     */
    private Integer directoryPid;
    /**
     *  目录名称
     */
    private String name;
    /**
     * 文件全路径
     */
    private String fullPath=null;

    public String getKtlJobName() {
        return ktlJobName;
    }

    public void setKtlJobName(String ktlJobName) {
        this.ktlJobName = ktlJobName;
    }

    public String getKtlJobType() {
        return ktlJobType;
    }

    public void setKtlJobType(String ktlJobType) {
        this.ktlJobType = ktlJobType;
    }

    public Integer getDirectoryId() {
        return directoryId;
    }

    public void setDirectoryId(Integer directoryId) {
        this.directoryId = directoryId;
    }

    public Integer getDirectoryPid() {
        return directoryPid;
    }

    public void setDirectoryPid(Integer directoryPid) {
        this.directoryPid = directoryPid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

}
