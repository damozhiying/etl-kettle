package com.khsh.etl.comm;

import com.ejet.comm.exception.CoBusinessException;
import com.ejet.configurer.IApplicationBootCallback;
import com.ejet.context.CoApplicationContext;
import com.ejet.quartz.comm.CoJobManager;
import com.ejet.quartz.service.impl.SysJobScheduleServiceImpl;
import com.khsh.etl.kettle.kit.ConstantKettle;
import com.khsh.etl.kettle.kit.KettleFactory;
import com.khsh.etl.kettle.kit.KettleRepositoryConfig;
import com.khsh.etl.kettle.kit.KettleRepositoryLoader;
import com.khsh.etl.model.EtlKettleRepositoryModel;
import com.khsh.etl.service.impl.EtlKettleDatabaseServiceImpl;
import com.khsh.etl.service.impl.EtlKettleRepositoryServiceImpl;
import org.pentaho.di.core.exception.KettleException;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Copyright (C), 2016-2018, 武汉康华数海有限公司
 * FileName: EtlKettleCallbackImpl
 * Author:   Ejet
 * CreateDate:     2018-09-10 10:28
 * Description: 应用启动后回调实现类
 * History:
 * Version: 1.0
 */
public class EtlKettleCallbackImpl implements IApplicationBootCallback {

    private final Logger log = LoggerFactory.getLogger(EtlKettleCallbackImpl.class);
    @Autowired
    EtlKettleDatabaseServiceImpl kettleDatabaseService;
    @Autowired
    EtlKettleRepositoryServiceImpl kettleRepositoryService;
    @Autowired
    SysJobScheduleServiceImpl jobScheduleService;

    @Override
    public void callApplicationReadyEvent() {
        log.info("======★★★★★[etl-kettle]callback ... ApplicationReadyEvent★★★★★======");
        try {
            if(jobScheduleService==null) {
                jobScheduleService = CoApplicationContext.getBean(SysJobScheduleServiceImpl.class);
            }
            if(kettleDatabaseService ==null) {
                kettleDatabaseService = CoApplicationContext.getBean(EtlKettleDatabaseServiceImpl.class);
            }
            if(kettleRepositoryService ==null) {
                kettleRepositoryService = CoApplicationContext.getBean(EtlKettleRepositoryServiceImpl.class);
            }

            new Thread(new Runnable() {
                @Override
                public void run() {
                    initKettleRepository();
                }
            }).start();

            //
            addQuartzListener();
            //
            //startQuartzJobs();

        } catch (Exception e) {
            log.error("======   ★启动应用完成callApplicationReadyEvent★   ======", e);
        }
    }

    /**
     * 添加监听器， 调度器监听器和任务监听器
     */
    private void addQuartzListener() {
        //JobSchedulerManager.getInstance().setScheduler(schedulerFactoryBean.getScheduler());
        try {
            //设置监听器实现, 如果需要？？？
            CoJobManager.getInstance().addSchedulerListener(new SchedulerListenerImpl());

        } catch (SchedulerException e) {
            log.error("初始化quartz错误", e);
        }

    }


    /**
     * 预加载资源
     */
    public void initKettleRepository() {
        try {
            log.info("======   init kettle callback ======");
            KettleFactory.initEnv();

            KettleRepositoryConfig config = kettleRepositoryService.loadConfig();

            KettleFactory.connectRepository(config);

            loadRepository();

        } catch (KettleException | CoBusinessException e) {
            log.error("加载kettle资源失败", e);
        }
    }

    public void loadRepository() throws CoBusinessException {

        List<EtlKettleRepositoryModel> list = kettleRepositoryService.queryByCond(null);

        if(list!=null) {
            log.info("======   加载kettle资源,个数{}   ======", list.size());
            for (EtlKettleRepositoryModel item : list) {
                try {
                    if(ConstantKettle.REP_TYPE_DB.equals(item.getRepType().toUpperCase())) { //文件方式不需要加载

                        KettleRepositoryLoader.loadRepository(item.getDatabaseUuid(), item.getRepType(), item.getKtlJobType(), item.getRepDirectory(), item.getRepFilename());
                    }
                } catch (KettleException e) {
                    log.error("加载kettle资源失败??? uuid: {}, kettleType:{}, 路径:{}",  item.getUuid(), item.getKtlJobType(), item.getRepPath() , e);
                }
            }
        }

    }


    // /**
    //  * 启动任务
    //  */
    // public void startQuartzJobs() {
    //     try {
    //         log.info("======   加载任务   ======");
    //         //JobSchedulerManager.getInstance().setScheduler(schedulerFactoryBean.getScheduler());
    //         List<SysJobScheduleVO> list = jobScheduleService.queryByCond(null);
    //         if (list==null || list.size()==0) {
    //             return ;
    //         }
    //         log.info("加载定时任务信息, 任务总数：{}", list.size());
    //         for (SysJobScheduleVO model : list) {
    //             if (model.getStatus() != null && model.getStatus()== CoConstant.STATUS_NORMAL) { //1:正常运行
    //                 log.info("加载定时任务：{}, 任务类:{}, 表达式:{}",  model.getJobName(), model.getJobClazz(), model.getJobCron());
    //                 CoJobManager.getInstance().executeJob(model);
    //                 //更新状态和运行时间
    //                 jobScheduleService.updateState(model);
    //             }
    //         }
    //
    //     } catch (CoBusinessException | SchedulerException e) {
    //         log.error("加载任务错误", e);
    //     }
    // }


    @Override
    public void callContextStoppedEvent() {

    }

    @Override
    public void callContextClosedEvent() {

    }

    @Override
    public void callContextRefreshedEvent() {

    }

}
