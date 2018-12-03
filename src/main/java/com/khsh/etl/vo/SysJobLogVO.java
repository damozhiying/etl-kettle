package com.khsh.etl.vo;

import com.khsh.etl.kettle.kit.KettleMessage;
import com.khsh.etl.model.SysJobLogModel;

import java.util.ArrayList;
import java.util.List;

public class SysJobLogVO extends SysJobLogModel {

    private List<SysJobLogModel> ids = new ArrayList<>();

    /**  任务运行开始时间  */
    private Long starttime;
    /**  任务运行结束时间  */
    private Long endtime;

    /**  作业名称  */
    private String ktlJobName;
    /**  kette作业类型: KJBKTR  */
    private String ktlJobType;

    public Long getStarttime() {
        return starttime;
    }

    public void setStarttime(Long starttime) {
        this.starttime = starttime;
    }

    public Long getEndtime() {
        return endtime;
    }

    public void setEndtime(Long endtime) {
        this.endtime = endtime;
    }

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

    public List<SysJobLogModel> getIds() {
        return ids;
    }

    public void setIds(List<SysJobLogModel> ids) {
        this.ids = ids;
    }
}
