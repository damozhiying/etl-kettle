package com.khsh.etl.service.impl;

import com.ejet.comm.PageBean;
import com.ejet.comm.exception.CoBusinessException;
import com.ejet.comm.exception.ExceptionCode;
import com.ejet.comm.utils.time.TimeUtils;
import com.github.pagehelper.PageHelper;
import com.khsh.etl.mapper.SysJobLogDao;
import com.khsh.etl.model.SysJobLogModel;
import com.khsh.etl.service.ISysJobLogService;
import com.khsh.etl.vo.SysJobLogVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("sysJobLogService")
public class SysJobLogServiceImpl implements ISysJobLogService {


	private final Logger log = LoggerFactory.getLogger(SysJobLogServiceImpl.class);

	@Autowired
	private SysJobLogDao mDao;

	public int insertAutoKey(SysJobLogModel model) throws CoBusinessException {
 		 return mDao.insertAutoKey(model);
 	}

	public void update(SysJobLogModel model) throws CoBusinessException {
 		if(model.getId()==null) {
 			throw new CoBusinessException(ExceptionCode.PARAM_MISSING_ID);
 		}
 		 mDao.update(model);
 	}

	public void delete(SysJobLogModel model) throws CoBusinessException {
        if(model.getId()==null) {
            throw new CoBusinessException(ExceptionCode.PARAM_MISSING_ID);
        }
        mDao.delete(model);
 	}

	public SysJobLogModel findByPK(SysJobLogModel model) throws CoBusinessException {
 		 return mDao.findByPK(model);
 	}

	public List<SysJobLogModel>  queryByCond(SysJobLogModel model) throws CoBusinessException {
 		 return mDao.queryByCond(model);
 	}

	public PageBean<SysJobLogVO> queryByPage(SysJobLogVO model, Integer pageNum, Integer pageSize) throws CoBusinessException {
		PageHelper.startPage(pageNum, pageSize);
		if(model!=null) {
		    if(model.getJobStarttime()!=null) {
		        model.setJobStarttime(TimeUtils.formatDate2Time00(model.getJobStarttime(), "yyyy-MM-dd", "yyyy-MM-dd HH:mm:SSS"));
            }
            if(model.getJobEndtime()!=null) {
                model.setJobEndtime(TimeUtils.formatDate2Time59(model.getJobEndtime(), "yyyy-MM-dd", "yyyy-MM-dd HH:mm:SSS"));
            }
        }
		List<SysJobLogVO> list = mDao.queryByPage(model);
		PageBean<SysJobLogVO> page = new PageBean<SysJobLogVO>(list);
		return page;
 	}

	public int insertSingle(SysJobLogModel model) throws CoBusinessException {
 		// 获取最大id。保证连续性
 		Integer maxId = mDao.findMaxId(null);
 		maxId = maxId==null? 1 : maxId+1;
 		model.setId(maxId);
 		mDao.insertSingle(model);
 		return maxId;
 	}


    /**
     * 批量删除
     * @param model
     * @throws CoBusinessException
     */
    public void deleteBatch(SysJobLogVO model) throws CoBusinessException {
        if(model.getIds()==null || model.getIds().size()==0) {
            throw new CoBusinessException(ExceptionCode.PARAM_MISSING);
        }
        mDao.deleteBatch(model);
    }



}
