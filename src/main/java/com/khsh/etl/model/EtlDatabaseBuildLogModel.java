package com.khsh.etl.model;

import com.ejet.comm.base.CoBaseVO;

public class EtlDatabaseBuildLogModel extends CoBaseVO {

	/**    */
 	private Integer id;
	/**  表etl_database_build标识ID  */
 	private String buildUuid;
	/**  日志级别, 1：debug，2：info 3：warn 4：error  */
 	private Integer logLevel;
	/**  数据名称  */
 	private String logSubject;
	/**  数据类型  */
 	private String logDetail;
    /**  结果：1：成功 0：失败  */
    private Integer logResult;
	/**  状态  */
 	private Integer status;
	/**  备注  */
 	private String remark;
	/**  日志产生日期  */
 	private String createDay;
	/**  修改时间  */
 	private String modifyTime;
	/**  修改人  */
 	private String modifyUser;
	/**  扩展  */
 	private String ext;
	/**  扩展  */
 	private String ext1;
	/**  扩展  */
 	private String ext2;

	public void setId(Integer id) {
		this.id=id;
	}

	public Integer getId(){
		return id;
	}

	public void setBuildUuid(String buildUuid) {
		this.buildUuid=buildUuid;
	}

	public String getBuildUuid(){
		return buildUuid;
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

	public void setLogDetail(String logDetail) {
		this.logDetail=logDetail;
	}

	public String getLogDetail(){
		return logDetail;
	}

	public void setStatus(Integer status) {
		this.status=status;
	}

	public Integer getStatus(){
		return status;
	}

	public void setRemark(String remark) {
		this.remark=remark;
	}

	public String getRemark(){
		return remark;
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

    public Integer getLogResult() {
        return logResult;
    }

    public void setLogResult(Integer logResult) {
        this.logResult = logResult;
    }
}
