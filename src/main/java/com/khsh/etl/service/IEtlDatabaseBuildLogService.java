package com.khsh.etl.service;

import com.ejet.comm.exception.CoBusinessException;
import com.khsh.etl.model.EtlDatabaseBuildLogModel;

import java.util.List;

public interface IEtlDatabaseBuildLogService {


	public void insertAutoKey(EtlDatabaseBuildLogModel model) throws CoBusinessException;

	public void update(EtlDatabaseBuildLogModel model) throws CoBusinessException;

	public void delete(EtlDatabaseBuildLogModel model) throws CoBusinessException;

	public List<EtlDatabaseBuildLogModel>  queryByCond(EtlDatabaseBuildLogModel model) throws CoBusinessException;


}
