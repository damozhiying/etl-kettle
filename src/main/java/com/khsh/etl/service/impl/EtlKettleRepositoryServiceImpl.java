package com.khsh.etl.service.impl;

import com.ejet.comm.PageBean;
import com.ejet.comm.db.CoDbFactory;
import com.ejet.comm.exception.CoBusinessException;
import com.ejet.comm.exception.ExceptionCode;
import com.ejet.comm.utils.PropertyUtils;
import com.ejet.comm.utils.StringUtils;
import com.ejet.comm.utils.UuidUtils;
import com.ejet.comm.utils.io.FileUtils;
import com.ejet.comm.utils.io.IOUtils;
import com.ejet.comm.utils.time.TimeUtils;
import com.ejet.global.CoConstant;
import com.github.pagehelper.PageHelper;
import com.khsh.etl.databuilder.utils.DbUtils;
import com.khsh.etl.kettle.kit.ConstantKettle;
import com.khsh.etl.kettle.kit.KettleRepositoryConfig;
import com.khsh.etl.mapper.EtlKettleRepositoryDao;
import com.khsh.etl.model.EtlKettleDatabaseModel;
import com.khsh.etl.model.EtlKettleRepositoryModel;
import com.khsh.etl.model.KettleDirectoryModel;
import com.khsh.etl.service.IEtlKettleRepositoryService;
import com.khsh.etl.vo.EtlKettleRepositoryVO;
import com.khsh.etl.vo.KettleJobVO;
import com.khsh.etl.vo.KettleTreeVO;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("etlKettleRepositoryService")
public class EtlKettleRepositoryServiceImpl implements IEtlKettleRepositoryService {

	private final Logger log = LoggerFactory.getLogger(EtlKettleRepositoryServiceImpl.class);

	@Autowired
	private EtlKettleRepositoryDao mDao;

    @Autowired
    EtlKettleDatabaseServiceImpl kettleDatabaseService;

	public void insertAutoKey(EtlKettleRepositoryModel model) throws CoBusinessException {
 		 mDao.insertAutoKey(model);
 	}

	public void update(EtlKettleRepositoryModel model) throws CoBusinessException {
 		if(model.getId()==null) {
 			throw new CoBusinessException(ExceptionCode.PARAM_MISSING_ID);
 		}
        if(StringUtils.isBlank(model.getRepType())
                || StringUtils.isBlank(model.getRepPath())) {
            throw new CoBusinessException(ExceptionCode.PARAM_MISSING_ID);
        }

        if(!ConstantKettle.REP_TYPE_DB.equalsIgnoreCase(model.getRepType()) && !ConstantKettle.REP_TYPE_FILE.equalsIgnoreCase(model.getRepType()) ) { //db存储
            throw new CoBusinessException("作业存储类型非法!");
        }
        //设置作业类型
        set(model);

        if( !model.getKtlJobType().equalsIgnoreCase("KTR") && !model.getKtlJobType().equalsIgnoreCase("KJB") ) {
            throw new CoBusinessException("kettle作业类型非法");
        }
        model.setModifyTime(TimeUtils.getCurrentFullTime());
        mDao.update(model);
 	}

	public void delete(EtlKettleRepositoryModel model) throws CoBusinessException {
 		 mDao.delete(model);
 	}

	public EtlKettleRepositoryModel findByPK(EtlKettleRepositoryModel model) throws CoBusinessException {
        if(model.getId()==null && model.getUuid()==null) {
            throw new CoBusinessException(ExceptionCode.PARAM_MISSING_ID);
        }
 		 return mDao.findByPK(model);
 	}

	public List<EtlKettleRepositoryModel>  queryByCond(EtlKettleRepositoryModel model) throws CoBusinessException {
 		 return mDao.queryByCond(model);
 	}

	public PageBean<EtlKettleRepositoryModel> queryByPage(EtlKettleRepositoryModel model, Integer pageNum, Integer pageSize) throws CoBusinessException {
		PageHelper.startPage(pageNum, pageSize);
		List<EtlKettleRepositoryModel> list = mDao.queryByPage(model);
		PageBean<EtlKettleRepositoryModel> page = new PageBean<EtlKettleRepositoryModel>(list);

 		 return page;
 	}

