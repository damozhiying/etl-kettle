package com.khsh.etl.controller;

import com.ejet.comm.PageBean;
import com.ejet.comm.Param;
import com.ejet.comm.Result;
import com.ejet.comm.base.CoBaseController;
import com.ejet.comm.exception.CoBusinessException;
import com.khsh.etl.model.EtlKettleRepositoryModel;
import com.khsh.etl.service.impl.EtlKettleRepositoryServiceImpl;
import com.khsh.etl.vo.EtlKettleRepositoryVO;
import com.khsh.etl.vo.KettleJobVO;
import com.khsh.etl.vo.KettleTreeVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.ejet.comm.exception.ExceptionCode.SYS_ERROR;

@RestController
@RequestMapping(value="/etl-kettle-repository")
public class EtlKettleRepositoryController extends CoBaseController {

	private final Logger log = LoggerFactory.getLogger(EtlKettleRepositoryController.class);
	@Autowired
	private EtlKettleRepositoryServiceImpl mService;


	@ResponseBody
	@RequestMapping(value="/query")
	public Result query(@RequestBody(required=false) EtlKettleRepositoryVO model) {
		Result rs = new Result();
		try {
			List<EtlKettleRepositoryModel> page = mService.queryByCond(model);
			rs = new Result(page);
		}catch (CoBusinessException e) {
			log.error("", e);
			rs = new Result(e.getCode(), e);
		}
		return rs;
	}

    @ResponseBody
    @RequestMapping(value="/find-by-pk")
    public Result findByPK(@RequestBody(required=true) EtlKettleRepositoryVO model) {
        Result rs = new Result();
        try {
            EtlKettleRepositoryModel result = mService.findByPK(model);
            rs = new Result(result);
        }catch (CoBusinessException e) {
            log.error("", e);
            rs = new Result(e.getCode(), e);
        }
        return rs;
    }


    @ResponseBody
	@RequestMapping(value="/delete")
	public Result delete(@RequestBody(required=true) EtlKettleRepositoryVO model) {
		Result rs = new Result();
		try{
			mService.delete(model);
		}catch (CoBusinessException e) {
			log.error("", e);
			rs = new Result(e.getCode(), e);
		}
		return rs;
	}


	@ResponseBody
	@RequestMapping(value="/add")
	public Result add(@RequestBody(required=true) EtlKettleRepositoryVO model) {
		Result rs = new Result();
		try{
			mService.insertSingle(model);
		}catch (CoBusinessException e) {
			log.error("", e);
			rs = new Result(e.getCode(), e);
		}
		return rs;
	}


	@ResponseBody
	@RequestMapping(value="/update")
	public Result update(@RequestBody(required=true) EtlKettleRepositoryVO model) {
		Result rs = new Result();
		try{
			mService.update(model);
		}catch (CoBusinessException e) {
			log.error("", e);
			rs = new Result(e.getCode(), e);
		}
		return rs;
	}


	@ResponseBody
	@RequestMapping(value="/query-by-page")
	public Result queryByPage(@RequestBody(required=true) Param<EtlKettleRepositoryVO> param, BindingResult bindResult) {
		Result rs = new Result();
		try{
			checkBindResult(bindResult);
			checkParam(param);
			EtlKettleRepositoryVO model = param.getData();
			PageBean<EtlKettleRepositoryModel> pageBean = mService.queryByPage(model, param.getPage().getPageNum(), param.getPage().getPageSize());
			rs = new Result(pageBean.getPage(), pageBean.getResult());
		}catch (CoBusinessException e) {
			log.error("", e);
			rs = new Result(e.getCode(), e);
		}catch (Exception e) {
			log.error("", e);
			rs = new Result(SYS_ERROR, e);
		}
		return rs;
	}


    @ResponseBody
    @RequestMapping(value="/query-directory-tree")
    public Result queryDirectoryTree(@RequestBody(required=false) Param<KettleJobVO> param) {
        Result rs = new Result();
        try {
            KettleTreeVO pageBean = mService.queryDirectoryTree();
            rs = new Result(pageBean);
        }catch (CoBusinessException e) {
            log.error("", e);
            rs = new Result(e.getCode(), e);
        }catch (Exception e) {
            log.error("", e);
            rs = new Result(SYS_ERROR, e);
        }
        return rs;
    }

    @ResponseBody
    @RequestMapping(value="/query-directory-job")
    public Result queryDirectoryJob(@RequestBody(required=true) Param<KettleJobVO> param, BindingResult bindResult) {
        Result rs = new Result();
        try {
            checkBindResult(bindResult);
            checkParam(param);
            KettleJobVO model = param.getData();
            PageBean<KettleJobVO> pageBean = mService.queryDirectoryJob(model, param.getPage().getPageNum(), param.getPage().getPageSize());
            rs = new Result(param.getPage(), pageBean.getResult());
        }catch (CoBusinessException e) {
            log.error("", e);
            rs = new Result(e.getCode(), e);
        }catch (Exception e) {
            log.error("", e);
            rs = new Result(SYS_ERROR, e);
        }
        return rs;
    }


    /**
     * 查询资源全路径信息
     * @param vo
     * @param bindResult
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/query-job-fullpath")
    public Result queryJobFullpath(@RequestBody(required=true) KettleJobVO vo, BindingResult bindResult) {
        Result rs = new Result();
        try {
            KettleJobVO result = mService.queryJobFullpath(vo);
            rs = new Result(result);
        }catch (CoBusinessException e) {
            log.error("", e);
            rs = new Result(e.getCode(), e);
        }catch (Exception e) {
            log.error("", e);
            rs = new Result(SYS_ERROR, e);
        }
        return rs;
    }

    /**
     * 查询资源全路径信息
     * @param vo
     * @param bindResult
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/query-job-used")
    public Result queryJobUsed(@RequestBody(required=false) EtlKettleRepositoryVO vo, BindingResult bindResult) {
        Result rs = new Result();
        try {
            List<EtlKettleRepositoryVO> result = mService.queryJobUsed(vo);
            rs = new Result(result);
        }catch (CoBusinessException e) {
            log.error("", e);
            rs = new Result(e.getCode(), e);
        }catch (Exception e) {
            log.error("", e);
            rs = new Result(SYS_ERROR, e);
        }
        return rs;
    }

    /**
     * 批量同步资源
     */
    @ResponseBody
    @RequestMapping(value="/add-jobs-batch")
    public Result addJobsBatch(@RequestBody(required=true)List<EtlKettleRepositoryVO> list, BindingResult bindResult) {
        Result rs = new Result();
        try {
           String result = mService.addJobsBatch(list);
           rs.setData(result);
        }catch (CoBusinessException e) {
            log.error("", e);
            rs = new Result(e.getCode(), e);
        }catch (Exception e) {
            log.error("", e);
            rs = new Result(SYS_ERROR, e);
        }
        return rs;
    }




}
