package com.khsh.etl.model;

import com.ejet.comm.base.CoBaseVO;

public class KettleMessageLogModel extends CoBaseVO {

	/**    */
 	private Integer id;
	/**  标识uuid  */
 	private String uuid;
	/**  任务ID  */
 	private String jobId;
	/**  任务名称  */
 	private String jobName;
	/**  kettle作业uuid  */
 	private String ktlJobUuid;
	/**  kettle作业名称  */
 	private String ktlJobName;
	/**  kette作业类型: KJB、KTR  */
 	private String ktlJobType;
	/**  作业通道id  */
 	private String logChannelId;
	/**  ID_BATCH  */
 	private Integer logBatchId;
	/**    */
 	private Integer seqNr;
	/**    */
 	private String transName;
	/**    */
 	private Long errors;
	/**    */
 	private Long inputBufferRows;
	/**    */
 	private Long outputBufferRows;
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
 	private String startdate;
	/**    */
 	private String enddate;
	/**    */
 	private String logdate;
	/**    */
 	private String depdate;
	/**    */
 	private String replaydate;
	/**    */
 	private String executingServer;
	/**    */
 	private String executingUser;
	/**    */
 	private String client;
	/**  运行日期  */
 	private String createDay;
	/**  修改时间  */
 	private String modifyTime;
	/**    */
 	private String logField;
	/**    */
 	private String resultFilesList;
	/**    */
 	private String logText;

	public void setId(Integer id) {
		this.id=id;
	}

	public Integer getId(){
		return id;
	}

	public void setUuid(String uuid) {
		this.uuid=uuid;
	}

	public String getUuid(){
		return uuid;
	}

	public void setJobId(String jobId) {
		this.jobId=jobId;
	}

	public String getJobId(){
		return jobId;
	}

	public void setJobName(String jobName) {
		this.jobName=jobName;
	}

	public String getJobName(){
		return jobName;
	}

	public void setKtlJobUuid(String ktlJobUuid) {
		this.ktlJobUuid=ktlJobUuid;
	}

	public String getKtlJobUuid(){
		return ktlJobUuid;
	}

	public void setKtlJobName(String ktlJobName) {
		this.ktlJobName=ktlJobName;
	}

	public String getKtlJobName(){
		return ktlJobName;
	}

	public void setKtlJobType(String ktlJobType) {
		this.ktlJobType=ktlJobType;
	}

	public String getKtlJobType(){
		return ktlJobType;
	}

	public void setLogChannelId(String logChannelId) {
		this.logChannelId=logChannelId;
	}

	public String getLogChannelId(){
		return logChannelId;
	}

	public void setLogBatchId(Integer logBatchId) {
		this.logBatchId=logBatchId;
	}

	public Integer getLogBatchId(){
		return logBatchId;
	}

	public void setSeqNr(Integer seqNr) {
		this.seqNr=seqNr;
	}

	public Integer getSeqNr(){
		return seqNr;
	}

	public void setTransName(String transName) {
		this.transName=transName;
	}

	public String getTransName(){
		return transName;
	}

	public void setErrors(Long errors) {
		this.errors=errors;
	}

	public Long getErrors(){
		return errors;
	}

	public void setInputBufferRows(Long inputBufferRows) {
		this.inputBufferRows=inputBufferRows;
	}

	public Long getInputBufferRows(){
		return inputBufferRows;
	}

	public void setOutputBufferRows(Long outputBufferRows) {
		this.outputBufferRows=outputBufferRows;
	}

	public Long getOutputBufferRows(){
		return outputBufferRows;
	}

	public void setNrLinesDeleted(Long nrLinesDeleted) {
		this.nrLinesDeleted=nrLinesDeleted;
	}

	public Long getNrLinesDeleted(){
		return nrLinesDeleted;
	}

	public void setNrLinesInput(Long nrLinesInput) {
		this.nrLinesInput=nrLinesInput;
	}

	public Long getNrLinesInput(){
		return nrLinesInput;
	}

	public void setNrLinesOutput(Long nrLinesOutput) {
		this.nrLinesOutput=nrLinesOutput;
	}

	public Long getNrLinesOutput(){
		return nrLinesOutput;
	}

	public void setNrLinesRead(Long nrLinesRead) {
		this.nrLinesRead=nrLinesRead;
	}

	public Long getNrLinesRead(){
		return nrLinesRead;
	}

	public void setNrLinesRejected(Long nrLinesRejected) {
		this.nrLinesRejected=nrLinesRejected;
	}

	public Long getNrLinesRejected(){
		return nrLinesRejected;
	}

	public void setNrLinesUpdated(Long nrLinesUpdated) {
		this.nrLinesUpdated=nrLinesUpdated;
	}

	public Long getNrLinesUpdated(){
		return nrLinesUpdated;
	}

	public void setNrLinesWritten(Long nrLinesWritten) {
		this.nrLinesWritten=nrLinesWritten;
	}

	public Long getNrLinesWritten(){
		return nrLinesWritten;
	}

	public void setStartdate(String startdate) {
		this.startdate=startdate;
	}

	public String getStartdate(){
		return startdate;
	}

	public void setEnddate(String enddate) {
		this.enddate=enddate;
	}

	public String getEnddate(){
		return enddate;
	}

	public void setLogdate(String logdate) {
		this.logdate=logdate;
	}

	public String getLogdate(){
		return logdate;
	}

	public void setDepdate(String depdate) {
		this.depdate=depdate;
	}

	public String getDepdate(){
		return depdate;
	}

	public void setReplaydate(String replaydate) {
		this.replaydate=replaydate;
	}

	public String getReplaydate(){
		return replaydate;
	}

	public void setExecutingServer(String executingServer) {
		this.executingServer=executingServer;
	}

	public String getExecutingServer(){
		return executingServer;
	}

	public void setExecutingUser(String executingUser) {
		this.executingUser=executingUser;
	}

	public String getExecutingUser(){
		return executingUser;
	}

	public void setClient(String client) {
		this.client=client;
	}

	public String getClient(){
		return client;
	}

	public void setCreateDay(String createDay) {
		this.createDay=createDay;
	}

	public String getCreateDay(){
		return createDay;
	}

	public void setModifyTime(String modifyTime) {
		this.modifyTime=modifyTime;
	}

	public String getModifyTime(){
		return modifyTime;
	}

	public void setLogField(String logField) {
		this.logField=logField;
	}

	public String getLogField(){
		return logField;
	}

	public void setResultFilesList(String resultFilesList) {
		this.resultFilesList=resultFilesList;
	}

	public String getResultFilesList(){
		return resultFilesList;
	}

	public void setLogText(String logText) {
		this.logText=logText;
	}

	public String getLogText(){
		return logText;
	}


}
