package com.khsh.etl.databuilder;// /**
//  * Copyright (C), 2016-2018, 武汉康华数海有限公司
//  * FileName: SynTables
//  * Author:   Ejet
//  * CreateDate:     2018-08-28 10:45
//  * Description: 同步库及表结构
//  * History:
//  * Version: 1.0
//  */
// package com.khsh.etl.databuilder;
//
// import com.ejet.comm.db.DbConfig;
// import com.ejet.comm.db.DbFactory;
// import com.ejet.comm.utils.io.IOUtils;
// import com.khsh.etl.databuilder.db.*;
// import com.khsh.etl.databuilder.db.meta.*;
// import com.khsh.etl.databuilder.db.vo.ColumnVO;
// import com.khsh.etl.databuilder.db.vo.TableVO;
// import oracle.jdbc.OracleConnection;
//
// import java.sql.Connection;
// import java.sql.DatabaseMetaData;
// import java.sql.SQLException;
// import java.util.ArrayList;
// import java.util.List;
//
// public class SynTables {
//
//     private DbConfig srcConfig = null;
//     private DbConfig destConfig = null;
//
//     private DbContext srcCtx = new DbContext();
//     private DbContext destCtx = new DbContext();
//
//     public SynTables(DbConfig src, DbConfig dest) {
//         super();
//         this.srcConfig = src;
//         this.destConfig = dest;
//     }
//
//     /**
//      * 初始并校验库连接
//      */
//     private void initConnection(DbConfig config, DbContext ctx) throws SQLException {
//         Connection connection = DbFactory.getConnection(config.getDriverClassName(), config.getUrl(), config.getUsername(), config.getPassword());
//         if(connection==null) {
//             throw new RuntimeException("数据库连接失败!" + config.getUrl());
//         }
//         String catalog = null;
//         String schema = null;
//         DbTypeEnum dbType = DbMetaHelper.formatDbTypeEnum(connection);
//         if(dbType== DbTypeEnum.ORACLE) {
//             DatabaseMetaData dmd = connection.getMetaData();
//             //dmd.getCatalogs().fin
//     		OracleConnection conn = (OracleConnection)connection;
// //    		catalog = conn.getCatalog();
// //    		schema = conn.getSchema();
//         } else {
//             catalog = connection.getCatalog();
//             schema = connection.getSchema();
//         }
//         DbDatabaseMeta databaseSrc = new DbDatabaseMeta(DbMetaHelper.formatDbTypeEnum(connection),
//                 config.getDriverClassName(), config.getUrl(), config.getUsername(), config.getPassword(),
//                 catalog, schema);
//         ctx.setDatabase(databaseSrc);
//         ctx.setConnection(connection);
//     }
//
//     // /**
//     //  * 将数据库查询字段进行转换
//     //  */
//     // private DbColumnMeta convertColumnVO(ColumnVO columnVO) {
//     //     DbColumnMeta meta = new DbColumnMeta();
//     //     meta.setColumnName(columnVO.getColumnName());
//     //     meta.setColumnType(DbColumnTypeEnum.getColTypeByname(columnVO.getDataType())); //获取字典类型
//     //     meta.setNull((columnVO.getIsnullable()!=null && columnVO.getIsnullable().equalsIgnoreCase("N")) ? false : true);
//     //     meta.setLength(columnVO.getLength());
//     //     meta.setDataScale(columnVO.getDataScale());
//     //     meta.setColOrder(columnVO.getColOrder());
//     //     return meta;
//     // }
//
//     // /**
//     //  * 获取源库 表字段
//     //  */
//     // private List<DbColumnMeta> getColumns(DbDatabaseMeta database, String tableName) throws Exception {
//     //     //获取表字段
//     //     List<ColumnVO> columns = DbHelper.queryColumns(srcCtx.getConnection(), database, tableName);
//     //     List<DbColumnMeta> columnList = new ArrayList<>();
//     //     if(columns!=null && columns.size()>0) {
//     //         for(ColumnVO item : columns) {
//     //             DbColumnMeta col=convertColumnVO(item);
//     //             if(col.getColumnType()==null) {
//     //                 throw new RuntimeException("[表]" + tableName + ", 字段: " + item.getColumnName() + ", " + item.getDataType() + ", 格式不可识别!");
//     //             }
//     //             columnList.add(col);
//     //         }
//     //     }
//     //     return columnList;
//     // }
//
//     /**
//      * 获取源库表
//      */
//     private void getSrcTables() throws Exception {
//         //获取所有库表
//         if(srcCtx.getTables()==null) {
//             List<DbTableMeta> tableMeta = new ArrayList<>();
//             srcCtx.setTables(tableMeta);
//         }
//         srcCtx.getTables().clear();
//         List<TableVO> srcTables = DbHelper.queryTables(srcCtx.getConnection(), srcCtx.getDatabase(), srcCtx.getIgnoreTables());
//         if(srcTables!=null) {
//             System.out.println("[库表个数]" + srcTables.size()  );
//             for(TableVO item : srcTables) {
//                 String tableName = item.getTableName();
//                 System.out.println("[" + item.getTableCatalog() + "." + item.getTableType() + "]"
//                         + "[库.表名]" + item.getTableSchema() + "." + tableName  );
//                 List<DbColumnMeta> columnMeta = getColumns(srcCtx.getDatabase(), tableName);
//                 DbTableMeta meta = new DbTableMeta(tableName, columnMeta);
//                 srcCtx.getTables().add(meta);
//             }
//         }
//     }
//
//
//     /**
//      * 处理附加信息字段
//      */
//     private void buildAddtion(DbTableMeta tableMeta) {
//         if(destCtx.getAddtion()!=null && destCtx.getAddtion().size()>0) {
//             for(DbTableMeta item : destCtx.getAddtion()) {
//                 if(item==null) {
//                     continue;
//                 }
//                 if(item.getTableName()==null || item.getTableName().trim()=="" || item.getTableName().equals(tableMeta.getTableName())) {
//                     for(DbColumnMeta col: item.getColumns()) {
//                         col.setColumnTypeShell(DbColumnConverter.getColumnTypeShell(col));
//                         tableMeta.getColumns().add(col);
//                     }
//                 }
//             }
//         }
//     }
//
//     /**
//      * 构建目标表
//      * @throws Exception
//      */
//     private void buildDesTables(boolean isDrop) throws Exception {
//         if(destCtx.getTables()==null) {
//             List<DbTableMeta> tableMeta = new ArrayList<>();
//             destCtx.setTables(tableMeta);
//         }
//         destCtx.getTables().clear();
//         int count = 0;
//         for(DbTableMeta item : srcCtx.getTables()) {
//             System.out.println(++count + "====[源库.表名]" + srcCtx.getDatabase().getSchema() + "." + item.getTableName()  );
//             DbTableMeta tableMeta = buildColumns(item);
//             System.out.println(count + "====[目标库.表名]"+ tableMeta.getTableName() + ", 建表脚本:" + tableMeta.getTableCreateShell());
//             DbHelper.createTable(destCtx.getConnection(), destCtx.getDatabase(), isDrop, tableMeta.getTableName(), tableMeta.getTableCreateShell());
//             destCtx.getTables().add(tableMeta);
//         }
//     }
//
//     /**
//      * 构建表字段
//      *
//      * @return
//      * @throws Exception
//      */
//     private DbTableMeta buildColumns(DbTableMeta item) throws Exception {
//         //获取表字段
//         List<DbColumnMeta> columnList = new ArrayList<>();
//         for(DbColumnMeta col : item.getColumns()) {
//             DbColumnMeta dest = DbColumnConverter.convertColumn(col, srcCtx.getDatabase().getDbtype(), destCtx.getDatabase().getDbtype());
//             columnList.add(dest);
//         }
//         DbTableMeta tableMeta = new DbTableMeta(item.getTableName(), columnList);
//         //处理附加信息
//         buildAddtion(tableMeta);
//         tableMeta.setTableCreateShell(DbMetaHelper.getCreateSQL(destCtx.getDatabase().getDbtype(), tableMeta));
//         return tableMeta;
//     }
//
//
//     /**
//      * 设置新增表及字段信息，如果表名称为空，则字段针对所有表
//      * @param addtion
//      */
//     public void setAddition(List<DbTableMeta> addtion) {
//         destCtx.setAddtion(addtion);
//     }
//
//     /**
//      * 设置忽略同步表名字
//      * @param ignoreTables
//      */
//     public void setIgnoreTables(List<String> ignoreTables) {
//         srcCtx.setIgnoreTables(ignoreTables);
//     }
//
//     /**
//      * 同步表
//      *
//      * @return
//      */
//     public void build(boolean isDrop) throws Exception {
//         try {
//             initConnection(srcConfig, srcCtx);
//             initConnection(destConfig, destCtx);
//             getSrcTables();
//             buildDesTables(isDrop);
//         } finally {
//             IOUtils.closeQuietly(srcCtx.getConnection());
//             IOUtils.closeQuietly(destCtx.getConnection());
//         }
//     }
//
//
// }
