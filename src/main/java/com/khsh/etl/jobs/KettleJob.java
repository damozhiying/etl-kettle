package com.khsh.etl.jobs;

import com.alibaba.fastjson.JSONObject;
import com.ejet.comm.exception.CoBusinessException;
import com.ejet.comm.exception.ExceptionCode;
import com.ejet.comm.utils.StringUtils;
import com.ejet.comm.utils.UuidUtils;
import com.ejet.comm.utils.io.IOUtils;
import com.ejet.comm.utils.time.TimeUtils;
import com.ejet.context.CoApplicationContext;
import com.ejet.quartz.comm.CoJobBase;
import com.khsh.etl.kettle.kit.ConstantKettle;
import com.khsh.etl.kettle.kit.KettleFactory;
import com.khsh.etl.kettle.kit.KettleHelper;
import com.khsh.etl.kettle.kit.KettleMessage;
import com.khsh.etl.model.KettleMessageLogModel;
import com.khsh.etl.service.impl.EtlKettleRepositoryServiceImpl;
import com.khsh.etl.service.impl.KettleMessageLogServiceImpl;
import com.khsh.etl.service.impl.SysJobLogServiceImpl;
import com.khsh.etl.vo.SysJobLogVO;
import com.khsh.etl.vo.bus.KettleJobParamVO;
import org.pentaho.di.core.exception.KettleException;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright (C), 2016-2018, 武汉康华数海有限公司
 * FileName: KettleJob
 * Author:   Ejet
 * CreateDate:     2018/9/30 15:27
 * Description: 基于kettle抽取数据任务
 * History:
 * Version: 1.0
 */
public class KettleJob extends CoJobBase {
    private final Logger LOGGER = LoggerFactory.getLogger(KettleJob.class);
    @Autowired
    EtlKettleRepositoryServiceImpl kettleRepositoryService;
    @Autowired
    SysJobLogServiceImpl jobLogService;
    @Autowired
    KettleMessageLogServiceImpl messageService;
    /**
     * 任务类型
     */
    private final Integer PRE_JOB = 0;
    private final Integer POST_JOB = 1;
    private final Integer AFTER_JOB = 2;

    //日志类型, 1：业务日志，2：系统日志 3：异常日志
    private final Integer LOG_TYPE_BUSS = 1;
    private final Integer LOG_TYPE_SYS = 2;
    private final Integer LOG_TYPE_ERR = 3;

    //日志级别, 1：debug，2：info 3：warn 4：error
    private final Integer LOG_LEVEL_DEBUG = 1;
    private final Integer LOG_LEVEL_INFO = 2;
    private final Integer LOG_LEVEL_WARN = 3;
    private final Integer LOG_LEVEL_ERROR = 4;

    //运行结果：1、正常 2：终止 3：未完成 4：异常
    private final Integer JOB_RESULT_NORMAL = 1;
    private final Integer JOB_RESULT_STOP = 2;
    private final Integer JOB_RESULT_UNDO = 3;
    private final Integer JOB_RESULT_ERR = 4;


    private List<KettleJobParamVO> pre = new ArrayList<>();
    private List<KettleJobParamVO> post = new ArrayList<>();
    private List<KettleJobParamVO> after = new ArrayList<>();

    private boolean hasGlobalError = false;

    /**
     * @param param
     * @return
     * @throws KettleException
     */
    private boolean isKettleJob(KettleJobParamVO param) throws KettleException {
        boolean ret = false;
        if(param==null) {
            throw new KettleException("参数类型为非Kettle任务类型!");
        }
        if(param!=null && param.getParamType()!=null && ConstantKettle.KETTLE.equals(param.getParamType().toUpperCase())) {
            return true;
        }
        return ret;
    }

