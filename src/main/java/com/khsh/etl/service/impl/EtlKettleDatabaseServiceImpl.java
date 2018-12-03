package com.khsh.etl.service.impl;


import com.ejet.comm.PageBean;
import com.ejet.comm.exception.CoBusinessException;
import com.ejet.comm.exception.ExceptionCode;
import com.ejet.comm.utils.UuidUtils;
import com.ejet.comm.utils.time.TimeUtils;
import com.github.pagehelper.PageHelper;
import com.khsh.etl.mapper.EtlKettleDatabaseDao;
import com.khsh.etl.model.EtlKettleDatabaseModel;
import com.khsh.etl.service.IEtlKettleDatabaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("etlKettleDatabaseService")
public class EtlKettleDatabaseServiceImpl implements IEtlKettleDatabaseService {

	private final Logger log = LoggerFactory.getLogger(EtlKettleDatabaseServiceImpl.class);
	@Autowired
	private EtlKettleDatabaseDao mDao;

	@Override
	public int insertAutoKey(EtlKettleDatabaseModel model) throws CoBusinessException {
 		return mDao.insertAutoKey(model);
 	}

	@Override
	public void update(EtlKettleDatabaseModel model) throws CoBusinessException {
        if(model.getId()==null && model.getUuid()==null) {
            throw new CoBusinessException(ExceptionCode.PARAM_MISSING_ID);
        }
 		mDao.update(model);
 	}

	@Override
	public void delete(EtlKettleDatabaseModel model) throws CoBusinessException {
        if(model.getId()==null && model.getUuid()==null) {
            throw new CoBusinessException(ExceptionCode.PARAM_MISSING_ID);
        }
	    mDao.delete(model);
 	}

	public EtlKettleDatabaseModel findByPK(EtlKettleDatabaseModel model) throws CoBusinessException {
        if(model.getId()==null && model.getUuid()==null) {
            throw new CoBusinessException(ExceptionCode.PARAM_MISSING_ID);
        }
	    return mDao.findByPK(model);
 	}

	@Override
	public List<EtlKettleDatabaseModel> queryByCond(EtlKettleDatabaseModel model) throws CoBusinessException {
 		return mDao.queryByCond(model);
 	}

	public PageBean<EtlKettleDatabaseModel> queryByPage(EtlKettleDatabaseModel model, Integer pageNum, Integer pageSize) throws CoBusinessException {
		PageHelper.startPage(pageNum, pageSize);
		List<EtlKettleDatabaseModel> list = mDao.queryByPage(model);
		PageBean<EtlKettleDatabaseModel> page = new PageBean<EtlKettleDatabaseModel>(list);
 		return page;
 	}

	public int insertSingle(EtlKettleDatabaseModel model) throws CoBusinessException {
        if(model.getUuid()==null) {
            model.setUuid(UuidUtils.get32UUID());
        }
        model.setModifyTime(TimeUtils.getCurrentFullTime());
        int maxId = mDao.insertAutoKey(model);
 		return maxId;
 	}


}
