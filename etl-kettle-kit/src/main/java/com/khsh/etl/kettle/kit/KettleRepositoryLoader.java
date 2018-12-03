package com.khsh.etl.kettle.kit;

import org.pentaho.di.base.AbstractMeta;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.job.JobMeta;
import org.pentaho.di.repository.Repository;
import org.pentaho.di.repository.RepositoryDirectoryInterface;
import org.pentaho.di.repository.kdr.KettleDatabaseRepository;
import org.pentaho.di.trans.TransMeta;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Copyright (C), 2016-2018, 武汉康华数海有限公司
 * FileName: KettleRepositoryLoader
 * Author:   Ejet
 * CreateDate:     2018-09-12 11:20
 * Description: 加载数据库中资源任务
 * History:
 * Version: 1.0
 */
public class KettleRepositoryLoader {

    private static Map<String, AbstractMeta> jobs = Collections.synchronizedMap(new HashMap<>());

    /**
     * 返回key，缓存key字段
     *
     * @return
     */
    public static String getKey(String storageType, String ktlJobType, String repositoryDir, String fileName)  throws KettleException {
        if(storageType==null || storageType.trim().equals("")) {
            throw new KettleException("job存储类型错误:" + storageType);
        }
        if(!ConstantKettle.REP_TYPE_DB.equalsIgnoreCase(storageType.trim()) && !ConstantKettle.REP_TYPE_FILE.equalsIgnoreCase(storageType.trim())) {
            throw new KettleException("job存储类型错误:" + storageType);
        }
        if(ktlJobType==null || ktlJobType.trim().equals("")) {
            throw new KettleException("job类型错误:" + ktlJobType);
        }
        if(repositoryDir==null || repositoryDir.trim().equals("")) {
            throw new KettleException("job存储目录错误:" + repositoryDir);
        }
        if(fileName==null || fileName.trim().equals("")) {
            throw new KettleException("job文件名错误:" + fileName);
        }
        String key = storageType + "_" + ktlJobType + "_" + repositoryDir + "_" + fileName ;
        return key;
    }

    /**
     * 从文件加载kettle作业job信息
     * @param filePath
     * @return
     * @throws KettleException
     */
    public static synchronized JobMeta loadJobFile(String filePath) throws KettleException {
        JobMeta jobMeta = null;
        jobMeta = new JobMeta(filePath, null);
        return jobMeta;
    }

    /**
     * 从文件加载kettle作业trans信息
     * @param filePath
     * @return
     * @throws KettleException
     */
    public static synchronized TransMeta loadTransFile(String filePath) throws KettleException {
        TransMeta transMeta = null;
        transMeta = new TransMeta(filePath);
        return transMeta;
    }

    /**
     *  根据任务类型，文件地址，加载作业信息
     * @param ktlJobType
     * @return
     * @throws KettleException
     */
    public static synchronized AbstractMeta loadRepositoryFile(String ktlJobType, String repositoryDir, String fileName) throws KettleException {
        repositoryDir = repositoryDir.replace("\\", "/");
        repositoryDir = repositoryDir.endsWith("/") ? repositoryDir : repositoryDir + "/";
        String filePath = repositoryDir + fileName;
        if(ConstantKettle.KETTLE_TYPE_KJB.contains(ktlJobType.toUpperCase())) {
            return loadJobFile(filePath);
        } else if(ConstantKettle.KETTLE_TYPE_KTR.contains(ktlJobType.toUpperCase())) {
            return loadTransFile(filePath);
        }
        return null;
    }

    /**
     * 从文件加载
     * @param ktlJobType
     * @param repositoryDir
     * @param fileName
     * @return
     * @throws KettleException
     */
    public static synchronized AbstractMeta loadRepository(String dbuuid, String storageType, String ktlJobType, String repositoryDir, String fileName) throws KettleException {
        String key = getKey(storageType, ktlJobType, repositoryDir, fileName);
        AbstractMeta result = null;
        if(jobs.containsKey(key)) {
            result = jobs.get(key);
        } else {
            if(ConstantKettle.REP_TYPE_DB.equalsIgnoreCase(storageType.trim())) {
                result = loadRepositoryDb(ktlJobType, repositoryDir, fileName);
            } else if( ConstantKettle.REP_TYPE_FILE.equalsIgnoreCase(storageType.trim()) ) {
                result = loadRepositoryFile(ktlJobType, repositoryDir, fileName);
            }
            if(result!=null) {
                jobs.put(key, result);
            }
        }
        return result;
    }

    /**
     * 加载资源
     * @throws KettleException
     */
    public static synchronized AbstractMeta loadRepositoryDb(String ktlJobType, String repositoryDir, String fileName) throws KettleException {
        KettleDatabaseRepository repository = KettleFactory.getRepository();
        RepositoryDirectoryInterface directory = repository.findDirectory(repositoryDir);
        if(directory==null) {
            throw new KettleException("Job对应数据库中目录不存在:" + repositoryDir);
        }
        if(ConstantKettle.KETTLE_TYPE_KJB.contains(ktlJobType.toUpperCase())) {
            JobMeta jobMeta = repository.loadJob(repository.getJobId(fileName, directory), null);
            return jobMeta;
        } else if(ConstantKettle.KETTLE_TYPE_KTR.contains(ktlJobType.toUpperCase())) {
            TransMeta transMeta = ((Repository) repository).loadTransformation(fileName, directory, null, true, null );
            return transMeta;
        }
        return null;
    }


