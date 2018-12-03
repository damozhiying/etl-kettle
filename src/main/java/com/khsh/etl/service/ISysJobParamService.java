package com.khsh.etl.service;

import com.ejet.comm.exception.CoBusinessException;
import com.khsh.etl.model.SysJobParamModel;

import java.util.List;

public interface ISysJobParamService {


	public int insertAutoKey(SysJobParamModel model) throws CoBusinessException;

	public void update(SysJobParamModel model) throws CoBusinessException;

	public void delete(SysJobParamModel model) throws CoBusinessException;

	public List<SysJobParamModel>  queryByCond(SysJobParamModel model) throws CoBusinessException;


}
