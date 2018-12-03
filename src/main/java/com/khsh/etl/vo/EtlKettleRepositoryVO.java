package com.khsh.etl.vo;

import com.khsh.etl.model.EtlKettleRepositoryModel;
public class EtlKettleRepositoryVO extends EtlKettleRepositoryModel {
    /**
     * 是否使用
     */
    private String hasUsed;

    public String getHasUsed() {
        return hasUsed;
    }

    public void setHasUsed(String hasUsed) {
        this.hasUsed = hasUsed;
    }
}