    @Override
    public void run(JobExecutionContext context) {
        try {
            LOGGER.info("★★★★★Kjb作业任务:{}, {}" , jobModel.getJobName(), jobModel.getParamValue());
            if(kettleRepositoryService==null) {
                kettleRepositoryService = CoApplicationContext.getBean(EtlKettleRepositoryServiceImpl.class);
            }
            if(jobLogService==null) {
                jobLogService = CoApplicationContext.getBean(SysJobLogServiceImpl.class);
            }
            if(messageService ==null) {
                messageService = CoApplicationContext.getBean(KettleMessageLogServiceImpl.class);
            }
            //查询任务对应参数
            KettleJobParamVO query = new KettleJobParamVO();
            query.setJobId(jobModel.getJobId());

            List<KettleJobParamVO> list = kettleRepositoryService.queryKettleJobParam(query);
            if (list==null)     return;

            for(KettleJobParamVO param: list) {
                LOGGER.info("任务：{} （{}）， 任务类型：{}, 作业名称:{}({})", param.getJobName(), param.getJobId(), param.getBeforeAfter(), param.getKtlJobName(), param.getKtlJobType());
                if(!isKettleJob(param)) {
                    continue;
                }
                if(param.getBeforeAfter()==PRE_JOB) {
                    pre.add(param);
                } else if(param.getBeforeAfter()==AFTER_JOB) {
                    after.add(param);
                } else {
                    post.add(param);
                }

                hasGlobalError = false;
                preHandle(pre);
                postHandle(post);
            }
        } catch (KettleException | CoBusinessException e) {
            LOGGER.error("Kjb任务异常", e);
            JobExecutionException je = new JobExecutionException(e);
            // 不再执行，所有该job的调度全部停止
            je.setUnscheduleAllTriggers(true);
            //再次尝试执行
            //je.refireImmediately();
        } finally {
            if(hasGlobalError) {
                afterException(after);
            }
            //jobLog(logVO);
        }
    }

    public void setMessage(KettleJobParamVO param, KettleMessage message, String logUUidR) {
        if(param==null || message==null)
            return;

        message.setJobId(param.getJobId());
        message.setJobName(param.getJobName());
        message.setKtlJobUuid(param.getUuid()); //kettle资源uuid
        message.setKtlJobName(param.getKtlJobName());
        message.setLogUuidR(logUUidR);
    }


    public void setError(KettleJobParamVO param, SysJobLogVO logVO, KettleMessage message, Exception e) {
        if(param==null)
            return;
        if(logVO==null) {
            logVO = new SysJobLogVO();
            startJobLog(param, logVO);
        }
        message.setErrors(1);
        message.setLogText(IOUtils.getError(e));
        setMessage(param, message, logVO.getLogUuidR());

        logVO.setJobResult(JOB_RESULT_ERR);
        logVO.setLogLevel(LOG_LEVEL_ERROR);
        logVO.setLogSubject("作业异常");
        logVO.setLogDetail(IOUtils.getError(e));
    }

    public void startJobLog(KettleJobParamVO param, SysJobLogVO logVO) {
        if(param==null)
            return;
        logVO.setLogType(LOG_TYPE_BUSS);
        logVO.setJobResult(JOB_RESULT_UNDO);
        logVO.setLogLevel(LOG_LEVEL_INFO);
        logVO.setJobId(param.getJobId());
        logVO.setJobName(param.getJobName());
        logVO.setStarttime(System.currentTimeMillis());
        logVO.setJobStarttime(TimeUtils.getTime(logVO.getStarttime(), "yyyy-MM-dd HH:mm:sss"));
        logVO.setLogUuidR(UuidUtils.getUUID());
        logVO.setBussName(param.getKtlJobName());//kettle资源名称
        logVO.setBussType(param.getKtlJobType());//kettle资源类型
        logVO.setBussUuid(param.getUuid());//kettle资源uuid
        try {
            //记录日志
            jobLogService.insertAutoKey(logVO);
        } catch (CoBusinessException e) {
            LOGGER.error("开始记录任务日志异常", e);
        } finally {
            //recodeLog(jobLog, message);
        }

    }

