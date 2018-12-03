package com.khsh.etl.model;

import com.ejet.comm.base.CoBaseVO;

public class EtlDatabaseModel extends CoBaseVO {

	/**    */
 	private Integer id;
	/**  库ID  */
 	private String uuid;
	/**  库名称  */
 	private String name;
	/**  数据库类型名称  */
 	private String dbType;
	/**  驱动名称  */
 	private String dbDriver;
	/**  数据库url  */
 	private String dbUrl;
	/**  用户名  */
 	private String dbUsername;
	/**  密码  */
 	private String dbPassword;
	/**  状态, 1: 正常，0：禁用  */
 	private Integer status;
	/**  库类型标识, 1: 目标库，0：源库  */
 	private Integer fromTo;
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

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

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

	public void setDbDriver(String dbDriver) {
		this.dbDriver=dbDriver;
	}

	public String getDbDriver(){
		return dbDriver;
	}

	public void setDbUrl(String dbUrl) {
		this.dbUrl=dbUrl;
	}

	public String getDbUrl(){
		return dbUrl;
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

    public Integer getFromTo() {
        return fromTo;
    }

    public void setFromTo(Integer fromTo) {
        this.fromTo = fromTo;
    }
}
