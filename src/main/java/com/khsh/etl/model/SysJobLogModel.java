package com.khsh.etl.model;

import com.ejet.comm.base.CoBaseVO;

public class SysJobLogModel extends CoBaseVO {

	/**    */
 	private Integer id;
	/**  任务ID  */
 	private String jobId;
	/**  任务名称  */
 	private String jobName;
	/**  任务运行开始时间  */
 	private String jobStarttime;
	/**  任务运行结束时间  */
 	private String jobEndtime;
	/**  运行总时长(秒)  */
 	private Integer jobCosttime;
	/**  任务运行状态  */
 	private String jobState;
	/**  运行结果：1、正常 2：终止 3：未完成 4：异常   */
 	private Integer jobResult;
	/**  任务关联业务类型  */
 	private String bussType;
	/**  任务关联业务id  */
 	private String bussUuid;
	/**  任务关联业务名称  */
 	private String bussName;
	/**  日志类型, 1：业务日志，2：系统日志 3：异常日志  */
 	private Integer logType;
	/**  日志级别, 1：debug，2：info 3：warn 4：error  */
 	private Integer logLevel;
	/**  日志简要  */
 	private String logSubject;
	/**  日志详情，关联uuid  */
 	private String logUuidR;
	/**  运行日志文件路径,详情  */
 	private String logPath;
	/**  运行日期  */
 	private String createDay;
	/**  修改时间  */
 	private String modifyTime;
	/**  修改人  */
 	private String modifyUser;
	/**  预留字段  */
 	private String ext;
	/**  预留字段  */
 	private String ext1;
	/**  预留字段  */
 	private String ext2;
	/**  日志详情  */
 	private String logDetail;

	public void setId(Integer id) {
		this.id=id;
	}

	public Integer getId(){
		return id;
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

	public void setJobStarttime(String jobStarttime) {
		this.jobStarttime=jobStarttime;
	}

	public String getJobStarttime(){
		return jobStarttime;
	}

	public void setJobEndtime(String jobEndtime) {
		this.jobEndtime=jobEndtime;
	}

	public String getJobEndtime(){
		return jobEndtime;
	}

	public void setJobCosttime(Integer jobCosttime) {
		this.jobCosttime=jobCosttime;
	}

	public Integer getJobCosttime(){
		return jobCosttime;
	}

	public void setJobState(String jobState) {
		this.jobState=jobState;
	}

	public String getJobState(){
		return jobState;
	}

	public void setJobResult(Integer jobResult) {
		this.jobResult=jobResult;
	}

	public Integer getJobResult(){
		return jobResult;
	}

	public void setBussType(String bussType) {
		this.bussType=bussType;
	}

	public String getBussType(){
		return bussType;
	}

	public void setBussUuid(String bussUuid) {
		this.bussUuid=bussUuid;
	}

	public String getBussUuid(){
		return bussUuid;
	}

	public void setBussName(String bussName) {
		this.bussName=bussName;
	}

	public String getBussName(){
		return bussName;
	}

	public void setLogType(Integer logType) {
		this.logType=logType;
	}

	public Integer getLogType(){
		return logType;
	}

	public void setLogLevel(Integer logLevel) {
		this.logLevel=logLevel;
	}

	public Integer getLogLevel(){
		return logLevel;
	}

	public void setLogSubject(String logSubject) {
		this.logSubject=logSubject;
	}

	public String getLogSubject(){
		return logSubject;
	}

	public void setLogUuidR(String logUuidR) {
		this.logUuidR=logUuidR;
	}

	public String getLogUuidR(){
		return logUuidR;
	}

	public void setLogPath(String logPath) {
		this.logPath=logPath;
	}

	public String getLogPath(){
		return logPath;
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

	public void setModifyUser(String modifyUser) {
		this.modifyUser=modifyUser;
	}

	public String getModifyUser(){
		return modifyUser;
	}

	public void setExt(String ext) {
		this.ext=ext;
	}

	public String getExt(){
		return ext;
	}

	public void setExt1(String ext1) {
		this.ext1=ext1;
	}

	public String getExt1(){
		return ext1;
	}

	public void setExt2(String ext2) {
		this.ext2=ext2;
	}

	public String getExt2(){
		return ext2;
	}

	public void setLogDetail(String logDetail) {
		this.logDetail=logDetail;
	}

	public String getLogDetail(){
		return logDetail;
	}


}
