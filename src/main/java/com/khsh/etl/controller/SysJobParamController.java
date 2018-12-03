package com.khsh.etl.controller;

import com.ejet.comm.PageBean;
import com.ejet.comm.Param;
import com.ejet.comm.Result;
import com.ejet.comm.base.CoBaseController;
import com.ejet.comm.exception.CoBusinessException;
import com.khsh.etl.model.SysJobParamModel;
import com.khsh.etl.service.impl.SysJobParamServiceImpl;
import com.khsh.etl.vo.SysJobParamKettleVO;
import com.khsh.etl.vo.SysJobParamVO;
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
@RequestMapping(value="/sys-job-param")
public class SysJobParamController extends CoBaseController {

	private final Logger log = LoggerFactory.getLogger(SysJobParamController.class);
	@Autowired
	private SysJobParamServiceImpl mService;


	@ResponseBody
	@RequestMapping(value="/query")
	public Result query(@RequestBody(required=false) SysJobParamVO model) {
		Result rs = new Result();
		try {
			List<SysJobParamModel> page = mService.queryByCond(model);
			rs = new Result(page);
		}catch (CoBusinessException e) {
			log.error("", e);
			rs = new Result(e.getCode(), e);
		}
		return rs;
	}


    @ResponseBody
    @RequestMapping(value="/find-by-pk")
    public Result findByPK(@RequestBody(required=false) SysJobParamVO model) {
        Result rs = new Result();
        try {
            SysJobParamModel result = mService.findByPK(model);
            rs = new Result(result);
        }catch (CoBusinessException e) {
            log.error("", e);
            rs = new Result(e.getCode(), e);
        }
        return rs;
    }

	@ResponseBody
	@RequestMapping(value="/delete")
	public Result delete(@RequestBody(required=true) SysJobParamVO model) {
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
	public Result add(@RequestBody(required=true) SysJobParamVO model) {
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
	public Result update(@RequestBody(required=true) SysJobParamVO model) {
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
	public Result queryByPage(@RequestBody(required=true) Param<SysJobParamVO> param, BindingResult bindResult) {
		Result rs = new Result();
		try{
			checkBindResult(bindResult);
			checkParam(param);
			SysJobParamVO model = param.getData();
			PageBean<SysJobParamModel> pageBean = mService.queryByPage(model, param.getPage().getPageNum(), param.getPage().getPageSize());
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
    @RequestMapping(value="/add-kettle")
    public Result addKettle(@RequestBody(required=true) SysJobParamVO model) {
        Result rs = new Result();
        try{
            mService.addKettle(model);
        }catch (CoBusinessException e) {
            log.error("", e);
            rs = new Result(e.getCode(), e);
        }
        return rs;
    }

    @ResponseBody
    @RequestMapping(value="/update-kettle")
    public Result updateKettle(@RequestBody(required=true) SysJobParamVO model) {
        Result rs = new Result();
        try{
            mService.updateKettle(model);
        }catch (CoBusinessException e) {
            log.error("", e);
            rs = new Result(e.getCode(), e);
        }
        return rs;
    }

    @ResponseBody
    @RequestMapping(value="/query-kettle-by-page")
    public Result queryKettleByPage(@RequestBody(required=true) Param<SysJobParamKettleVO> param, BindingResult bindResult) {
        Result rs = new Result();
        try{
            checkBindResult(bindResult);
            checkParam(param);
            SysJobParamKettleVO model = param.getData();
            PageBean<SysJobParamKettleVO> pageBean = mService.queryKettleByPage(model, param.getPage().getPageNum(), param.getPage().getPageSize());
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
    @RequestMapping(value="/find-kettle-by-pk")
    public Result findKettleByPK(@RequestBody(required=false) SysJobParamKettleVO model) {
        Result rs = new Result();
        try {
            SysJobParamKettleVO result = mService.findKettleByPK(model);
            rs = new Result(result);
        }catch (CoBusinessException e) {
            log.error("", e);
            rs = new Result(e.getCode(), e);
        }
        return rs;
    }


}
