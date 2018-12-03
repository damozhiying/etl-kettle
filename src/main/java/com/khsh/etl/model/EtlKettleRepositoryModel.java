package com.khsh.etl.model;

import com.ejet.comm.base.CoBaseVO;

public class EtlKettleRepositoryModel extends CoBaseVO {

	/**    */
 	private Integer id;
	/**  标识uuid  */
 	private String uuid;
	/**    */
 	private String databaseUuid;
	/**  作业名称  */
 	private String ktlJobName;
	/**  kette作业类型: KJBKTR  */
 	private String ktlJobType;
	/**  资源类型: DBFILE  */
 	private String repType;
	/**  资源路径  */
 	private String repPath;
	/**  资源目录  */
 	private String repDirectory;
	/**  资源文件名  */
 	private String repFilename;
	/**  kettle资源id  */
 	private Long repId;
	/**  kettle资源目录id  */
 	private Integer repIdDirectory;
	/**  kettle参数，采用json，key：value键值对  */
 	private String ktlParamValue;
	/**  状态, 1: 正常，0：禁用  */
 	private Integer status;
	/**  备注,描述  */
 	private String remark;
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

	public void setDatabaseUuid(String databaseUuid) {
		this.databaseUuid=databaseUuid;
	}

	public String getDatabaseUuid(){
		return databaseUuid;
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

	public void setRepType(String repType) {
		this.repType=repType;
	}

	public String getRepType(){
		return repType;
	}

	public void setRepPath(String repPath) {
		this.repPath=repPath;
	}

	public String getRepPath(){
		return repPath;
	}

	public void setRepDirectory(String repDirectory) {
		this.repDirectory=repDirectory;
	}

	public String getRepDirectory(){
		return repDirectory;
	}

	public void setRepFilename(String repFilename) {
		this.repFilename=repFilename;
	}

	public String getRepFilename(){
		return repFilename;
	}

	public void setRepId(Long repId) {
		this.repId=repId;
	}

	public Long getRepId(){
		return repId;
	}

	public void setRepIdDirectory(Integer repIdDirectory) {
		this.repIdDirectory=repIdDirectory;
	}

	public Integer getRepIdDirectory(){
		return repIdDirectory;
	}

	public void setKtlParamValue(String ktlParamValue) {
		this.ktlParamValue=ktlParamValue;
	}

	public String getKtlParamValue(){
		return ktlParamValue;
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


}
