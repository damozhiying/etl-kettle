package com.khsh.etl.service;

import com.ejet.comm.exception.CoBusinessException;
import com.khsh.etl.model.EtlKettleRepositoryModel;

import java.util.List;

public interface IEtlKettleRepositoryService {


	public void insertAutoKey(EtlKettleRepositoryModel model) throws CoBusinessException;

	public void update(EtlKettleRepositoryModel model) throws CoBusinessException;

	public void delete(EtlKettleRepositoryModel model) throws CoBusinessException;

	public List<EtlKettleRepositoryModel>  queryByCond(EtlKettleRepositoryModel model) throws CoBusinessException;


}
