package com.khsh.etl;

import com.ejet.CommWebApplication;
import com.ejet.bss.userinfo.UserInfoApplication;
import com.ejet.comm.redis.CommWebRedisApplication;
import com.ejet.context.CoApplicationContext;
import com.ejet.monitor.CommWebMonitorApplication;
import com.ejet.quartz.CommWebQuartzApplication;
import com.ejet.uploadfile.CommUploadfileApplication;
import com.khsh.etl.comm.EtlKettleCallbackImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.ArrayList;
import java.util.List;

@EnableTransactionManagement // 启注解事务管理，等同于xml配置方式的 <tx:annotation-driven />
@SpringBootApplication()
@EnableCaching
public class EtlKettleApplication extends SpringBootServletInitializer {
    private static final Logger logger = LoggerFactory.getLogger(EtlKettleApplication.class);


    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(EtlKettleApplication.class);
    }


    static {
        //每一个模块，都可以有自己的启动回调实现接口，只需要将实现类添加进去即可
        EtlKettleCallbackImpl callbackImpl = new EtlKettleCallbackImpl();
        //设置启动回调接口
        CoApplicationContext.getInstance().addApplicationBootCallback(callbackImpl);
    }

    public static void main(String[] args) {


        List<Class> list  = new ArrayList<>();
        list.add(EtlKettleApplication.class);       //本项目
        list.add(CommWebApplication.class);         //基础项目
        list.add(CommWebQuartzApplication.class);   //调度项目
        list.add(CommWebRedisApplication.class);    //redis项目
        list.add(UserInfoApplication.class);        //userinfo项目
        list.add(CommUploadfileApplication.class);  //上传文件
        list.add(CommWebMonitorApplication.class);  //监控

        SpringApplication.run(list.toArray(new Class[list.size()]), args);

        logger.info("======== ★ EtlKettle Application start ★ ======");
    }




}
