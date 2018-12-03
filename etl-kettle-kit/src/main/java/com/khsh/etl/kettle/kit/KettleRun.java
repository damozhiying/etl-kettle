package com.khsh.etl.kettle.kit;

import com.ejet.comm.utils.time.DateUtils;
import org.pentaho.di.base.AbstractMeta;

/**
 * Copyright (C), 2016-2018, 武汉康华数海有限公司
 * FileName: KettleRun
 * Author:   ShenYijie
 * CreateDate:     2018-11-14 10:09
 * Description: /**
 * History:
 * Version: 1.0
 */
public class KettleRun {

    protected static final ThreadLocal<KettleMessage> LOCAL_INFO = new ThreadLocal<>();

    /**
     * 设置 KettleMessage 参数
     *
     * @param info
     */
    protected static void setLocalInfo(KettleMessage info) {
        LOCAL_INFO.set(info);
    }

    /**
     * 获取 KettleMessage 参数
     *
     * @return
     */
    public static KettleMessage getLocalInfo() {
        return LOCAL_INFO.get();
    }

    /**
     * 移除本地变量
     */
    public static void clearInfo() {
        LOCAL_INFO.remove();
    }


    public static KettleMessage startRun(AbstractMeta job) {
        KettleMessage message = getLocalInfo();
        if(message==null) {
            message = new KettleMessage();
        }
        message.setKtlJobName(job.getName());
        message.setLogChannelId(job.getLogChannelId());
        message.setCreateDate(DateUtils.format(job.getCreatedDate(), "yyyy-MM-dd HH:mm:sss"));
        message.setModifiedDate(DateUtils.format(job.getModifiedDate(), "yyyy-MM-dd HH:mm:sss"));
        message.setFilename(job.getFilename());
        message.setFileType(job.getFileType());
        message.setMaxUndo(job.getMaxUndo());
        setLocalInfo(message);
        return message;
    }

}