    // /**
    //  * 查找资源
    //  * @param repPath
    //  * @throws KettleException
    //  */
    // public static synchronized AbstractMeta loadRepository(String ktlJobType, String repPath) throws KettleException {
    //     String key = repPath + "_" + ktlJobType;
    //     // if(jobs.containsKey(key)) {
    //     //     return jobs.get(key);
    //     // }
    //     KettleDatabaseRepository repository = KettleFactory.getRepository();
    //     //根据变量查找到模型所在的目录对象,此步骤很重要。
    //     String dirName = FileUtils.getFolderName(repPath);
    //     if(StringUtils.isBlank(dirName)) {
    //         throw new KettleException("文件路径错误!");
    //     }
    //     String jobName = FileUtils.getFilename(repPath);
    //     if(StringUtils.isBlank(jobName)) {
    //         throw new KettleException("文件名错误!");
    //     }
    //     RepositoryDirectoryInterface directory = repository.findDirectory(dirName);
    //     if(directory==null) {
    //         throw new KettleException("Job对应目录不存在:" + dirName);
    //     }
    //     if(ConstantKettle.KETTLE_TYPE_KJB.contains(ktlJobType.toUpperCase())) {
    //         JobMeta jobMeta = repository.loadJob(repository.getJobId(jobName, directory), null);
    //         jobs.put(key, jobMeta);
    //         return jobMeta;
    //     } else if(ConstantKettle.KETTLE_TYPE_KTR.contains(ktlJobType.toUpperCase())) {
    //         TransMeta transMeta = ((Repository) repository).loadTransformation(jobName, directory, null, true, null );
    //         jobs.put(key, transMeta);
    //         return transMeta;
    //     }
    //     return null;
    // }
    //
    // /**
    //  * 从资源库加载job作业
    //  */
    // public static synchronized JobMeta loadJobRepository(String path) throws KettleException {
    //     String key = path + "_" + ConstantKettle.KETTLE_TYPE_KJB;
    //     if(jobs.containsKey(key)) {
    //         return (JobMeta) jobs.get(key);
    //     }
    //     KettleDatabaseRepository repository = KettleFactory.getRepository();
    //     //根据变量查找到模型所在的目录对象,此步骤很重要。
    //     String dirName = FileUtils.getFolderName(path);
    //     if(StringUtils.isBlank(dirName)) {
    //         throw new KettleException("文件路径错误!");
    //     }
    //     String jobName = FileUtils.getFilename(path);
    //     if(StringUtils.isBlank(jobName)) {
    //         throw new KettleException("文件名错误!");
    //     }
    //
    //     RepositoryDirectoryInterface directory = repository.findDirectory(dirName);
    //     if(directory==null) {
    //         throw new KettleException("Job对应目录不存在:" + dirName);
    //     }
    //     //ObjectId jobId = repository.getJobId(jobName, directory);
    //     //JobMeta jobMeta = repository.loadJob(jobId, null);
    //     JobMeta jobMeta = repository.loadJob(jobName, directory, null, null);
    //     jobs.put(key, jobMeta);
    //     return jobMeta;
    // }
    //
    //
    // /**
    //  * 从资源库加载trans作业
    //  */
    // public static synchronized TransMeta loadTransRepository(String path) throws KettleException {
    //     String key = path + "_" + ConstantKettle.KETTLE_TYPE_KTR;
    //     if(jobs.containsKey(key)) {
    //         return (TransMeta) jobs.get(key);
    //     }
    //     KettleDatabaseRepository repository = KettleFactory.getRepository();
    //     //根据变量查找到模型所在的目录对象,此步骤很重要。
    //     String dirName = FileUtils.getFolderName(path);
    //     if(StringUtils.isBlank(dirName)) {
    //         throw new KettleException("文件路径错误!");
    //     }
    //     String jobName = FileUtils.getFilename(path);
    //     if(StringUtils.isBlank(jobName)) {
    //         throw new KettleException("文件名错误!");
    //     }
    //     RepositoryDirectoryInterface directory = repository.findDirectory(dirName);
    //     if(directory==null) {
    //         throw new KettleException("Job对应目录不存在:" + dirName);
    //     }
    //     TransMeta transMeta = ((Repository) repository).loadTransformation(jobName, directory, null, true, null );
    //     jobs.put(key, transMeta);
    //     return transMeta;
    // }
    //
    //
    // /**
    //  * 加载资源
    //  * @param ktlJobType
    //  * @param repType
    //  * @param repPath
    //  * @return
    //  * @throws KettleException
    //  */
    // public static synchronized AbstractMeta load(String ktlJobType, String repType, String repPath) throws KettleException {
    //     if(ConstantKettle.KETTLE_TYPE_KTR.equals(ktlJobType)) { //kettle转换
    //         TransMeta transMeta = null;
    //         if(ConstantKettle.REP_TYPE_DB.equals(repType)) { //从资源库中加载
    //             transMeta = (TransMeta) loadTransRepository(repPath);
    //         } else { //文件
    //             transMeta = new TransMeta(repPath);
    //         }
    //         return transMeta;
    //     } else if(ConstantKettle.KETTLE_TYPE_KJB.equals(ktlJobType)) { //kettle job
    //         JobMeta jobMeta = null;
    //         if(ConstantKettle.REP_TYPE_DB.equals(repType)) { //从资源库中加载
    //             jobMeta = (JobMeta) loadJobRepository(repPath);
    //         } else {
    //             jobMeta = new JobMeta(repPath, null);
    //         }
    //         return jobMeta;
    //     }
    //     return null;
    // }
    //




}