 	public void set(EtlKettleRepositoryModel model) {
        //设置作业类型
        if(ConstantKettle.REP_TYPE_FILE.equalsIgnoreCase(model.getRepType())) { //文件
            String suffix = FileUtils.getFileExtension(model.getRepPath());
            String folderName = FileUtils.getFolderName(model.getRepPath());
            model.setRepDirectory(folderName);
            model.setRepFilename(FileUtils.getFileName(model.getRepPath()));

        } else if(ConstantKettle.REP_TYPE_DB.equalsIgnoreCase(model.getRepType())) { //db数据库
            model.setRepDirectory(model.getRepPath().substring(0, model.getRepPath().toLowerCase().lastIndexOf("/")));
            if(model.getRepDirectory()==null || model.getRepDirectory().trim().equals("")) {
                model.setRepDirectory(model.getRepPath().contains("/") ? "/" : "");
            }
            String fileName  = model.getRepPath().substring(model.getRepPath().lastIndexOf("/")+1);
            fileName = fileName.substring(0, fileName.lastIndexOf("."));
            model.setRepFilename(fileName);
        }
    }

	public int insertSingle(EtlKettleRepositoryModel model) throws CoBusinessException {
	    if(StringUtils.isBlank(model.getRepType())
                || StringUtils.isBlank(model.getRepPath())) {
            throw new CoBusinessException(ExceptionCode.PARAM_MISSING);
        }

        if(!ConstantKettle.REP_TYPE_DB.equalsIgnoreCase(model.getRepType()) && !ConstantKettle.REP_TYPE_FILE.equalsIgnoreCase(model.getRepType()) ) { //db存储
            throw new CoBusinessException("作业存储类型非法!");
        }

        //设置作业类型
        set(model);

	    if( !model.getKtlJobType().equalsIgnoreCase("KTR") && !model.getKtlJobType().equalsIgnoreCase("KJB") ) {
            throw new CoBusinessException("kettle作业类型非法");
        }

        if(model.getUuid()==null) {
            model.setUuid(UuidUtils.get32UUID());
        }
        model.setModifyTime(TimeUtils.getCurrentFullTime());
        int maxId = mDao.insertAutoKey(model);
        return maxId;
 	}


    /**
     * 查询作业使用情况
     */
    public List<EtlKettleRepositoryVO> queryJobUsed(EtlKettleRepositoryVO vo) throws CoBusinessException {
        return mDao.queryJobUsed(vo);
    }


    /**
     * 查询kettle任务及参数信息
     * @throws CoBusinessException
     */
    public List<com.khsh.etl.vo.bus.KettleJobParamVO> queryKettleJobParam(com.khsh.etl.vo.bus.KettleJobParamVO vo) throws CoBusinessException {
        return mDao.queryKettleJobParam(vo);
    }

    /**
     * 查询目录树
     * @return
     */
    public KettleTreeVO queryDirectoryTree() throws CoBusinessException {
        KettleTreeVO result = null;
        Connection connection = null;
        KettleDirectoryModel rootNode = new KettleDirectoryModel();
        rootNode.setDirectoryId(0); // 根节点
        rootNode.setName("/");
        try {
            connection = getKettleConnection();
            List<KettleDirectoryModel> dirs = new ArrayList<>();
            QueryRunner qr = new QueryRunner();
            //查询
            dirs = qr.query(connection, getDirectorySql(), new BeanListHandler<KettleDirectoryModel>(KettleDirectoryModel.class));
            if(dirs!=null) {
                //result = CoTreeUtil.getTree(dirs, rootNode, "directoryId", "getDirectoryPid", new String[]{"url", "ext"});
                result = getTree(dirs, rootNode);
            }
        } catch (SQLException e) {
                log.error("加载kettle资源库失败", e);
                throw new CoBusinessException(ExceptionCode.SYS_ERROR, "加载kettle资源库失败");
        }finally {
            IOUtils.closeQuietly(connection);
        }
        return result;
    }


    /**
     * 查询目录下作业
     * @throws CoBusinessException
     */
    public KettleJobVO queryJobFullpath(KettleJobVO vo) throws CoBusinessException {
        Integer repId = vo.getId();
        Integer repIdDirectory = vo.getDirectoryId();
        KettleJobVO result = queryPath(vo);
        if(result.getFullPath()==null) {
            result.setFullPath("/" + result.getKtlJobName() + "." + result.getKtlJobType());
        } else {
            result.setFullPath("/" + result.getFullPath() + "/" + result.getKtlJobName() + "." + result.getKtlJobType());
        }
        result.setDirectoryId(repIdDirectory);
        result.setId(repId);
        return result;
    }

