package com.khsh.etl.service.impl;


import com.ejet.comm.PageBean;
import com.ejet.comm.db.CoDbConfig;
import com.ejet.comm.exception.CoBusinessException;
import com.ejet.comm.exception.ExceptionCode;
import com.ejet.comm.utils.StringUtils;
import com.ejet.comm.utils.UuidUtils;
import com.ejet.comm.utils.io.IOUtils;
import com.ejet.comm.utils.time.TimeUtils;
import com.github.pagehelper.PageHelper;
import com.khsh.etl.databuilder.TablesBuilder;
import com.khsh.etl.databuilder.db.dialect.DbColumnTypeEnum;
import com.khsh.etl.databuilder.db.meta.DbColumnMeta;
import com.khsh.etl.databuilder.db.meta.DbTableMeta;
import com.khsh.etl.mapper.EtlDatabaseBuildDao;
import com.khsh.etl.model.EtlDatabaseBuildExtModel;
import com.khsh.etl.model.EtlDatabaseBuildLogModel;
import com.khsh.etl.model.EtlDatabaseBuildModel;
import com.khsh.etl.model.EtlDatabaseModel;
import com.khsh.etl.service.IEtlDatabaseBuildService;
import com.khsh.etl.vo.EtlDatabaseBuildVO;
import com.khsh.etl.vo.EtlDatabaseVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service("etlDatabaseBuildService")
public class EtlDatabaseBuildServiceImpl implements IEtlDatabaseBuildService {


	private final Logger log = LoggerFactory.getLogger(EtlDatabaseBuildServiceImpl.class);

	@Autowired
	private EtlDatabaseBuildDao mDao;

    @Autowired
    private EtlDatabaseBuildExtServiceImpl databaseBuildExtService;

    @Autowired
    private EtlDatabaseServiceImpl databaseService;

    @Autowired
    private EtlDatabaseBuildLogServiceImpl databaseBuildLogService;

	public void insertAutoKey(EtlDatabaseBuildModel model) throws CoBusinessException {
 		 mDao.insertAutoKey(model);
 	}

	public void update(EtlDatabaseBuildModel model) throws CoBusinessException {
 		if(model.getId()==null && model.getUuid()==null) {
 			throw new CoBusinessException(ExceptionCode.PARAM_MISSING_ID);
 		}
 		 mDao.update(model);
 	}

	public void delete(EtlDatabaseBuildModel model) throws CoBusinessException {
 		 mDao.delete(model);
 	}

	public EtlDatabaseBuildModel  findByPK(EtlDatabaseBuildModel model) throws CoBusinessException {
        if(model.getId()==null && model.getUuid()==null) {
            throw new CoBusinessException(ExceptionCode.PARAM_MISSING_ID);
        }
 		return mDao.findByPK(model);
 	}

	public List<EtlDatabaseBuildModel>  queryByCond(EtlDatabaseBuildModel model) throws CoBusinessException {
 		 return mDao.queryByCond(model);
 	}

	public PageBean<EtlDatabaseBuildVO> queryByPage(EtlDatabaseBuildModel model, Integer pageNum, Integer pageSize) throws CoBusinessException {
		PageHelper.startPage(pageNum, pageSize);
		List<EtlDatabaseBuildVO> list = mDao.queryByPage(model);
		PageBean<EtlDatabaseBuildVO> page = new PageBean<EtlDatabaseBuildVO>(list);

 		 return page;
 	}

	public int insertSingle(EtlDatabaseBuildModel model) throws CoBusinessException {
        if(model.getUuid()==null) {
            model.setUuid(UuidUtils.get32UUID());
        }
        model.setModifyTime(TimeUtils.getCurrentFullTime());
        int maxId = mDao.insertAutoKey(model);
        return maxId;
 	}


