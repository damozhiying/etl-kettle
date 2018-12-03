package com.khsh.etl.vo;

import com.khsh.etl.model.KettleMessageLogModel;
public class KettleMessageLogVO extends KettleMessageLogModel {
    /**  任务运行开始时间  */
    private Long starttime;
    /**  任务运行结束时间  */
    private Long endtime;

    /**
     * 步骤名称
     */
    private String stepName;


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

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }
}
