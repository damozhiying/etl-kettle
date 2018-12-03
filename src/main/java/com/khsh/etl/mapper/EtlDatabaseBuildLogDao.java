package com.khsh.etl.mapper;

import com.ejet.comm.exception.CoBusinessException;
import com.khsh.etl.model.EtlDatabaseBuildLogModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EtlDatabaseBuildLogDao {


	public void insertSingle(EtlDatabaseBuildLogModel obj) throws CoBusinessException;

	public int insertAutoKey(EtlDatabaseBuildLogModel obj) throws CoBusinessException;

	public void update(EtlDatabaseBuildLogModel obj) throws CoBusinessException;

	public void delete(EtlDatabaseBuildLogModel obj) throws CoBusinessException;

	public abstract EtlDatabaseBuildLogModel  findByPK(EtlDatabaseBuildLogModel obj) throws CoBusinessException;

	public abstract List<EtlDatabaseBuildLogModel>  queryByCond(EtlDatabaseBuildLogModel obj) throws CoBusinessException;

	public abstract List<EtlDatabaseBuildLogModel>  queryByPage(EtlDatabaseBuildLogModel obj) throws CoBusinessException;

	public abstract Integer  findMaxId(EtlDatabaseBuildLogModel obj) throws CoBusinessException;


}
