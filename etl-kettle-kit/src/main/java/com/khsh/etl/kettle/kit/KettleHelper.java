package com.khsh.etl.kettle.kit;

import com.ejet.comm.utils.collect.BeanUtils;
import com.ejet.comm.utils.time.DateUtils;
import org.pentaho.di.base.AbstractMeta;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.logging.KettleLogLayout;
import org.pentaho.di.core.logging.KettleLogStore;
import org.pentaho.di.core.logging.KettleLoggingEvent;
import org.pentaho.di.core.logging.LoggingRegistry;
import org.pentaho.di.job.Job;
import org.pentaho.di.job.JobExecutionConfiguration;
import org.pentaho.di.job.JobMeta;
import org.pentaho.di.repository.Repository;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransExecutionConfiguration;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.cluster.TransSplitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * Copyright (C), 2016-2018, 武汉康华数海有限公司
 * FileName: KettleRunner
 * Author:   Ejet
 * CreateDate:     2018-08-30 13:59
 * Description: kettle运行工具类
 * History:
 * Version: 1.0
 */
public class KettleHelper extends KettleRun {

    private static Logger log = LoggerFactory.getLogger("KettleRunner");
    /**
     *  执行job
     */
    public static KettleMessage run(Repository repository, Map<String, String> initKettleParam, String dbuuid, String storageType, String ktlJobType, String repositoryDir, String fileName) throws KettleException {
        AbstractMeta meta = KettleRepositoryLoader.loadRepository(dbuuid, storageType, ktlJobType, repositoryDir, fileName);
        if (meta == null) {
            throw new KettleException("查找资源失败!!!");
        }
        KettleMessage message = startRun(meta);
        try {
            if (meta instanceof JobMeta) {
                message = runJob(repository, initKettleParam, (JobMeta)meta);
            } else if (meta instanceof TransMeta) {
                message = runTrans(initKettleParam, (TransMeta)meta);
            }
        } finally {
            clearInfo();
        }
        return message;
    }

    /**
     *  执行job
     * @return
     */
    public static KettleMessage runJob(Repository repository, Map<String,String> initKettleParam, JobMeta jobMeta) throws KettleException {
        KettleMessage message = getLocalInfo();
        Job job = null;
        try {
            job = new Job(repository, jobMeta);
            if(message==null || job==null) {
                throw new KettleException("message资源、job作业资源初始化失败!!!");
            }
            //初始化job参数，脚本中获取参数值：${variableName}
            if(initKettleParam!=null) {
                for (String variableName : initKettleParam.keySet()) {
                    job.setVariable(variableName, initKettleParam.get(variableName));
                }
            }
            job.start();
            job.waitUntilFinished();//等待完成

            message.setBatchId(job.getBatchId());
            message.setErrors(job.getErrors());
            message.setDepDate(DateUtils.format(job.getDepDate(), "yyyy-MM-dd HH:mm:sss"));
            message.setEndDate(DateUtils.format(job.getEndDate(), "yyyy-MM-dd HH:mm:sss"));
            message.setStartDate(DateUtils.format(job.getStartDate(), "yyyy-MM-dd HH:mm:sss"));
            message.setCurrentDate(DateUtils.format(job.getCurrentDate(), "yyyy-MM-dd HH:mm:sss"));
            message.setExecutingServer(job.getExecutingServer());
            message.setContainerObjectId(job.getContainerObjectId());

            //job.getJobTracker().getJobEntryResult().getResult();

            StringBuffer sb = new StringBuffer();
            KettleLogLayout logLayout = new KettleLogLayout( true );
            List<String> childIds = LoggingRegistry.getInstance().getLogChannelChildren( job.getLogChannelId() );
            List<KettleLoggingEvent> logLines = KettleLogStore.getLogBufferFromTo( childIds, true, -1, KettleLogStore.getLastBufferLineNr() );
            for ( int i = 0; i < logLines.size(); i++ ) {
                KettleLoggingEvent event = logLines.get( i );
                String line = logLayout.format( event ).trim();
                sb.append(line).append("\n");
            }
            message.setLogText(sb.toString());
            LoggingRegistry.getInstance().removeIncludingChildren(job.getLogChannelId());
            //拷贝读取的行数
            BeanUtils.copyProperties(job.getResult(), message);

        } finally {
            if(job!=null) {

                BeanUtils.copyProperties(job.getResult(), message);
            }
        }
        log.info(message.toString());
        return message;
    }