    /**
     * 此方法后期可优化为数据库递归函数或存储过程
     */
    public KettleJobVO queryPath(KettleJobVO vo) throws CoBusinessException {
        Connection connection = null;
        StringBuffer sqlBuff = new StringBuffer();
        sqlBuff.append("SELECT ID_DIRECTORY as directoryId, ID_DIRECTORY_PARENT as directoryPid, DIRECTORY_NAME as name FROM r_directory ");
        if(vo.getDirectoryId()!=null) {
            sqlBuff.append(" WHERE ID_DIRECTORY=").append(vo.getDirectoryId());
        }
        // sqlBuff.append(" UNION ALL ");
        // sqlBuff.append("SELECT ID_DIRECTORY as directoryId, ID_DIRECTORY_PARENT as directoryPid, DIRECTORY_NAME as name FROM r_directory ");
        // if(vo.getDirectoryPid()!=null) {
        //     sqlBuff.append(" WHERE ID_DIRECTORY=").append(vo.getDirectoryPid());
        // }
        try {
            connection = getKettleConnection();
            QueryRunner qr = new QueryRunner();
            KettleJobVO bean = qr.query(connection, sqlBuff.toString(), new BeanHandler<KettleJobVO>(KettleJobVO.class));
            if(bean!=null) {
                String name = bean.getName();
                String path = vo.getFullPath()==null ? name : name + "/" + vo.getFullPath();
                vo.setFullPath(path);
                vo.setDirectoryId(bean.getDirectoryPid());
                if(bean.getDirectoryPid()==0) { //根节点
                    return vo;
                }
                queryPath(vo);
            }
        } catch (SQLException e) {
            log.error("加载kettle资源库失败", e);
            throw new CoBusinessException(ExceptionCode.SYS_ERROR, "查询作业文件失败");
        }finally {
            IOUtils.closeQuietly(connection);
        }
        return vo;
    }

    /**
     * 查询目录下作业
     * @throws CoBusinessException
     */
    public PageBean<KettleJobVO> queryDirectoryJob(KettleJobVO vo, Integer pageNum, Integer pageSize) throws CoBusinessException {
        Connection connection = null;
        StringBuffer sqlBuff = new StringBuffer();
        sqlBuff.append("SELECT ID_JOB as id, ID_DIRECTORY as directoryId, NAME as ktlJobName, 'KJB' as ktlJobType FROM r_job ");
        if(vo.getDirectoryId()!=null) {
            sqlBuff.append(" WHERE ID_DIRECTORY=").append(vo.getDirectoryId());
        }
        sqlBuff.append(" UNION ALL ")
                .append(" SELECT ID_TRANSFORMATION as id, ID_DIRECTORY as directoryId, NAME as ktlJobName, 'KTR' as ktlJobType FROM r_transformation ");
        if(vo.getDirectoryId()!=null) {
            sqlBuff.append(" WHERE ID_DIRECTORY=").append(vo.getDirectoryId());
        }
        PageBean<KettleJobVO> page = null;
        try {
            connection = getKettleConnection();
            QueryRunner qr = new QueryRunner();
            List<KettleJobVO> list = qr.query(connection, sqlBuff.toString(), new BeanListHandler<KettleJobVO>(KettleJobVO.class));
            page = new PageBean<KettleJobVO>(list);
        } catch (SQLException e) {
            log.error("加载kettle资源库失败", e);
            throw new CoBusinessException(ExceptionCode.SYS_ERROR, "查询作业文件失败");
        }finally {
            IOUtils.closeQuietly(connection);
        }
        return page;
    }



    public Connection getKettleConnection() throws CoBusinessException {
        Connection connection = null;
        try {
            KettleRepositoryConfig config = loadConfig();
            String driverClass = DbUtils.getDriverClass(config.getDbType());
            String url = DbUtils.getDbUrl(config.getDbType(), config.getDbHost(), config.getDbPort(), config.getDbDatabase());
            connection = CoDbFactory.getConnection(driverClass, url, config.getDbUsername(), config.getDbPassword());
        } catch (Exception e) {
            log.error("加载kettle资源库失败", e);
            throw new CoBusinessException(ExceptionCode.SYS_ERROR, "加载kettle资源库失败");
        }
        return connection;
    }

    public KettleRepositoryConfig loadConfig() throws CoBusinessException {
        InputStream inputStream = null;
        KettleRepositoryConfig config = new KettleRepositoryConfig();
        try {
            log.info("======   init kettle loadConfig ======");
            //目前只支持1个库，后期再扩展
            EtlKettleDatabaseModel query = new EtlKettleDatabaseModel();
            query.setId(1);
            query.setStatus(CoConstant.STATUS_NORMAL);
            EtlKettleDatabaseModel database = kettleDatabaseService.findByPK(query);
            if(database!=null) {
                BeanUtils.copyProperties(database, config);
            } else {
                log.info("======   load kettle resource properties ======");
                ClassPathResource resource = new ClassPathResource("kettle_repository.properties");
                inputStream = resource.getInputStream();
                config = (KettleRepositoryConfig) PropertyUtils.loadProperty(inputStream, KettleRepositoryConfig.class);
            }
        } catch (CoBusinessException | InstantiationException | IllegalAccessException | IOException e) {
            log.error("加载kettle资源库失败", e);
            throw new CoBusinessException(ExceptionCode.SYS_ERROR, "加载kettle资源库失败");
        }finally {
            IOUtils.closeQuietly(inputStream);
        }
        return config;
    }

