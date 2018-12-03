package com.khsh.etl.kettle.kit;


import com.ejet.comm.base.CoBaseVO;

/**
 * Copyright (C), 2016-2018, 武汉康华数海有限公司
 * FileName: KettleMessage
 * Author:   ShenYijie
 * CreateDate:     2018-11-14 10:00
 * Description: kettle运行信息
 * History:
 * Version: 1.0
 */
public class KettleMessage extends CoBaseVO {
    /**  任务运行开始时间  */
    private Long starttime;
    /**  任务运行结束时间  */
    private Long endtime;


    /**
     * 任务与作业关联uuid
     */
    private String logUuidR;
    /**
     * 定时任务ID
     */
    private String jobId;
    /**
     * 定时任务名称
     */
    private String jobName;
    /**
     * 运行结果：1、正常 2：终止 3：未完成 4：异常
     */
    private Integer jobResult = 3;


    /**
     * kettle作业资源uuid
     */
    private String ktlJobUuid;
    /**
     * kettle作业名称
     */
    private String ktlJobName;

    /**
     * 本地执行
     */
    private boolean executingLocally;
    /**
     * 远程执行
     */
    private boolean executingRemotely;
    /**
     * 是否集群
     */
    private boolean executingClustered;


    private String filename;

    private String fileType;

    private String createDate;
    private String modifiedDate;

    protected int maxUndo;

    private Integer errors = null;
    private String startDate;
    private String endDate;
    private String currentDate;
    private String logDate;
    private String depDate;
    private long batchId;
    private long passedBatchId;

    private String executingServer;
    private String containerObjectId;


    /**    */
    private Integer id;
    /**  作业uuid  */
    private String jobUuid;
    /**  作业通道id  */
    private String logChannelId;
    /**    */
    private Long nrLinesDeleted;
    /**    */
    private Long nrLinesInput;
    /**    */
    private Long nrLinesOutput;
    /**    */
    private Long nrLinesRead;
    /**    */
    private Long nrLinesRejected;
    /**    */
    private Long nrLinesUpdated;
    /**    */
    private Long nrLinesWritten;
    /**    */
    private String resultFilesList;
    /**    */
    private String logText;



    public void setErrors(Integer errors) {
        this.errors = errors;
        if(errors!=null && errors>0) {
            jobResult = 4; //1、正常 2：终止 3：未完成 4：异常
        } else {
            jobResult = 1;
        }
    }

    public String getLogUuidR() {
        return logUuidR;
    }

    public void setLogUuidR(String logUuidR) {
        this.logUuidR = logUuidR;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public Integer getJobResult() {
        return jobResult;
    }

    public void setJobResult(Integer jobResult) {
        this.jobResult = jobResult;
    }

    public String getKtlJobUuid() {
        return ktlJobUuid;
    }

    public void setKtlJobUuid(String ktlJobUuid) {
        this.ktlJobUuid = ktlJobUuid;
    }

    public String getKtlJobName() {
        return ktlJobName;
    }

    public void setKtlJobName(String ktlJobName) {
        this.ktlJobName = ktlJobName;
    }

    public boolean isExecutingLocally() {
        return executingLocally;
    }

    public void setExecutingLocally(boolean executingLocally) {
        this.executingLocally = executingLocally;
    }

    public boolean isExecutingRemotely() {
        return executingRemotely;
    }

    public void setExecutingRemotely(boolean executingRemotely) {
        this.executingRemotely = executingRemotely;
    }

    public boolean isExecutingClustered() {
        return executingClustered;
    }

    public void setExecutingClustered(boolean executingClustered) {
        this.executingClustered = executingClustered;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public int getMaxUndo() {
        return maxUndo;
    }

    public void setMaxUndo(int maxUndo) {
        this.maxUndo = maxUndo;
    }

    public Integer getErrors() {
        return errors;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getLogDate() {
        return logDate;
    }

    public void setLogDate(String logDate) {
        this.logDate = logDate;
    }

    public String getDepDate() {
        return depDate;
    }

    public void setDepDate(String depDate) {
        this.depDate = depDate;
    }

    public long getBatchId() {
        return batchId;
    }

    public void setBatchId(long batchId) {
        this.batchId = batchId;
    }

    public long getPassedBatchId() {
        return passedBatchId;
    }

    public void setPassedBatchId(long passedBatchId) {
        this.passedBatchId = passedBatchId;
    }

    public String getExecutingServer() {
        return executingServer;
    }

    public void setExecutingServer(String executingServer) {
        this.executingServer = executingServer;
    }

    public String getContainerObjectId() {
        return containerObjectId;
    }

    public void setContainerObjectId(String containerObjectId) {
        this.containerObjectId = containerObjectId;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJobUuid() {
        return jobUuid;
    }

    public void setJobUuid(String jobUuid) {
        this.jobUuid = jobUuid;
    }

    public String getLogChannelId() {
        return logChannelId;
    }

    public void setLogChannelId(String logChannelId) {
        this.logChannelId = logChannelId;
    }

    public Long getNrLinesDeleted() {
        return nrLinesDeleted;
    }

    public void setNrLinesDeleted(Long nrLinesDeleted) {
        this.nrLinesDeleted = nrLinesDeleted;
    }

    public Long getNrLinesInput() {
        return nrLinesInput;
    }

    public void setNrLinesInput(Long nrLinesInput) {
        this.nrLinesInput = nrLinesInput;
    }

    public Long getNrLinesOutput() {
        return nrLinesOutput;
    }

    public void setNrLinesOutput(Long nrLinesOutput) {
        this.nrLinesOutput = nrLinesOutput;
    }

    public Long getNrLinesRead() {
        return nrLinesRead;
    }

    public void setNrLinesRead(Long nrLinesRead) {
        this.nrLinesRead = nrLinesRead;
    }

    public Long getNrLinesRejected() {
        return nrLinesRejected;
    }

    public void setNrLinesRejected(Long nrLinesRejected) {
        this.nrLinesRejected = nrLinesRejected;
    }

    public Long getNrLinesUpdated() {
        return nrLinesUpdated;
    }

    public void setNrLinesUpdated(Long nrLinesUpdated) {
        this.nrLinesUpdated = nrLinesUpdated;
    }

    public Long getNrLinesWritten() {
        return nrLinesWritten;
    }

    public void setNrLinesWritten(Long nrLinesWritten) {
        this.nrLinesWritten = nrLinesWritten;
    }

    public String getResultFilesList() {
        return resultFilesList;
    }

    public void setResultFilesList(String resultFilesList) {
        this.resultFilesList = resultFilesList;
    }

    public String getLogText() {
        return logText;
    }

    public void setLogText(String logText) {
        this.logText = logText;
    }
}
