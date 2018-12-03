package com.khsh.etl.service;

import com.ejet.comm.exception.CoBusinessException;
import com.khsh.etl.model.KettleMessageLogModel;

import java.util.List;

public interface IKettleMessageLogService {


	public int insertAutoKey(KettleMessageLogModel model) throws CoBusinessException;

	public void update(KettleMessageLogModel model) throws CoBusinessException;

	public void delete(KettleMessageLogModel model) throws CoBusinessException;

	public List<KettleMessageLogModel>  queryByCond(KettleMessageLogModel model) throws CoBusinessException;


}
