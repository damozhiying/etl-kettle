package com.khsh.etl.service.impl;

import com.ejet.comm.PageBean;
import com.ejet.comm.exception.CoBusinessException;
import com.ejet.comm.exception.ExceptionCode;
import com.ejet.comm.utils.time.TimeUtils;
import com.ejet.global.CoConstant;
import com.github.pagehelper.PageHelper;
import com.khsh.etl.mapper.EtlDatabaseBuildLogDao;
import com.khsh.etl.model.EtlDatabaseBuildLogModel;
import com.khsh.etl.service.IEtlDatabaseBuildLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("etlDatabaseBuildLogService")
public class EtlDatabaseBuildLogServiceImpl implements IEtlDatabaseBuildLogService {

	private final Logger log = LoggerFactory.getLogger(EtlDatabaseBuildLogServiceImpl.class);

	@Autowired
	private EtlDatabaseBuildLogDao mDao;

	@Override
	public void insertAutoKey(EtlDatabaseBuildLogModel model) throws CoBusinessException {
        model.setModifyTime(TimeUtils.getCurrentFullTime());
        model.setStatus(model.getStatus()==null ? CoConstant.STATUS_NORMAL : model.getStatus());
 		mDao.insertAutoKey(model);
 	}

	@Override
	public void update(EtlDatabaseBuildLogModel model) throws CoBusinessException {
 		if(model.getId()==null) {
 			throw new CoBusinessException(ExceptionCode.PARAM_MISSING_ID);
 		}
 		mDao.update(model);
 	}

	@Override
	public void delete(EtlDatabaseBuildLogModel model) throws CoBusinessException {
 		mDao.delete(model);
 	}

	public EtlDatabaseBuildLogModel  findByPK(EtlDatabaseBuildLogModel model) throws CoBusinessException {
 		return mDao.findByPK(model);
 	}

	@Override
	public List<EtlDatabaseBuildLogModel>  queryByCond(EtlDatabaseBuildLogModel model) throws CoBusinessException {
 		return mDao.queryByCond(model);
 	}

	public PageBean<EtlDatabaseBuildLogModel> queryByPage(EtlDatabaseBuildLogModel model, Integer pageNum, Integer pageSize) throws CoBusinessException {
		PageHelper.startPage(pageNum, pageSize);
		List<EtlDatabaseBuildLogModel> list = mDao.queryByPage(model);
		PageBean<EtlDatabaseBuildLogModel> page = new PageBean<EtlDatabaseBuildLogModel>(list);
 		return page;
 	}

	public int insertSingle(EtlDatabaseBuildLogModel model) throws CoBusinessException {
 		// 获取最大id。保证连续性
 		Integer maxId = mDao.findMaxId(null);
 		maxId = maxId==null? 1 : maxId+1;
 		model.setId(maxId);
 		mDao.insertSingle(model);
 		return maxId;
 	}


}
