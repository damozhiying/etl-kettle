package com.khsh.etl.service.impl;

import com.ejet.comm.PageBean;
import com.ejet.comm.exception.CoBusinessException;
import com.ejet.comm.exception.ExceptionCode;
import com.github.pagehelper.PageHelper;
import com.khsh.etl.mapper.KettleMessageLogDao;
import com.khsh.etl.model.KettleMessageLogModel;
import com.khsh.etl.service.IKettleMessageLogService;
import com.khsh.etl.vo.KettleMessageLogVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("kettleMessageLogService")
public class KettleMessageLogServiceImpl implements IKettleMessageLogService {


	private final Logger log = LoggerFactory.getLogger(KettleMessageLogServiceImpl.class);

	@Autowired
	private KettleMessageLogDao mDao;

	@Override
	public int insertAutoKey(KettleMessageLogModel model) throws CoBusinessException {
 		return mDao.insertAutoKey(model);
 	}

	@Override
	public void update(KettleMessageLogModel model) throws CoBusinessException {
 		if(model.getId()==null) {
 			throw new CoBusinessException(ExceptionCode.PARAM_MISSING_ID);
 		}
 		mDao.update(model);
 	}

	@Override
	public void delete(KettleMessageLogModel model) throws CoBusinessException {
 		mDao.delete(model);
 	}

	public KettleMessageLogModel findByPK(KettleMessageLogModel model) throws CoBusinessException {
 		return mDao.findByPK(model);
 	}

	@Override
	public List<KettleMessageLogModel>  queryByCond(KettleMessageLogModel model) throws CoBusinessException {
 		return mDao.queryByCond(model);
 	}


	public int insertSingle(KettleMessageLogModel model) throws CoBusinessException {
 		// 获取最大id。保证连续性
 		Integer maxId = mDao.findMaxId(null);
 		maxId = maxId==null? 1 : maxId+1;
 		model.setId(maxId);
 		mDao.insertSingle(model);
 		return maxId;
 	}

    public PageBean<KettleMessageLogVO> queryByPage(KettleMessageLogVO model, Integer pageNum, Integer pageSize) throws CoBusinessException {
        PageHelper.startPage(pageNum, pageSize);
        List<KettleMessageLogVO> list = mDao.queryByPage(model);
        PageBean<KettleMessageLogVO> page = new PageBean<KettleMessageLogVO>(list);
        return page;
    }

    public List<KettleMessageLogVO> queryJobDetailMessage(KettleMessageLogVO model) throws CoBusinessException {
        List<KettleMessageLogVO> list = mDao.queryJobDetailMessage(model);
        return list;
    }


}
