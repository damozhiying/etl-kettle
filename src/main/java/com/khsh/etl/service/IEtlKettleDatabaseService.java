package com.khsh.etl.service;

import com.ejet.comm.exception.CoBusinessException;
import com.khsh.etl.model.EtlKettleDatabaseModel;

import java.util.List;

public interface IEtlKettleDatabaseService {


	public int insertAutoKey(EtlKettleDatabaseModel model) throws CoBusinessException;

	public void update(EtlKettleDatabaseModel model) throws CoBusinessException;

	public void delete(EtlKettleDatabaseModel model) throws CoBusinessException;

	public EtlKettleDatabaseModel findByPK(EtlKettleDatabaseModel model) throws CoBusinessException;

	public List<EtlKettleDatabaseModel>  queryByCond(EtlKettleDatabaseModel model) throws CoBusinessException;


}
