package com.khsh.etl.model;

import com.ejet.comm.base.CoBaseVO;

/**
 * Copyright (C), 2016-2018, 武汉康华数海有限公司
 * FileName: KettleDirectoryModel
 * Author:   ShenYijie
 * CreateDate:     2018-11-12 11:46
 * Description: kettle资源目录对象
 * History:
 * Version: 1.0
 */
public class KettleDirectoryModel extends CoBaseVO {

    /**
     * 目录id
     */
    private Integer directoryId;
    /**
     * 目录父id
     */
    private Integer getDirectoryPid;
    /**
     * 目录名称
     */
    private String name;

    public Integer getDirectoryId() {
        return directoryId;
    }

    public void setDirectoryId(Integer directoryId) {
        this.directoryId = directoryId;
    }

    public Integer getGetDirectoryPid() {
        return getDirectoryPid;
    }

    public void setGetDirectoryPid(Integer getDirectoryPid) {
        this.getDirectoryPid = getDirectoryPid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
