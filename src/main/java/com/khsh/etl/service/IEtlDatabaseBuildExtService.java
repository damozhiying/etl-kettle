package com.khsh.etl.service;

import com.ejet.comm.exception.CoBusinessException;
import com.khsh.etl.model.EtlDatabaseBuildExtModel;

import java.util.List;

public interface IEtlDatabaseBuildExtService {


	public void insertAutoKey(EtlDatabaseBuildExtModel model) throws CoBusinessException;

	public void update(EtlDatabaseBuildExtModel model) throws CoBusinessException;

	public void delete(EtlDatabaseBuildExtModel model) throws CoBusinessException;

	public List<EtlDatabaseBuildExtModel>  queryByCond(EtlDatabaseBuildExtModel model) throws CoBusinessException;


}