    /**
     *  前置任务
     */
    public void preHandle(List<KettleJobParamVO> jobs) throws CoBusinessException {
        for (KettleJobParamVO param : jobs) {
            deal(param);
        }
    }

    /**
     * 任务
     */
    public void postHandle(List<KettleJobParamVO> jobs) throws CoBusinessException {
        for (KettleJobParamVO param : jobs) {
            deal(param);
        }
    }

    /**
     * 后置错误处理任务
     */
    public void afterException(List<KettleJobParamVO> jobs) {
        boolean hasFinalError = false;
        try {
            if(!hasGlobalError) {
                return;
            }
            for (KettleJobParamVO param : jobs) {
                deal(param);
            }
        } catch (CoBusinessException e) {
            LOGGER.error("kettle后置处理任务", e);
            JobExecutionException je = new JobExecutionException(e);
            // 不再执行，所有该job的调度全部停止
            je.setUnscheduleAllTriggers(true);
            //再次尝试执行
            //je.refireImmediately();
        } finally {
            //recodeLog(jobLog, message);
        }
    }


    public void deal(KettleJobParamVO param) throws CoBusinessException {
        KettleMessage message = null;
        SysJobLogVO jobLog = new SysJobLogVO();
        try {
            startJobLog(param, jobLog);
            Map<String, String> jobParams = new HashMap<String, String>();
            if (!StringUtils.isBlank(param.getKtlParamValue())) {
                jobParams = JSONObject.parseObject(param.getKtlParamValue(), Map.class);
            }
            LOGGER.info("★ 运行前置任务：{} （{}）， 任务类型：{}, 作业名称:{}({})", param.getJobName(), param.getJobId(), param.getBeforeAfter(), param.getKtlJobName(), param.getKtlJobType());
            message = KettleHelper.run(KettleFactory.getRepository(), jobParams, param.getDatabaseUuid(), param.getRepType(), param.getKtlJobType(), param.getRepDirectory(), param.getRepFilename());
            setMessage(param, message, jobLog.getLogUuidR());
        } catch (KettleException e) {
            hasGlobalError = true;
            LOGGER.error("kettle任务异常" + param.getJobName() + "(" + param.getBeforeAfter() + ")", e);
            if(message==null) {
                message = new KettleMessage();
            }
            setError(param, jobLog, message, e);
            throw new CoBusinessException(ExceptionCode.SYS_ERROR, e);
        } finally {
            recodeLog(jobLog, message);
        }
    }


    @Override
    public void stop() {



    }

    /**
     * 记录日志
     */
    public void recodeLog(SysJobLogVO log, KettleMessage message) {
        try {
            long end = System.currentTimeMillis();
            String time = TimeUtils.getTime(end, "yyyy-MM-dd HH:mm:sss");
            log.setEndtime(log.getEndtime()==null ? end : log.getEndtime());
            log.setJobEndtime(TimeUtils.getTime(log.getEndtime(), "yyyy-MM-dd HH:mm:sss"));
            log.setJobCosttime((int) ((log.getEndtime()-log.getStarttime())/1000));

            //如果直接报错
            boolean haserror = message.getErrors()!=null && message.getErrors()>0;
            log.setJobResult( haserror ? JOB_RESULT_ERR : JOB_RESULT_NORMAL);
            log.setLogLevel(log.getJobResult()==JOB_RESULT_ERR ? LOG_LEVEL_ERROR : log.getLogLevel());
            log.setModifyTime(time);

            jobLogService.update(log);

            KettleMessageLogModel model = new KettleMessageLogModel();
            BeanUtils.copyProperties(message, model);
            model.setUuid(message.getLogUuidR());
            model.setModifyTime(time);
            model.setCreateDay(time.substring(0, 10)); //取日期
            messageService.insertAutoKey(model);

        } catch (Exception e) {
            LOGGER.error("记录日志错误", e);
        }
    }


}
