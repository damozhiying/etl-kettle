package com.khsh.etl.controller.databuilder;

import com.alibaba.fastjson.TypeReference;
import com.ejet.comm.PageBean;
import com.ejet.comm.Param;
import com.ejet.comm.Result;
import com.ejet.comm.base.CoBaseController;
import com.ejet.comm.exception.CoBusinessException;
import com.khsh.etl.model.EtlDatabaseBuildModel;
import com.khsh.etl.service.impl.EtlDatabaseBuildServiceImpl;
import com.khsh.etl.vo.EtlDatabaseBuildVO;
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
@RequestMapping(value="/etl-database-build")
public class EtlDatabaseBuildController extends CoBaseController {

	private final Logger log = LoggerFactory.getLogger(EtlDatabaseBuildController.class);
	@Autowired
	private EtlDatabaseBuildServiceImpl mService;


	@ResponseBody
	@RequestMapping(value="/query")
	public Result query(@RequestBody(required=false) EtlDatabaseBuildVO model) {
		Result rs = new Result();
		try {
			List<EtlDatabaseBuildModel> page = mService.queryByCond(model);
			rs = new Result(page);
		}catch (CoBusinessException e) {
			log.error("", e);
			rs = new Result(e.getCode(), e);
		}
		return rs;
	}

    @ResponseBody
    @RequestMapping(value="/find-by-pk")
	public Result findByPK(@RequestBody(required=false) EtlDatabaseBuildVO model) {
		Result rs = new Result();
		try {
			EtlDatabaseBuildModel result = mService.findByPK(model);
			rs = new Result(result);
		}catch (CoBusinessException e) {
			log.error("", e);
			rs = new Result(e.getCode(), e);
		}
		return rs;
	}


	@ResponseBody
	@RequestMapping(value="/delete")
	public Result delete(@RequestBody(required=true) EtlDatabaseBuildVO model) {
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
	public Result add(@RequestBody(required=true) EtlDatabaseBuildVO model) {
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
	public Result update(@RequestBody(required=true) EtlDatabaseBuildVO model) {
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
	public Result queryByPage(@RequestBody(required=true) Param param, BindingResult bindResult) {
		Result rs = new Result();
		try{
			checkBindResult(bindResult);
			EtlDatabaseBuildVO model = toBean(param, new TypeReference<EtlDatabaseBuildVO>(){});
			PageBean<EtlDatabaseBuildVO> pageBean = mService.queryByPage(model, param.getPage().getPageNum(), param.getPage().getPageSize());
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
    @RequestMapping(value="/add-db-build")
    public Result addDbBuild(@RequestBody(required=true) EtlDatabaseBuildVO model) {
        Result rs = new Result();
        try{
            mService.addDbBuild(model);
        }catch (CoBusinessException e) {
            log.error("", e);
            rs = new Result(e.getCode(), e);
        }
        return rs;
    }

    @ResponseBody
    @RequestMapping(value="/update-db-build")
    public Result updateDbBuild(@RequestBody(required=true) EtlDatabaseBuildVO model) {
        Result rs = new Result();
        try{
            mService.updateDbBuild(model);
        }catch (CoBusinessException e) {
            log.error("", e);
            rs = new Result(e.getCode(), e);
        }
        return rs;
    }

    /**
     * 开始构建
     * @param model
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/start-build")
    public Result startBuild(@RequestBody(required=true) EtlDatabaseBuildVO model) {
        Result rs = new Result();
        try{
            mService.startBuild(model);
        }catch (CoBusinessException e) {
            log.error("", e);
            rs = new Result(e.getCode(), e);
        }
        return rs;
    }


}
