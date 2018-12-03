package com.khsh.etl.model;

import com.ejet.comm.base.CoBaseVO;

public class EtlKettleDatabaseModel extends CoBaseVO {

	/**    */
 	private Integer id;
	/**  库ID  */
 	private String uuid;
	/**  库名称  */
 	private String name;
	/**  数据库类型  */
 	private String dbType;
	/**  数据库访问类型  */
 	private String dbAccess;
	/**  数据库地址  */
 	private String dbHost;
	/**  数据库端口  */
 	private Integer dbPort;
	/**  数据库名  */
 	private String dbDatabase;
	/**  用户名  */
 	private String dbUsername;
	/**  密码  */
 	private String dbPassword;
	/**  kettle资源库id  */
 	private String dbRepositoryId;
	/**  kettle资源库描述  */
 	private String dbDescription;
	/**  扩展参数  */
 	private String dbParam;
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

	public void setName(String name) {
		this.name=name;
	}

	public String getName(){
		return name;
	}

	public void setDbType(String dbType) {
		this.dbType=dbType;
	}

	public String getDbType(){
		return dbType;
	}

	public void setDbAccess(String dbAccess) {
		this.dbAccess=dbAccess;
	}

	public String getDbAccess(){
		return dbAccess;
	}

	public void setDbHost(String dbHost) {
		this.dbHost=dbHost;
	}

	public String getDbHost(){
		return dbHost;
	}

	public void setDbPort(Integer dbPort) {
		this.dbPort=dbPort;
	}

	public Integer getDbPort(){
		return dbPort;
	}

	public void setDbDatabase(String dbDatabase) {
		this.dbDatabase=dbDatabase;
	}

	public String getDbDatabase(){
		return dbDatabase;
	}

	public void setDbUsername(String dbUsername) {
		this.dbUsername=dbUsername;
	}

	public String getDbUsername(){
		return dbUsername;
	}

	public void setDbPassword(String dbPassword) {
		this.dbPassword=dbPassword;
	}

	public String getDbPassword(){
		return dbPassword;
	}

	public void setDbRepositoryId(String dbRepositoryId) {
		this.dbRepositoryId=dbRepositoryId;
	}

	public String getDbRepositoryId(){
		return dbRepositoryId;
	}

	public void setDbDescription(String dbDescription) {
		this.dbDescription=dbDescription;
	}

	public String getDbDescription(){
		return dbDescription;
	}

	public void setDbParam(String dbParam) {
		this.dbParam=dbParam;
	}

	public String getDbParam(){
		return dbParam;
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
