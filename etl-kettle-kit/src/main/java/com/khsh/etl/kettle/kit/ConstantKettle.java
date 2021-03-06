package com.khsh.etl.kettle.kit;

/**
 * Copyright (C), 2016-2018, 武汉康华数海有限公司
 * FileName: ConstantKettle
 * Author:   Ejet
 * CreateDate:     2018-09-08 11:59
 * Description: 常量类
 * History:
 * Version: 1.0
 */
public class ConstantKettle {
    /**
     * kettle job作业类型
     */
    public static final String KETTLE_TYPE_KJB = "KJB";
    /**
     * kettle转换类型
     */
    public static final String KETTLE_TYPE_KTR = "KTR";
    /**
     * kettle类型任务
     */
    public static final String KETTLE = "KETTLE";

    /**
     * kettle资源类型 DB/FILE
     */
    public static final String REP_TYPE_DB = "DB";
    /**
     * kettle资源类型 FILE, 文件方式
     */
    public static final String REP_TYPE_FILE = "FILE";


    //1、正常 2：终止 3：未完成 4：异常
    public static final Integer JOB_RESULT_OK = 1;
    public static final Integer JOB_RESULT_STOP = 2;
    public static final Integer JOB_RESULT_UNFINISHED = 3;
    public static final Integer JOB_RESULT_ERROR = 3;





}
