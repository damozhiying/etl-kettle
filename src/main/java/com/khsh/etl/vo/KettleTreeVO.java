package com.khsh.etl.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (C), 2016-2018, 武汉康华数海有限公司
 * FileName: KettleTreeVO
 * Author:   ShenYijie
 * CreateDate:     2018-11-12 16:08
 * Description: kettle树结构
 * History:
 * Version: 1.0
 */
public class KettleTreeVO implements Serializable {

    private Integer directoryId;
    private Integer directoryPid;
    private String name;

    private List<KettleTreeVO> children = new ArrayList<>();

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

    public List<KettleTreeVO> getChildren() {
        return children;
    }

    public void setChildren(List<KettleTreeVO> children) {
        this.children = children;
    }
}