    /**
     * 添加同步数据规则
     * @param model
     * @return
     * @throws CoBusinessException
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT,
            timeout=36000, rollbackFor={Exception.class, CoBusinessException.class})
    public int addDbBuild(EtlDatabaseBuildVO model) throws CoBusinessException {
        //先入库
        String uuid = UuidUtils.get32UUID();
        model.setUuid(uuid);
        int maxId = insertSingle(model);

        if(model.getDbextlist()!=null) {
            for (EtlDatabaseBuildExtModel ext: model.getDbextlist()) {
                if(StringUtils.isBlank(ext.getColumnName()) || StringUtils.isBlank(ext.getColumnType())) {
                    throw new CoBusinessException(ExceptionCode.SYS_ERROR, "参数字段名称、类型为空!");
                }
                ext.setBuildUuid(uuid);
            }
            databaseBuildExtService.insertBatch(model.getDbextlist());
        }
        return maxId;
    }

    /**
     * 修改同步数据规则
     * @param model
     * @return
     * @throws CoBusinessException
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT,
            timeout=36000, rollbackFor={Exception.class, CoBusinessException.class})
    public void updateDbBuild(EtlDatabaseBuildVO model) throws CoBusinessException {
        if(model.getUuid()==null) {
            throw new CoBusinessException(ExceptionCode.PARAM_MISSING_ID);
        }
        update(model);

        EtlDatabaseBuildExtModel delExt = new EtlDatabaseBuildExtModel();
        delExt.setBuildUuid(model.getUuid());
        databaseBuildExtService.delete(delExt);
        if(model.getDbextlist()!=null) {
            for (EtlDatabaseBuildExtModel ext: model.getDbextlist()) {
                ext.setBuildUuid(model.getUuid());
            }
            databaseBuildExtService.insertBatch(model.getDbextlist());
        }
    }


    /**
     *  开始构建
     */
    public void startBuild(EtlDatabaseBuildVO model) throws CoBusinessException {
        if(model.getUuid()==null) {
            throw new CoBusinessException(ExceptionCode.PARAM_MISSING_ID);
        }
        //查询构建信息
        EtlDatabaseBuildModel query = new EtlDatabaseBuildModel();
        query.setUuid(model.getUuid());
        EtlDatabaseBuildModel build = findByPK(query);
        if(build==null) {
            throw new CoBusinessException(ExceptionCode.SYS_ERROR, "查询同步信息，数据为空!");
        }

        //查询数据库信息
        EtlDatabaseVO from = new EtlDatabaseVO();
        from.setUuid(build.getDatabaseUuidFrom());
        EtlDatabaseModel fromModel = databaseService.findByPK(from);

        EtlDatabaseVO to = new EtlDatabaseVO();
        to.setUuid(build.getDatabaseUuidTo());
        EtlDatabaseModel toModel = databaseService.findByPK(to);

        if(from==null || to==null) {
            throw new CoBusinessException(ExceptionCode.SYS_ERROR, "查询同步源库或者目标库，数据为!");
        }

        CoDbConfig fromconfig = convert(fromModel);
        CoDbConfig destconfig = convert(toModel);

        TablesBuilder syn = new TablesBuilder(fromconfig, destconfig);
        String ignoreStr = build.getIgnoreTables();
        if(ignoreStr!=null && !ignoreStr.trim().equals("")) {
            String[] ignores = ignoreStr.split(",");
            syn.setIgnoreTables(Arrays.asList(ignores));
        }

        EtlDatabaseBuildExtModel queryExt = new EtlDatabaseBuildExtModel();
        queryExt.setBuildUuid(build.getUuid());
        List<EtlDatabaseBuildExtModel> extModelList = databaseBuildExtService.queryByCond(queryExt);
        if(extModelList!=null) {
            List<DbTableMeta> list = new ArrayList<>();
            List<DbColumnMeta> columns = new ArrayList<>();
            for(EtlDatabaseBuildExtModel item : extModelList) {
                DbColumnMeta column = new DbColumnMeta();
                column.setLength(item.getColumnLength());
                column.setColumnType(DbColumnTypeEnum.getColTypeByname(item.getColumnType()));
                column.setDataScale(item.getColumnScale());
                column.setColumnName(item.getColumnName());
                column.setNull(true);
                if(column.getColumnType()==null) {
                    throw new CoBusinessException(ExceptionCode.SYS_ERROR, "字段:" + column.getColumnName() + ", 类型为空!");
                }
            }
            DbTableMeta meta = new DbTableMeta(null, columns); //针对所有表，后期可以扩展单表设置添加字段
            list.add(meta);
            syn.setAddition(list);
        }
        syn.setTablePrefix(build.getTablePrefix());

        boolean isDrop = build.getIsDrop()!=null && build.getIsDrop().equals("1");
        Integer logResult = 1;
        Integer logLevel = 2;//1：debug，2：info 3：warn 4：error
        String error = "";
        try {
            syn.build(isDrop);
        } catch (Exception e) {
            log.error("同步错误", e);
            logResult = 0;
            logLevel = 4;
            error = IOUtils.getError(e);
        }
        buildLog(build.getUuid(), logLevel,logResult,  syn.getMsg(), error);


    }


    /**
     * 记录错误日志
     * @throws CoBusinessException
     */
    public void buildLog(String uuid, Integer logLevel, Integer logResult, String subject, String detail) throws CoBusinessException {

        EtlDatabaseBuildLogModel log = new EtlDatabaseBuildLogModel();
        //log.setBuildUuid();
        log.setCreateDay(TimeUtils.getCurrentFullDay());
        log.setBuildUuid(uuid);
        log.setLogLevel(logLevel);
        log.setLogSubject(subject);
        log.setLogResult(logResult);
        log.setLogDetail(detail);
        databaseBuildLogService.insertAutoKey(log);

    }


    /**
     * 对象转换
     * @param model
     * @return
     */
    public CoDbConfig convert(EtlDatabaseModel model) {
        CoDbConfig config = new CoDbConfig();
        config.setDriverClassName(model.getDbDriver());
        config.setUrl(model.getDbUrl());
        config.setUsername(model.getDbUsername());
        config.setPassword(model.getDbPassword());
        return config;
    }


}
