package com.khsh.etl.service.impl;

import com.ejet.comm.PageBean;
import com.ejet.comm.exception.CoBusinessException;
import com.ejet.comm.exception.ExceptionCode;
import com.ejet.comm.utils.StringUtils;
import com.ejet.comm.utils.time.TimeUtils;
import com.ejet.global.CoConstant;
import com.github.pagehelper.PageHelper;
import com.khsh.etl.kettle.kit.ConstantKettle;
import com.khsh.etl.mapper.SysJobParamDao;
import com.khsh.etl.model.SysJobParamModel;
import com.khsh.etl.service.ISysJobParamService;
import com.khsh.etl.vo.SysJobParamKettleVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("sysJobParamService")
public class SysJobParamServiceImpl implements ISysJobParamService {


	private final Logger log = LoggerFactory.getLogger(SysJobParamServiceImpl.class);

	@Autowired
	private SysJobParamDao mDao;

	public int insertAutoKey(SysJobParamModel model) throws CoBusinessException {
	    model.setModifyTime(TimeUtils.getCurrentTimeInString());
	    model.setStatus( model.getStatus()==null ? CoConstant.STATUS_NORMAL : model.getStatus() );
 		return mDao.insertAutoKey(model);
 	}

	public void update(SysJobParamModel model) throws CoBusinessException {
 		if(model.getId()==null) {
 			throw new CoBusinessException(ExceptionCode.PARAM_MISSING_ID);
 		}
 		mDao.update(model);
 	}

	public void delete(SysJobParamModel model) throws CoBusinessException {
        if(model.getId()==null) {
            throw new CoBusinessException(ExceptionCode.PARAM_MISSING_ID);
        }
        mDao.delete(model);
 	}

	public SysJobParamModel findByPK(SysJobParamModel model) throws CoBusinessException {
	    if(model.getId()==null) {
            throw new CoBusinessException(ExceptionCode.PARAM_MISSING_ID);
        }
 		return mDao.findByPK(model);
 	}

	public List<SysJobParamModel>  queryByCond(SysJobParamModel model) throws CoBusinessException {
 		 return mDao.queryByCond(model);
 	}

	public PageBean<SysJobParamModel> queryByPage(SysJobParamModel model, Integer pageNum, Integer pageSize) throws CoBusinessException {
		PageHelper.startPage(pageNum, pageSize);
		List<SysJobParamModel> list = mDao.queryByPage(model);
		PageBean<SysJobParamModel> page = new PageBean<SysJobParamModel>(list);

 		 return page;
 	}

	public int insertSingle(SysJobParamModel model) throws CoBusinessException {
 		Integer maxId = null;
 		model.setId(maxId);
 		if(StringUtils.isBlank(model.getJobId()) || StringUtils.isBlank(model.getJobName())) {
 		    throw new CoBusinessException(ExceptionCode.SYS_ERROR, "任务参数为空！");
        }
        if(StringUtils.isBlank(model.getParamName())) {
            throw new CoBusinessException(ExceptionCode.SYS_ERROR, "参数名称为空！");
        }
        if(StringUtils.isBlank(model.getParamValue())) {
            throw new CoBusinessException(ExceptionCode.SYS_ERROR, "参数值为空！");
        }
        maxId = insertAutoKey(model);
 		return maxId;
 	}



    public int addKettle(SysJobParamModel model) throws CoBusinessException {
        if(StringUtils.isBlank(model.getJobId()) || StringUtils.isBlank(model.getJobName())) {
            throw new CoBusinessException(ExceptionCode.SYS_ERROR, "任务参数为空！");
        }
        if(StringUtils.isBlank(model.getParamName())) {
            throw new CoBusinessException(ExceptionCode.SYS_ERROR, "参数名称为空！");
        }
        if(StringUtils.isBlank(model.getParamValue())) {
            throw new CoBusinessException(ExceptionCode.SYS_ERROR, "参数值为空！");
        }
        model.setParamType(ConstantKettle.KETTLE); //kettle

        return insertAutoKey(model);
    }


    public void updateKettle(SysJobParamModel model) throws CoBusinessException {
        if(model.getId()==null) {
            throw new CoBusinessException(ExceptionCode.PARAM_MISSING_ID);
        }
        mDao.update(model);
    }


    public PageBean<SysJobParamKettleVO> queryKettleByPage(SysJobParamKettleVO model, Integer pageNum, Integer pageSize) throws CoBusinessException {
        PageHelper.startPage(pageNum, pageSize);
        List<SysJobParamKettleVO> list = mDao.queryKettleByPage(model);
        PageBean<SysJobParamKettleVO> page = new PageBean<SysJobParamKettleVO>(list);
        return page;
    }


    public SysJobParamKettleVO findKettleByPK(SysJobParamKettleVO model) throws CoBusinessException {
        if(model.getId()==null) {
            throw new CoBusinessException(ExceptionCode.PARAM_MISSING_ID);
        }
        return mDao.findKettleByPK(model);
    }


}