    /**
     * 获取查询sql
     * @return
     */
    public String getDirectorySql() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(" SELECT ID_DIRECTORY as directoryId, ID_DIRECTORY_PARENT as getDirectoryPid, DIRECTORY_NAME as name FROM r_directory order by ID_DIRECTORY asc, ID_DIRECTORY_PARENT asc ");
        return buffer.toString();
    }



    /**
     * 转换成树节点信息
     * @return
     */
    public static KettleTreeVO getTree(List<KettleDirectoryModel> all, KettleDirectoryModel root) {
        KettleTreeVO rootNode = new KettleTreeVO();
        rootNode.setName(root.getName());
        rootNode.setDirectoryId(root.getDirectoryId());
        rootNode.setDirectoryPid(root.getGetDirectoryPid());

        boolean allAppend = false;
        for(KettleDirectoryModel item : all) {
            KettleTreeVO current = new KettleTreeVO();
            current.setName(item.getName());
            current.setDirectoryId(item.getDirectoryId());
            current.setDirectoryPid(item.getGetDirectoryPid());
            boolean temp = appendChild(rootNode, current);
            allAppend = temp && allAppend;
        }
        return rootNode;
    }

    /**
     * 递归增加子节点数据
     *
     * @param treeNode
     * @param currentNode
     * @return
     */
    public static boolean appendChild(KettleTreeVO treeNode, KettleTreeVO currentNode) {
        boolean appended = false;
        if( treeNode.getDirectoryId().intValue() == currentNode.getDirectoryPid().intValue()) { //
            treeNode.getChildren().add(currentNode);
            return true;
        } else if( treeNode.getChildren()!=null ) {
            for(KettleTreeVO item : treeNode.getChildren()) {
                if( appendChild(item, currentNode) ) {
                    appended = true;
                    break;
                }
            }
        }
        return appended;
    }


    /**
     * 批量同步资源库信息
     */
    public String addJobsBatch(List<EtlKettleRepositoryVO> list) throws CoBusinessException {
        if(list==null || list.size()==0) {
            throw new CoBusinessException(ExceptionCode.PARAM_MISSING);
        }
        StringBuffer buffer = new StringBuffer();
        Map<String, KettleJobVO> map = new HashMap<>();

        int count = 0;
        int failed = 0;
        for(EtlKettleRepositoryVO item : list) {
            if(item.getRepId()==null || item.getRepIdDirectory()==null || item.getKtlJobType()==null || item.getKtlJobName()==null) {
                buffer.append("同步信息参数不完整{ id：").append(item.getRepId()).append(", direcotryId:").append(item.getRepIdDirectory())
                        .append(", 类型：").append(item.getKtlJobType()).append(", 名字:").append(item.getKtlJobName()).append(" }");
                ++failed;
                continue;
            }
            String key  = item.getRepId() + "_" + item.getRepIdDirectory() + "_" + item.getKtlJobType();
            KettleJobVO query = new KettleJobVO();
            query.setId(item.getRepId().intValue());
            query.setDirectoryId(item.getRepIdDirectory());
            query.setKtlJobType(item.getKtlJobType());
            query.setKtlJobName(item.getKtlJobName());
            KettleJobVO jobInfo = null;
            if(map.containsKey(key)) {
                jobInfo = map.get(key);
            } else {
                //查询任务具体信息
                jobInfo = queryJobFullpath(query);
            }
            if(jobInfo==null) {
                //查询路径信息不存在
                buffer.append("查询路径信息不存在{ KEY：").append(key).append(" }");
                ++failed;
                continue;
            }
            map.put(key, jobInfo);
            try {
                EtlKettleRepositoryModel addModel = new EtlKettleRepositoryModel();
                //{"ktlJobName":"转换 233","ktlJobType":"KTR","repType":"DB","repPath":"/转换 2.KTR","ktlParamValue":"",
                // "repId":3,"repIdDirectory":0,"status":"1","remark":""}
                addModel.setKtlJobName(item.getKtlJobName());
                addModel.setRepId(jobInfo.getId().longValue());
                addModel.setRepIdDirectory(jobInfo.getDirectoryId());
                addModel.setRepType(ConstantKettle.REP_TYPE_DB);
                addModel.setRepPath(jobInfo.getFullPath());
                addModel.setKtlJobType(jobInfo.getKtlJobType());
                addModel.setStatus(CoConstant.STATUS_NORMAL);
                insertSingle(addModel);
                ++count;
            } catch (Exception e) {
                ++failed;
            }
        }
        buffer.append("同步成功:").append(count).append(", 同步失败:").append(failed);
        return buffer.toString();
    }



}
