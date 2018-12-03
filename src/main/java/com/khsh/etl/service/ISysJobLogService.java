package com.khsh.etl.service;

import com.ejet.comm.exception.CoBusinessException;
import com.khsh.etl.model.SysJobLogModel;

import java.util.List;

public interface ISysJobLogService {


	public int insertAutoKey(SysJobLogModel model) throws CoBusinessException;

	public void update(SysJobLogModel model) throws CoBusinessException;

	public void delete(SysJobLogModel model) throws CoBusinessException;

	public List<SysJobLogModel>  queryByCond(SysJobLogModel model) throws CoBusinessException;


}
