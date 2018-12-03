package com.khsh.etl.controller;

import com.ejet.comm.PageBean;
import com.ejet.comm.Param;
import com.ejet.comm.Result;
import com.ejet.comm.base.CoBaseController;
import com.ejet.comm.exception.CoBusinessException;
import com.khsh.etl.model.SysJobLogModel;
import com.khsh.etl.service.impl.SysJobLogServiceImpl;
import com.khsh.etl.vo.SysJobLogVO;
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
@RequestMapping(value="/sys-job-log")
public class SysJobLogController extends CoBaseController {

	private final Logger log = LoggerFactory.getLogger(SysJobLogController.class);
	@Autowired
	private SysJobLogServiceImpl mService;


	@ResponseBody
	@RequestMapping(value="/query")
	public Result query(@RequestBody(required=false) SysJobLogModel model) {
		Result rs = new Result();
		try {
			List<SysJobLogModel> page = mService.queryByCond(model);
			rs = new Result(page);
		}catch (CoBusinessException e) {
			log.error("", e);
			rs = new Result(e.getCode(), e);
		}
		return rs;
	}


    @ResponseBody
    @RequestMapping(value="/delete")
    public Result delete(@RequestBody(required=true) SysJobLogModel model) {
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
	@RequestMapping(value="/delete-batch")
	public Result deleteBatch(@RequestBody(required=true) SysJobLogVO model) {
		Result rs = new Result();
		try{
			mService.deleteBatch(model);
		}catch (CoBusinessException e) {
			log.error("", e);
			rs = new Result(e.getCode(), e);
		}
		return rs;
	}


	@ResponseBody
	@RequestMapping(value="/add")
	public Result add(@RequestBody(required=true) SysJobLogModel model) {
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
	public Result update(@RequestBody(required=true) SysJobLogModel model) {
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
	public Result queryByPage(@RequestBody(required=true) Param<SysJobLogVO> param, BindingResult bindResult) {
		Result rs = new Result();
		try{
			checkBindResult(bindResult);
			checkParam(param);
            SysJobLogVO model = param.getData();
			PageBean<SysJobLogVO> pageBean = mService.queryByPage(model, param.getPage().getPageNum(), param.getPage().getPageSize());
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


}