    /**
     * 执行转换
     *
     * @return
     */
    public static KettleMessage runTrans(Map<String,String> initKettleParam, TransMeta transMeta) throws KettleException {
        KettleMessage message = getLocalInfo();
        Trans trans = null;
        try {
            trans = new Trans(transMeta);
            if(message==null || trans==null) {
                throw new KettleException("message资源、trans作业资源初始化失败!!!");
            }
            //初始化trans参数，脚本中获取参数值：${variableName}
            if (initKettleParam != null) {
                for (String variableName : initKettleParam.keySet()) {
                    trans.setVariable(variableName, initKettleParam.get(variableName));
                }
            }
            //执行转换
            trans.execute(null);
            //等待转换执行结束
            trans.waitUntilFinished();

            message.setBatchId(trans.getBatchId());
            message.setErrors(trans.getErrors());
            message.setDepDate(DateUtils.format(trans.getDepDate(), "yyyy-MM-dd HH:mm:sss"));
            message.setEndDate(DateUtils.format(trans.getEndDate(), "yyyy-MM-dd HH:mm:sss"));
            message.setStartDate(DateUtils.format(trans.getStartDate(), "yyyy-MM-dd HH:mm:sss"));
            message.setCurrentDate(DateUtils.format(trans.getCurrentDate(), "yyyy-MM-dd HH:mm:sss"));
            message.setExecutingServer(trans.getExecutingServer());
            message.setContainerObjectId(trans.getContainerObjectId());

            StringBuffer sb = new StringBuffer();
            KettleLogLayout logLayout = new KettleLogLayout( true );
            List<String> childIds = LoggingRegistry.getInstance().getLogChannelChildren( trans.getLogChannelId() );
            List<KettleLoggingEvent> logLines = KettleLogStore.getLogBufferFromTo( childIds, true, -1, KettleLogStore.getLastBufferLineNr() );
            for ( int i = 0; i < logLines.size(); i++ ) {
                KettleLoggingEvent event = logLines.get( i );
                String line = logLayout.format( event ).trim();
                sb.append(line).append("\n");
            }
            message.setLogText(sb.toString());
            LoggingRegistry.getInstance().removeIncludingChildren(trans.getLogChannelId());
            //拷贝读取的行数
            BeanUtils.copyProperties(trans.getResult(), message);

        }finally {
            if (trans != null) {
                BeanUtils.copyProperties(trans.getResult(), message);
            }
        }
        log.info(message.toString());
        return message;
    }


    /**
     * 集群运行任务
     * @return
     * @throws KettleException
     */
    public static TransSplitter runTransCluster(Map<String,String> initKettleParam, TransMeta transMeta) throws KettleException {
        Trans trans = null;
        log.info(" ====== 集群runTransCluster =======");
        //转换
        trans = new Trans(transMeta);
        for (String variableName : initKettleParam.keySet()) {
            trans.setVariable(variableName, initKettleParam.get(variableName));
        }
        // 设置执行模式
        TransExecutionConfiguration config = new TransExecutionConfiguration();
        ////设置集群为true
        config.setExecutingClustered(true);
        //设置local为false
        config.setExecutingLocally(false);
        config.setExecutingRemotely(false);
        config.setClusterPosting(true);
        //设置准备执行为true
        config.setClusterPreparing(true);
        //设置开始执行为true，否则需要到carte的监控页面上点击开始执行
        config.setClusterStarting(true);
        TransSplitter transSplitter = Trans.executeClustered(transMeta, config);
        log.info(" ====== 集群runTransCluster =======" + transSplitter.getCarteObjectMap());
        return transSplitter;
    }

    /**
     * 集群运行任务
     * @return
     * @throws KettleException
     */
    public static TransSplitter runJobCluster(Repository repository, Map<String,String> initKettleParam, JobMeta jobMeta) throws KettleException {
        Trans trans = null;
        log.info(" ====== 集群runJobCluster =======");
        //转换
        Job job = new Job(repository, jobMeta);
        //初始化job参数，脚本中获取参数值：${variableName}
        if(initKettleParam!=null) {
            for (String variableName : initKettleParam.keySet()) {
                job.setVariable(variableName, initKettleParam.get(variableName));
            }
        }
        JobExecutionConfiguration config  = new JobExecutionConfiguration();
        ////设置集群为true
        // config.setExecutingClustered(true);
        // //设置local为false
        // config.setExecutingLocally(false);
        // config.setExecutingRemotely(false);
        // config.setClusterPosting(true);
        // //设置准备执行为true
        // config.setClusterPreparing(true);
        // //设置开始执行为true，否则需要到carte的监控页面上点击开始执行
        // config.setClusterStarting(true);
        // job.setExecutingServer();
        // TransSplitter transSplitter = Trans.executeClustered(transMeta, config);
        // System.out.println(transSplitter.getCarteObjectMap());
        return null;
    }



}
